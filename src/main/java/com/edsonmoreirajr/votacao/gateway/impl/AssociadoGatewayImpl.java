package com.edsonmoreirajr.votacao.gateway.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.enums.EnumStatusAssociado;
import com.edsonmoreirajr.votacao.gateway.AssociadoGateway;
import com.edsonmoreirajr.votacao.repository.AssociadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AssociadoGatewayImpl implements AssociadoGateway {

    private final static String ENTITY_NOT_FOUND_ASSOCIADO = "entity-not-found.associado";
    private final MessageSourceService messageSourceService;
    private final AssociadoRepository associadoRepository;

    @Override
    public Page<Associado> getAllAssociados(Pageable pageable) {
        return associadoRepository.findAll(pageable);
    }

    @Override
    public Associado createOrUpdateAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    @Override
    public Optional<Associado> getAssociadoById(Long id) {
        return associadoRepository.findById(id);
    }

    @Override
    public void deleteAssociado(Long id) {
        var associado = associadoRepository.findById(id);
        if (associado.isEmpty()) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_ASSOCIADO, id));
        }
        associadoRepository.deleteById(id);
    }

    @Override
    public EnumStatusAssociado getStatusByAssociadoId(Long id) {
        return associadoRepository.findStatusByAssociadoId(id);
    }

    @Override
    public Optional<Associado> getAssociadoByCPF(String cpf) {
        return associadoRepository.findByCpf(cpf);
    }

}
