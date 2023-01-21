package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;
import com.edsonmoreirajr.votacao.dto.response.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Votos", description = "Votos endpoints")
public interface VotoController {

    @Operation(summary = "Cria um voto",
            description = "Cria um voto com dados enviados pelo body da requisição.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Voto criado com sucesso.",
                            content = @Content(schema = @Schema(implementation = VotoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados do voto enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Não foi possível processar a sua solicitação devido a um erro de negócio.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Associado não econtrado para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<VotoDto> createVoto(
            @Parameter(description = "Objeto com dados do voto para criação.") VotoRequest votoRequest);
}
