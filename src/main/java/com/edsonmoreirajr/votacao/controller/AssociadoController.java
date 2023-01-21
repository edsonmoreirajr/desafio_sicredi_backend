package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
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

@Tag(name = "Associados", description = "Associados endpoints")
public interface AssociadoController {

    @Operation(summary = "Busca todos os associados.",
            description = "Retorna uma lista paginada de todos os associados cadastrados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida",
                            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
            }
    )
    ResponseEntity<PagedResponse<AssociadoDto>> getAllAssociados(@ParameterObject Pageable pageable);

    @Operation(summary = "Cria um associado.",
            description = "Cria um associado com dados enviados pelo body da requisição.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Associado criado com sucesso.",
                            content = @Content(schema = @Schema(implementation = AssociadoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados do associado enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Não foi possível processar a sua solicitação devido a um erro de negócio.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<AssociadoDto> createAssociado(
            @Parameter(description = "Objeto com dados do associado para criação.") AssociadoRequest associadoRequest);

    @Operation(summary = "Atualiza os dados do associado",
            description = "Atualiza os dados de um associado pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida.",
                            content = @Content(schema = @Schema(implementation = AssociadoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados do associado enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Associado não econtrado para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))}
    )
    ResponseEntity<AssociadoDto> updateAssociado(
            @Parameter(description = "Id do associado.") Long id,
            @Parameter(description = "Objeto com dados do associado para atualização.") AssociadoRequest associadoRequest);

    @Operation(summary = "Busca um associado.",
            description = "Busca um associado pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida.",
                            content = @Content(schema = @Schema(implementation = AssociadoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id do associado precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Associado não econtrado para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))}
    )
    ResponseEntity<AssociadoDto> getAssociadoById(@Parameter(description = "Id do associado.") Long id);

    @Operation(summary = "Deleta um associado",
            description = "Deleta associado pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id do associado precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Associado foi deletado com sucesso.")
            }
    )
    ResponseEntity<Void> deleteAssociado(
            @Parameter(description = "Id do associado") Long id);
}