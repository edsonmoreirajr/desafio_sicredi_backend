package com.edsonmoreirajr.votacao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @NotNull(message = "{validation.sessao.pautaid.notnull}")
    @Positive(message = "{validation.associado.nome.notblank}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Identificador da pauta gerado pelo banco de dados. Maior que zero.")
    private Long pautaId;
    @Builder.Default
    @Positive(message = "{validation.sessao.duracao.positive}")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Valor que define a duração da sessão. Maior que zero.")
    private Long duracao = 1L;

}
