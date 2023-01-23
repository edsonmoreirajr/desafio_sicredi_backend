package com.edsonmoreirajr.votacao.dto;

import com.edsonmoreirajr.votacao.entity.enums.EnumVoto;
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
public class VotoDto {

    private Long id;
    private EnumVoto voto;
    private Long sessaoId;
}
