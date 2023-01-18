package com.edsonmoreirajr.votacao.usecase;

import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PautaUseCase {
    Page<PautaDto> getAllPautas(Pageable pageable);

    PautaDto createPauta(PautaRequest pautaRequest);

    PautaDto updatePauta(Long id, PautaRequest pautaRequest);

    PautaDto getPautaById(Long id);

    void deletePauta(Long id);

    TotalVotosDto getTotalVotos(Long id);
}
