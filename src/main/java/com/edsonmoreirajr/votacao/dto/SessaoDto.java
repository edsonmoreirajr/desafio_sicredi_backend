package com.edsonmoreirajr.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessaoDto {

    private Long id;
    private Long duracao;
    private Long pautaId;
    private ZonedDateTime abertura;
    private ZonedDateTime fechamento;
    private Boolean ativo;
}
