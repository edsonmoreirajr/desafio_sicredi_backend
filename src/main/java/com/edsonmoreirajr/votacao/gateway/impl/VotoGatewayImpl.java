package com.edsonmoreirajr.votacao.gateway.impl;

import com.edsonmoreirajr.votacao.entity.Voto;
import com.edsonmoreirajr.votacao.gateway.VotoGateway;
import com.edsonmoreirajr.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VotoGatewayImpl implements VotoGateway {

    private final VotoRepository votoRepository;

    @Override
    public Voto create(Voto voto) {
        return votoRepository.save(voto);
    }

    @Override
    public Optional<Voto> getVotoByAssociadoId(Long associadoId, Long sessaoId) {
        return votoRepository.findByAssociadoIdAndSessaoId(associadoId, sessaoId);
    }

    @Override
    public void deleteAllVotosBySessaoId(Long sessaoId) {
        votoRepository.deleteAllBySessaoId(sessaoId);
    }
}
