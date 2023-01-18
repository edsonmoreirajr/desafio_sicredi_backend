package com.edsonmoreirajr.votacao.repository;

import com.edsonmoreirajr.votacao.dto.TotalVotosDto;

public interface PautaRepositoryCustom {

    TotalVotosDto getTotalVotos(Long id);

}
