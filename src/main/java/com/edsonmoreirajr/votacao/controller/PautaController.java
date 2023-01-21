package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import com.edsonmoreirajr.votacao.dto.response.ErrorMessage;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pautas", description = "Pautas endpoints")
public interface PautaController {

    @Operation(summary = "Busca todas as pautas.",
            description = "Retorna uma lista paginada de todas as pautas cadastradas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida",
                            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
            }
    )
    ResponseEntity<PagedResponse<PautaDto>> getAllPautas(@ParameterObject Pageable pageable);

    @Operation(summary = "Cria uma pauta.",
            description = "Cria um pauta com dados enviados pelo body da requisição.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pauta criada com sucesso.",
                            content = @Content(schema = @Schema(implementation = PautaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados da pauta enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<PautaDto> createPauta(
            @Parameter(description = "Objeto com dados da pauta para criação.") PautaRequest pautaRequest);

    @Operation(summary = "Atualiza os dados de uma pauta.",
            description = "Atualiza os dados de uma pauta por id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida.",
                            content = @Content(schema = @Schema(implementation = PautaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados da pauta enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pauta não econtrada para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<PautaDto> updatePauta(
            @Parameter(description = "Id da pauta.") Long id,
            @Parameter(description = "Objeto com dados da pauta para atualização.") PautaRequest pautaRequest);

    @Operation(summary = "Busca pauta.",
            description = "Busca uma pauta pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida.",
                            content = @Content(schema = @Schema(implementation = PautaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id do pauta precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pauta não econtrada para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<PautaDto> getPautaById(@Parameter(description = "Id da pauta.") Long id);

    @Operation(summary = "Deleta uma pauta.",
            description = "Deleta uma pauta pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id da pauta precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pauta foi deletada com sucesso.")
            })
    ResponseEntity<Void> deletePauta(@Parameter(description = "Id da pauta.") Long id);

    @Operation(summary = "Retornar o total de votos.",
            description = "Processa o total de votos e retorna esses valores e o dados da pauta pelo id dela.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida",
                            content = @Content(schema = @Schema(implementation = TotalVotosDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id do pauta precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Pauta não econtrada para o id informado.")
            }
    )
    ResponseEntity<TotalVotosDto> getTotalVotos(
            @Parameter(description = "Id da pauta") Long pautaId);
}
