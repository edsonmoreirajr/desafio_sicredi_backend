package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.SessaoDto;
import com.edsonmoreirajr.votacao.dto.request.SessaoRequest;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.SessaoUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/v1/sessoes")
@Tag(name = "Sessões", description = "Sessões endpoints")
@RequiredArgsConstructor
public class SessaoController {

    private final SessaoUseCase sessaoUseCase;
    private final HateoasUtil<SessaoDto> hateoasUtil;

    @GetMapping
    @Operation(description = "Retorna uma lista pagina de todas as sessões.")
    public ResponseEntity<PagedResponse<SessaoDto>> getAllSessoes(
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "abertura", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "ativo", direction = Sort.Direction.DESC)
            })
            @ParameterObject Pageable pageable) {

        Page<SessaoDto> sessaoDtos = sessaoUseCase.getAllSessoes(pageable);

        return ResponseEntity.ok(hateoasUtil.buildResponseList(sessaoDtos));
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza os dados de uma sessão por id.")
    ResponseEntity<SessaoDto> updateSessao(
            @Parameter(description = "Id da sessão.") @PathVariable("id") Long id,
            @Parameter(description = "Objeto com dados da sessão para atualização.") @Valid @RequestBody SessaoRequest sessaoRequest) {

        SessaoDto sessaoDto = sessaoUseCase.updateSessao(id, sessaoRequest);

        return ResponseEntity.ok(sessaoDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Busca uma sessão pelo id.")
    public ResponseEntity<SessaoDto> getSessaoById(
            @Parameter(description = "Id da sessão.") @PathVariable("id") Long id) {

        SessaoDto sessaoDto = sessaoUseCase.getSessaoById(id);

        if (nonNull(sessaoDto)) {
            return ResponseEntity.ok(sessaoDto);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleta uma sessão pelo id.")
    public ResponseEntity<Void> deleteSessao(
            @Parameter(description = "Id da sessão.") @PathVariable("id") Long id) {

        sessaoUseCase.deleteSessao(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/abrir-sessao")
    @Operation(description = "Cria e abre uma nova sessão para votação.")
    public ResponseEntity<SessaoDto> abrirSessao(
            @Parameter(description = "Objeto com dados da sessão para criação.") @Valid @RequestBody SessaoRequest sessaoRequest) {

        SessaoDto sessaoDto = sessaoUseCase.abrirSessao(sessaoRequest);
        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(SessaoController.class, sessaoDto.getId()))
                .body(sessaoDto);
    }

    @GetMapping(value = "/{id}/fechar-sessao")
    @Operation(description = "Fecha uma sessão ativa pelo id.")
    public ResponseEntity<?> fecharSessao(
            @Parameter(description = "Id da sessão.") @PathVariable("id") Long id) {

        sessaoUseCase.fecharSessao(id);
        return ResponseEntity.ok().build();
    }

}
