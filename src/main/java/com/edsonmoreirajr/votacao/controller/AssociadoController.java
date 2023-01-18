package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.AssociadoUseCase;
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
import org.springframework.data.web.SortDefault.SortDefaults;
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
@RequestMapping("/api/v1/associados")
@Tag(name = "Associados", description = "Associados endpoints")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoUseCase associadoUseCase;
    private final HateoasUtil<AssociadoDto> hateoasUtil;

    @GetMapping
    @Operation(description = "Retorna uma lista paginada de todos os associados cadastrados.")
    public ResponseEntity<PagedResponse<AssociadoDto>> getAllAssociados(
            @SortDefaults({
                    @SortDefault(sort = "status", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "nome", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable pageable) {

        Page<AssociadoDto> associadoDtos = associadoUseCase.getAllAssociados(pageable);
        return ResponseEntity.ok(hateoasUtil.buildResponseList(associadoDtos));
    }

    @PostMapping("/create")
    @Operation(description = "Cria um associado.")
    public ResponseEntity<AssociadoDto> createAssociado(
            @Parameter(description = "Objeto com dados do associado para criação.") @Valid @RequestBody AssociadoRequest associadoRequest) {

        AssociadoDto associadoDto = associadoUseCase.createAssociado(associadoRequest);
        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(AssociadoController.class, associadoDto.getId()))
                .body(associadoDto);
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza os dados de um associado pelo id.")
    ResponseEntity<AssociadoDto> updateAssociado(
            @Parameter(description = "Id do associado.") @PathVariable("id") Long id,
            @Parameter(description = "Objeto com dados do associado para atualização.") @Valid @RequestBody AssociadoRequest associadoRequest) {

        AssociadoDto associadoDto = associadoUseCase.updateAssociado(id, associadoRequest);
        return ResponseEntity.ok(associadoDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Busca um associado pelo id.")
    public ResponseEntity<AssociadoDto> getAssociadoById(
            @Parameter(description = "Id do associado.") @PathVariable("id") Long id) {

        AssociadoDto associadoDto = associadoUseCase.getAssociadoById(id);

        if (nonNull(associadoDto)) {
            return ResponseEntity.ok(associadoDto);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleta associado pelo id.")
    public ResponseEntity<Void> deleteAssociado(
            @Parameter(description = "Id do associado") @PathVariable("id") Long id) {

        associadoUseCase.deleteAssociado(id);
        return ResponseEntity.noContent().build();
    }
}
