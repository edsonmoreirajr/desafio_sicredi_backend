package com.edsonmoreirajr.votacao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorMessage(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Instante quando aconteceu o erro.")
        LocalDateTime timestamp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Código do status http.")
        int statusCode,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Descrição detalhada do erro.")
        String description,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Mensagem resumida od erro.")
        String message
) {
    public ErrorMessage(int statusCode, String description, String message) {
        this(
                LocalDateTime.now(),
                statusCode,
                description,
                message
        );
    }
}
