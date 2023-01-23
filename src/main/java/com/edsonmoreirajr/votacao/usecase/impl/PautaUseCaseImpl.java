package com.edsonmoreirajr.votacao.usecase.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import com.edsonmoreirajr.votacao.entity.enums.EnumStatusPauta;
import com.edsonmoreirajr.votacao.exception.entities.BusinessException;
import com.edsonmoreirajr.votacao.gateway.PautaGateway;
import com.edsonmoreirajr.votacao.gateway.SessaoGateway;
import com.edsonmoreirajr.votacao.mapper.PautaMapper;
import com.edsonmoreirajr.votacao.usecase.PautaUseCase;
import com.edsonmoreirajr.votacao.validator.PageableValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class PautaUseCaseImpl implements PautaUseCase {

    private final static String BUSINESS_PAUTA_VINCULADA_SESSAO_ABERTA = "business.pauta.vinculada-sessao-aberta";
    private final static String ENTITY_NOT_FOUND_PAUTA = "entity-not-found.pauta";
    private final MessageSourceService messageSourceService;
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
        pauta.setStatus(EnumStatusPauta.ANALISE);
        return PautaMapper.INSTANCE.toPautaDto(pautaGateway.createOrUpdatePauta(pauta));
    }

    @Override
    public PautaDto updatePauta(Long id, PautaRequest pautaRequest) {
        var pauta = pautaGateway.getPautaById(id).orElse(null);
        if (isNull(pauta)) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_PAUTA, id));
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
            throw new BusinessException(messageSourceService.getMessage(BUSINESS_PAUTA_VINCULADA_SESSAO_ABERTA, id, sessao.getId()));
        }
        pautaGateway.deletePauta(id);
    }

    @Override
    public TotalVotosDto getTotalVotos(Long pautaId) {
        var pautaOptional = pautaGateway.getPautaById(pautaId);
        if (pautaOptional.isEmpty()) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_PAUTA, pautaId));
        }
        return pautaGateway.getTotalVotos(pautaId);
    }
}
