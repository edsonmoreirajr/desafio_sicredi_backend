package com.edsonmoreirajr.votacao.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class SessaoRequest {

    @NotNull
    @Positive
    private Long pautaId;
    @Builder.Default
    private Long duracao = 1L;

}
