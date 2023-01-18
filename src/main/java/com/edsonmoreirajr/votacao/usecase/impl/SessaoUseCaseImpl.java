package com.edsonmoreirajr.votacao.usecase.impl;

import com.edsonmoreirajr.votacao.dto.SessaoDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.SessaoRequest;
import com.edsonmoreirajr.votacao.entity.Sessao;
import com.edsonmoreirajr.votacao.entity.enums.StatusPautaEnum;
import com.edsonmoreirajr.votacao.exception.BusinessException;
import com.edsonmoreirajr.votacao.gateway.PautaGateway;
import com.edsonmoreirajr.votacao.gateway.SessaoGateway;
import com.edsonmoreirajr.votacao.gateway.VotoGateway;
import com.edsonmoreirajr.votacao.mapper.SessaoMapper;
import com.edsonmoreirajr.votacao.schedule.FecharSessaoTask;
import com.edsonmoreirajr.votacao.usecase.SessaoUseCase;
import com.edsonmoreirajr.votacao.validator.PageableValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessaoUseCaseImpl implements SessaoUseCase {
    private final TaskScheduler taskScheduler;
    private final SessaoGateway sessaoGateway;
    private final PautaGateway pautaGateway;
    private final VotoGateway votoGateway;

    @Override
    public Page<SessaoDto> getAllSessoes(Pageable pageable) {
        PageableValidator.validaPaginaENomeColunasOrdenacaoDoPageable(pageable);

        var sessoes = sessaoGateway.getAllSessoes(pageable);
        return new PageImpl<>(sessoes.stream()
                .map(SessaoMapper.INSTANCE::toSessaoDto)
                .collect(Collectors.toList()), sessoes.getPageable(), sessoes.getTotalElements());
    }

    @Override
    public SessaoDto updateSessao(Long id, SessaoRequest sessaoRequest) {
        var sessao = sessaoGateway.getSessaoById(id).orElse(null);
        if (isNull(sessao)) {
            throw new EntityNotFoundException("Sessão não encontrado para o Id: " + id);
        }
        SessaoMapper.INSTANCE.updateSessaoFromSessaoRequest(sessaoRequest, sessao);
        return SessaoMapper.INSTANCE.toSessaoDto(sessaoGateway.createOrUpdateSessao(sessao));
    }

    @Override
    public SessaoDto getSessaoById(Long id) {
        var sessao = sessaoGateway.getSessaoById(id).orElse(null);
        return SessaoMapper.INSTANCE.toSessaoDto(sessao);
    }

    @Override
    @Transactional
    public void deleteSessao(Long id) {
        votoGateway.deleteAllVotosBySessaoId(id);
        sessaoGateway.deleteSessao(id);
    }

    @Override
    public SessaoDto abrirSessao(SessaoRequest sessaoRequest) {
        var sessao = sessaoGateway.getSessaoByPautaId(sessaoRequest.getPautaId()).orElse(null);
        if (nonNull(sessao)) {
            throw new BusinessException("Não é possivel abrir uma nova sessão por que já existe outra aberta para pauta de id: " + sessaoRequest.getPautaId());
        }

        sessao = SessaoMapper.INSTANCE.toSessao(sessaoRequest);
        sessao.setAbertura(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        sessao.setAtivo(true);
        sessao = sessaoGateway.createOrUpdateSessao(sessao);

        criaScheduleParaFecharASessao(sessao);
        return SessaoMapper.INSTANCE.toSessaoDto(sessao);
    }

    private void criaScheduleParaFecharASessao(Sessao sessao) {
        long duration = sessao.getDuracao() * 60000;
        var fecharSessaoTask = new FecharSessaoTask(sessao.getId(), this);
        var scheduledFuture = taskScheduler.scheduleWithFixedDelay(
                fecharSessaoTask, Duration.ofMillis(duration)
        );
        fecharSessaoTask.setScheduledFuture(scheduledFuture);
    }

    @Override
    @Transactional
    public void fecharSessao(Long id) {
        var sessao = sessaoGateway.getSessaoById(id).orElse(null);
        validaSessaoEstaAtiva(sessao, id);

        sessao.setFechamento(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        sessao.setAtivo(false);
        sessaoGateway.createOrUpdateSessao(sessao);

        validaPautaEAtualizaStatusPauta(sessao);
    }

    private void validaPautaEAtualizaStatusPauta(Sessao sessao) {
        var pauta = pautaGateway.getPautaById(sessao.getPauta().getId()).orElse(null);
        if (isNull(pauta)) {
            throw new EntityNotFoundException("Pauta não encontrada para o id: " + sessao.getPauta().getId());
        }

        TotalVotosDto totalVotosDto = pautaGateway.getTotalVotos(pauta.getId());

        if (totalVotosDto.getTotalVotosSim() > totalVotosDto.getTotalVotosNao()) {
            pauta.setStatus(StatusPautaEnum.APROVADA);

        } else if (totalVotosDto.getTotalVotosSim() < totalVotosDto.getTotalVotosNao()) {
            pauta.setStatus(StatusPautaEnum.RECUSADA);

        } else {
            pauta.setStatus(StatusPautaEnum.EMPATE);
        }
        pautaGateway.createOrUpdatePauta(pauta);
    }

    @Override
    public void validaSessaoEstaAtiva(Sessao sessao, Long id) {
        if (isNull(sessao)) {
            throw new EntityNotFoundException("Sessão não encontrada para o id: " + id);
        }
        if (!sessao.getAtivo()) {
            throw new BusinessException("A sessão de id: " + id + " não está está ativa e não é mais possivel votar.");
        }
    }

    @Override
    public void verificaSeHaTempoRestanteParaVotar(Sessao sessao) {
        var dateTimeFechamento = sessao.getAbertura().plus(sessao.getDuracao(), ChronoUnit.MINUTES);
        long tempoRestanteSegundos = ChronoUnit.SECONDS.between(sessao.getAbertura(), dateTimeFechamento);
        if (tempoRestanteSegundos <= 10) {
            throw new BusinessException("Não há mais tempo habil para votar na sessão de id: " + sessao.getId());
        }
    }
}
