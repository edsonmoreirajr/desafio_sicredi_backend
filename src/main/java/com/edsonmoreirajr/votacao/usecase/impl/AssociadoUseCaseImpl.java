package com.edsonmoreirajr.votacao.usecase.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
import com.edsonmoreirajr.votacao.exception.entities.BusinessException;
import com.edsonmoreirajr.votacao.gateway.AssociadoGateway;
import com.edsonmoreirajr.votacao.mapper.AssociadoMapper;
import com.edsonmoreirajr.votacao.usecase.AssociadoUseCase;
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
public class AssociadoUseCaseImpl implements AssociadoUseCase {

    private final static String BUSINESS_ASSOCIADO_CADASTRADO_CPF = "business.associado.ja-cadastrado-com-cpf";
    private final static String ENTITY_NOT_FOUND_ASSOCIADO = "entity-not-found.associado";
    private final MessageSourceService messageSourceService;
    private final AssociadoGateway associadoGateway;

    @Override
    public Page<AssociadoDto> getAllAssociados(Pageable pageable) {
        PageableValidator.validaPaginaENomeColunasOrdenacaoDoPageable(pageable);

        var associados = associadoGateway.getAllAssociados(pageable);
        return new PageImpl<>(associados.stream()
                .map(AssociadoMapper.INSTANCE::toAssociadoDto)
                .collect(Collectors.toList()), associados.getPageable(), associados.getTotalElements());
    }

    @Override
    public AssociadoDto createAssociado(AssociadoRequest associadoRequest) {
        var associado = associadoGateway.getAssociadoByCPF(associadoRequest.getCpf()).orElse(null);
        if (nonNull(associado)) {
            throw new BusinessException(messageSourceService.getMessage(BUSINESS_ASSOCIADO_CADASTRADO_CPF, associadoRequest.getCpf()));
        }
        associado = AssociadoMapper.INSTANCE.toAssociado(associadoRequest);
        return AssociadoMapper.INSTANCE.toAssociadoDto(associadoGateway.createOrUpdateAssociado(associado));
    }

    @Override
    public AssociadoDto updateAssociado(Long id, AssociadoRequest associadoRequest) {
        var associado = associadoGateway.getAssociadoById(id).orElse(null);
        if (isNull(associado)) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_ASSOCIADO, id));
        }
        AssociadoMapper.INSTANCE.updateAssociadoFromAssociadoRequest(associadoRequest, associado);
        return AssociadoMapper.INSTANCE.toAssociadoDto(associadoGateway.createOrUpdateAssociado(associado));
    }

    @Override
    public AssociadoDto getAssociadoById(Long id) {
        var associado = associadoGateway.getAssociadoById(id).orElse(null);
        return AssociadoMapper.INSTANCE.toAssociadoDto(associado);
    }

    public void deleteAssociado(Long id) {
        associadoGateway.deleteAssociado(id);
    }
}
