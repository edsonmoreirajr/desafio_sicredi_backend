package com.edsonmoreirajr.votacao.usecase;

import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;

public interface VotoUseCase {
    VotoDto createVoto(VotoRequest votoRequest);
}
