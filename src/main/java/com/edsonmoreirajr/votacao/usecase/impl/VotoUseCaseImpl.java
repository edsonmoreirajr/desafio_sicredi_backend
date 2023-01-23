package com.edsonmoreirajr.votacao.usecase.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;
import com.edsonmoreirajr.votacao.entity.Sessao;
import com.edsonmoreirajr.votacao.entity.enums.EnumStatusAssociado;
import com.edsonmoreirajr.votacao.exception.entities.BusinessException;
import com.edsonmoreirajr.votacao.gateway.AssociadoGateway;
import com.edsonmoreirajr.votacao.gateway.SessaoGateway;
import com.edsonmoreirajr.votacao.gateway.VotoGateway;
import com.edsonmoreirajr.votacao.mapper.VotoMapper;
import com.edsonmoreirajr.votacao.usecase.SessaoUseCase;
import com.edsonmoreirajr.votacao.usecase.VotoUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class VotoUseCaseImpl implements VotoUseCase {

    private final static String BUSINESS_ASSOCIADO_VOTO_UNICO = "business.associado.voto-unico";
    private final static String ENTITY_NOT_FOUND_ASSOCIADO = "entity-not-found.associado";
    private final static String BUSINESS_ASSOCIADO_UNABLE_TO_VOTE = "business.associado.unable-to-vote";

    private final MessageSourceService messageSourceService;
    private final VotoGateway votoGateway;
    private final AssociadoGateway associadoGateway;
    private final SessaoGateway sessaoGateway;
    private final SessaoUseCase sessaoUseCase;

    @Override
    public VotoDto createVoto(VotoRequest votoRequest) {

        Sessao sessao = sessaoGateway.getSessaoById(votoRequest.getSessaoId()).orElse(null);
        sessaoUseCase.validaSessaoEstaAtiva(sessao, votoRequest.getSessaoId());
        sessaoUseCase.verificaSeHaTempoRestanteParaVotar(sessao);

        validaStatusParaVotarDoAssociado(votoRequest.getAssociadoId());

        var voto = votoGateway.getVotoByAssociadoId(votoRequest.getAssociadoId(), votoRequest.getAssociadoId()).orElse(null);

        if (nonNull(voto)) {
            throw new BusinessException(messageSourceService.getMessage(BUSINESS_ASSOCIADO_VOTO_UNICO, votoRequest.getAssociadoId()));
        }

        voto = VotoMapper.INSTANCE.toVoto(votoRequest);
        voto = votoGateway.create(voto);

        return VotoMapper.INSTANCE.toVotoDto(voto);
    }

    private void validaStatusParaVotarDoAssociado(Long associadoId) {
        EnumStatusAssociado statusAssociado = associadoGateway.getStatusByAssociadoId(associadoId);
        if (isNull(statusAssociado)) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_ASSOCIADO, associadoId));
        }
        if (statusAssociado.equals(EnumStatusAssociado.UNABLE_TO_VOTE)) {
            throw new BusinessException(messageSourceService.getMessage(BUSINESS_ASSOCIADO_UNABLE_TO_VOTE, associadoId));
        }
    }

}
