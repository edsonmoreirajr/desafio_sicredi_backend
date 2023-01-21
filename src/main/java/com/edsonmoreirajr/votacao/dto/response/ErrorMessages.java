package com.edsonmoreirajr.votacao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorMessages(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Instante quando aconteceu o erro.")
        LocalDateTime timestamp,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Código do status http.")
        int statusCode,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Lista com descições detalhadas dos erros.")
        List<String> descriptions,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Mensagem resumida od erro.")
        String message
) {
    public ErrorMessages(int statusCode, List<String> descriptions, String message) {
        this(
                LocalDateTime.now(),
                statusCode,
                descriptions,
                message
        );
    }
}
