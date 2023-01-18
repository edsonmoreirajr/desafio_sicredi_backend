package com.edsonmoreirajr.votacao.usecase.impl;

import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import com.edsonmoreirajr.votacao.entity.enums.StatusPautaEnum;
import com.edsonmoreirajr.votacao.exception.BusinessException;
import com.edsonmoreirajr.votacao.gateway.PautaGateway;
import com.edsonmoreirajr.votacao.gateway.SessaoGateway;
import com.edsonmoreirajr.votacao.mapper.PautaMapper;
import com.edsonmoreirajr.votacao.usecase.PautaUseCase;
import com.edsonmoreirajr.votacao.validator.PageableValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PautaUseCaseImpl implements PautaUseCase {

    private final PautaGateway pautaGateway;
    private final SessaoGateway sessaoGateway;

    @Override
    public Page<PautaDto> getAllPautas(Pageable pageable) {
        PageableValidator.validaPaginaENomeColunasOrdenacaoDoPageable(pageable);

        var pautas = pautaGateway.getAllPautas(pageable);
        return new PageImpl<>(pautas.stream()
                .map(PautaMapper.INSTANCE::toPautaDto)
                .collect(Collectors.toList()), pautas.getPageable(), pautas.getTotalElements());
    }

    @Override
    public PautaDto createPauta(PautaRequest pautaRequest) {
        var pauta = PautaMapper.INSTANCE.toPauta(pautaRequest);
        pauta.setStatus(StatusPautaEnum.ANALISE);
        return PautaMapper.INSTANCE.toPautaDto(pautaGateway.createOrUpdatePauta(pauta));
    }

    @Override
    public PautaDto updatePauta(Long id, PautaRequest pautaRequest) {
        var pauta = pautaGateway.getPautaById(id).orElse(null);
        if (isNull(pauta)) {
            throw new EntityNotFoundException("Pauta não encontrada para o Id: " + id);
        }
        PautaMapper.INSTANCE.updatePautaFromPautaRequest(pautaRequest, pauta);
        return PautaMapper.INSTANCE.toPautaDto(pautaGateway.createOrUpdatePauta(pauta));
    }

    @Override
    public PautaDto getPautaById(Long id) {
        var pauta = pautaGateway.getPautaById(id).orElse(null);
        return PautaMapper.INSTANCE.toPautaDto(pauta);
    }

    @Override
    public void deletePauta(Long id) {
        var sessao = sessaoGateway.getSessaoByPautaId(id).orElse(null);

        if (nonNull(sessao)) {
            throw new BusinessException("Não é possivel deletar a pauta de id: " + id + " por que está vinculada a sessão de id: " + sessao.getId());
        }
        pautaGateway.deletePauta(id);
    }

    @Override
    public TotalVotosDto getTotalVotos(Long pautaId) {
        var pautaOptional = pautaGateway.getPautaById(pautaId);
        if (pautaOptional.isEmpty()) {
            throw new BusinessException("Não existe uma pauta para esse id: " + pautaId);
        }
        return pautaGateway.getTotalVotos(pautaId);
    }
}
