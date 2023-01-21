package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.SessaoDto;
import com.edsonmoreirajr.votacao.dto.request.SessaoRequest;
import com.edsonmoreirajr.votacao.dto.response.ErrorMessage;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Sessões", description = "Sessões endpoints")
public interface SessaoController {

    @Operation(summary = "Busca todas as sesões.",
            description = "Retorna uma lista pagina de todas as sessões.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida",
                            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
            }
    )
    ResponseEntity<PagedResponse<SessaoDto>> getAllSessoes(@ParameterObject Pageable pageable);

    @Operation(summary = "Atualiza os dados de uma sessão.",
            description = "Atualiza os dados de uma sessão por id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida.",
                            content = @Content(schema = @Schema(implementation = SessaoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados da sessão enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Sessão não econtrada para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))}
    )
    ResponseEntity<SessaoDto> updateSessao(
            @Parameter(description = "Id da sessão.") Long id,
            @Parameter(description = "Objeto com dados da sessão para atualização.") SessaoRequest sessaoRequest);

    @Operation(summary = "Busca uma sessão.",
            description = "Busca uma sessão pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação bem sucedida.",
                            content = @Content(schema = @Schema(implementation = SessaoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id do sessão precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sessão não econtrada para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))}
    )
    ResponseEntity<SessaoDto> getSessaoById(@Parameter(description = "Id da sessão.") Long id);

    @Operation(summary = "Deleta uma sessão.",
            description = "Deleta uma sessão pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id da sessão precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sessão foi deletado com sucesso.")
            }
    )
    ResponseEntity<Void> deleteSessao(@Parameter(description = "Id da sessão.") Long id);

    @Operation(summary = "Cria e abre uma sessão.",
            description = "Cria e abre uma nova sessão para votação.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Sessão criada com sucesso.",
                            content = @Content(schema = @Schema(implementation = SessaoDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Um ou mais dados do sessão enviados na requisição estão incorretos.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Não foi possível processar a sua solicitação devido a um erro de negócio.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<SessaoDto> abrirSessao(
            @Parameter(description = "Objeto com dados da sessão para criação.") @Valid @RequestBody SessaoRequest sessaoRequest);

    @Operation(summary = "Fecha uma sessão.",
            description = "Fecha uma sessão ativa pelo id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operação realizada com sucesso.")
                    ,
                    @ApiResponse(
                            responseCode = "400",
                            description = "O id da sessão precisa ser maior que zero.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pauta não econtrada para o id informado.",
                            content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    ResponseEntity<?> fecharSessao(
            @Parameter(description = "Id da sessão.") Long id);
}
