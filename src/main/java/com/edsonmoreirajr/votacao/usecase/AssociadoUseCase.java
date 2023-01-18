package com.edsonmoreirajr.votacao.usecase;

import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociadoUseCase {

    Page<AssociadoDto> getAllAssociados(Pageable pageable);

    AssociadoDto createAssociado(AssociadoRequest associadoRequest);

    AssociadoDto updateAssociado(Long id, AssociadoRequest associadoRequest);

    AssociadoDto getAssociadoById(Long id);

    void deleteAssociado(Long id);
}
