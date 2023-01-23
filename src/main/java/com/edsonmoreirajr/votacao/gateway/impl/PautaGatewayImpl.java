package com.edsonmoreirajr.votacao.gateway.impl;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.entity.Pauta;
import com.edsonmoreirajr.votacao.gateway.PautaGateway;
import com.edsonmoreirajr.votacao.repository.PautaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PautaGatewayImpl implements PautaGateway {
    private final static String ENTITY_NOT_FOUND_PAUTA = "entity-not-found.pauta";
    private final MessageSourceService messageSourceService;
    private final PautaRepository pautaRepository;

    @Override
    public Page<Pauta> getAllPautas(Pageable pageable) {
        return pautaRepository.findAll(pageable);
    }

    @Override
    public Pauta createOrUpdatePauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    @Override
    public Optional<Pauta> getPautaById(Long id) {
        return pautaRepository.findById(id);
    }

    @Override
    public void deletePauta(Long id) {
        var pauta = pautaRepository.findById(id);
        if (pauta.isEmpty()) {
            throw new EntityNotFoundException(messageSourceService.getMessage(ENTITY_NOT_FOUND_PAUTA, id));
        }
        pautaRepository.deleteById(id);
    }

    @Override
    public TotalVotosDto getTotalVotos(Long pautaId) {
        return pautaRepository.getTotalVotos(pautaId);
    }

}
