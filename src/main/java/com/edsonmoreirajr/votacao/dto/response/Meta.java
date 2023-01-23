package com.edsonmoreirajr.votacao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Meta implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Total de registros encontrados no banco de dados.")
    private Long totalRecords;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Total de páginas geradas de acordo a quantidade de registros exibidas por página.")
    private Integer totalPages;
}
