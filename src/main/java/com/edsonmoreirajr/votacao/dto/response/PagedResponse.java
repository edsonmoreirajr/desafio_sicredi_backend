package com.edsonmoreirajr.votacao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Lista com dados buscados do banco de dados.")
    private List<T> data;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Objeto com informações de total de registros e total de páginas.")
    private Meta meta;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Objeto com links que represetam a páginação")
    private Links links;
}
