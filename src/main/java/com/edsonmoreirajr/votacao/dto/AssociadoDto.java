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
public class AssociadoDto {

    private Long id;
    private String nome;
    private String cpf;
    private String status;
}
