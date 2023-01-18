package com.edsonmoreirajr.votacao.usecase.impl;

import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;
import com.edsonmoreirajr.votacao.entity.Sessao;
import com.edsonmoreirajr.votacao.entity.enums.StatusAssociadoEnum;
import com.edsonmoreirajr.votacao.exception.BusinessException;
import com.edsonmoreirajr.votacao.gateway.AssociadoGateway;
import com.edsonmoreirajr.votacao.gateway.SessaoGateway;
import com.edsonmoreirajr.votacao.gateway.VotoGateway;
import com.edsonmoreirajr.votacao.mapper.VotoMapper;
import com.edsonmoreirajr.votacao.usecase.SessaoUseCase;
import com.edsonmoreirajr.votacao.usecase.VotoUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotoUseCaseImpl implements VotoUseCase {

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
            throw new BusinessException("O associado de id: " + votoRequest.getAssociadoId() + " já votou. Só é permitido votar uma vez para a mesma pauta.");
        }

        voto = VotoMapper.INSTANCE.toVoto(votoRequest);
        voto = votoGateway.create(voto);

        return VotoMapper.INSTANCE.toVotoDto(voto);
    }

    private void validaStatusParaVotarDoAssociado(Long associadoId) {
        StatusAssociadoEnum statusAssociado = associadoGateway.getStatusByAssociadoId(associadoId);
        if (isNull(statusAssociado)) {
            throw new EntityNotFoundException("Associado não encontrado para o id: " + associadoId);
        }
        if (statusAssociado.equals(StatusAssociadoEnum.UNABLE_TO_VOTE)) {
            throw new BusinessException("O associado de id: " + associadoId + " não está apto a votar.");
        }
    }

}
