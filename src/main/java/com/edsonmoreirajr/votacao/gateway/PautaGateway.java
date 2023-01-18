package com.edsonmoreirajr.votacao.gateway;

import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.entity.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PautaGateway {
    Page<Pauta> getAllPautas(Pageable pageable);

    Pauta createOrUpdatePauta(Pauta pauta);

    Optional<Pauta> getPautaById(Long id);

    void deletePauta(Long id);

    TotalVotosDto getTotalVotos(Long pautaId);
}
