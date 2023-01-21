package com.edsonmoreirajr.votacao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Links implements Serializable {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Link que representa a página atual exibida.")
    private String self;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Link que representa a primeira.")
    private String first;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Link que representa a página anterior.")
    private String prev;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Link que representa a próxima página.")
    private String next;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Link que representa a última página.")
    private String last;
}
