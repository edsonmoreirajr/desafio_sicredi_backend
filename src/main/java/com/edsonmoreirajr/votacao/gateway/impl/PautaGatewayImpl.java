package com.edsonmoreirajr.votacao.gateway.impl;

import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.entity.Pauta;
import com.edsonmoreirajr.votacao.gateway.PautaGateway;
import com.edsonmoreirajr.votacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PautaGatewayImpl implements PautaGateway {

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
        pautaRepository.deleteById(id);
    }

    @Override
    public TotalVotosDto getTotalVotos(Long pautaId) {
        return pautaRepository.getTotalVotos(pautaId);
    }

}
