package com.edsonmoreirajr.votacao.dto.request;

import com.edsonmoreirajr.votacao.entity.enums.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequest {

    private Long associadoId;
    private Long sessaoId;
    private VotoEnum voto;

}
