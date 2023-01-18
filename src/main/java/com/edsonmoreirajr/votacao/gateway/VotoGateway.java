package com.edsonmoreirajr.votacao.gateway;

import com.edsonmoreirajr.votacao.entity.Voto;

import java.util.Optional;

public interface VotoGateway {

    Voto create(Voto voto);

    Optional<Voto> getVotoByAssociadoId(Long associadoId, Long sessaoId);

    void deleteAllVotosBySessaoId(Long sessaoId);
}
