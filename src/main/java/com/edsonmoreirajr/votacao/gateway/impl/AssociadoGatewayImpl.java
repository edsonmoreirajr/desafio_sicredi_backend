package com.edsonmoreirajr.votacao.gateway.impl;

import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.enums.StatusAssociadoEnum;
import com.edsonmoreirajr.votacao.gateway.AssociadoGateway;
import com.edsonmoreirajr.votacao.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssociadoGatewayImpl implements AssociadoGateway {

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
        associadoRepository.deleteById(id);
    }

    @Override
    public StatusAssociadoEnum getStatusByAssociadoId(Long id) {
        return associadoRepository.findStatusByAssociadoId(id);
    }

    @Override
    public Optional<Associado> getAssociadoByCPF(String cpf) {
        return associadoRepository.findByCpf(cpf);
    }

}
