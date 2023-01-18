package com.edsonmoreirajr.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalVotosDto {

    private Long pautaId;
    private String titulo;
    private String status;
    private Long totalVotosSim;
    private Long totalVotosNao;
    private Long totalTodosVotos;
}
