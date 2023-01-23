package com.edsonmoreirajr.votacao.dto;

import com.edsonmoreirajr.votacao.entity.enums.EnumStatusPauta;
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
public class PautaDto {

    private Long id;
    private String titulo;
    private String descricao;
    private EnumStatusPauta status;
}
