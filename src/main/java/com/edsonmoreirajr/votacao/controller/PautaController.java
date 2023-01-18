package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.PautaUseCase;
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
@RequestMapping("/api/v1/pautas")
@Tag(name = "Pautas", description = "Pautas endpoints")
@RequiredArgsConstructor
public class PautaController {

    private final PautaUseCase pautaUseCase;
    private final HateoasUtil<PautaDto> hateoasUtil;

    @GetMapping
    @Operation(description = "Retorna uma lista paginada de todas as pautas cadastradas.")
    public ResponseEntity<PagedResponse<PautaDto>> getAllPautas(
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "titulo", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "status", direction = Sort.Direction.ASC)
            })
            @ParameterObject Pageable pageable) {

        Page<PautaDto> pautaDtos = pautaUseCase.getAllPautas(pageable);
        return ResponseEntity.ok(hateoasUtil.buildResponseList(pautaDtos));
    }

    @PostMapping("/create")
    @Operation(description = "Cria uma pauta.")
    public ResponseEntity<PautaDto> createPauta(
            @Parameter(description = "Objeto com dados da pauta para criação.") @Valid @RequestBody PautaRequest pautaRequest) {

        PautaDto pautaDto = pautaUseCase.createPauta(pautaRequest);
        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(PautaController.class, pautaDto.getId()))
                .body(pautaDto);
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualiza os dados de uma pauta por id.")
    ResponseEntity<PautaDto> updatePauta(
            @Parameter(description = "Id da pauta.") @PathVariable("id") Long id,
            @Parameter(description = "Objeto com dados da pauta para atualização.") @Valid @RequestBody PautaRequest pautaRequest) {

        PautaDto pautaDto = pautaUseCase.updatePauta(id, pautaRequest);
        return ResponseEntity.ok(pautaDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Busca uma pauta pelo id.")
    public ResponseEntity<PautaDto> getPautaById(
            @Parameter(description = "Id da pauta.") @PathVariable("id") Long id) {

        PautaDto pautaDto = pautaUseCase.getPautaById(id);

        if (nonNull(pautaDto)) {
            return ResponseEntity.ok(pautaDto);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Deleta uma pauta pelo id.")
    public ResponseEntity<Void> deletePauta(
            @Parameter(description = "Id da pauta.") @PathVariable("id") Long id) {

        pautaUseCase.deletePauta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{pautaId}/total-votos")
    @Operation(description = "Processa o total de votos e retorna esses valores e o dados da pauta pelo id dela.")
    public ResponseEntity<TotalVotosDto> getTotalVotos(
            @Parameter(description = "Id da pauta") @PathVariable("pautaId") Long pautaId) {

        TotalVotosDto totalVotosResponseModel = pautaUseCase.getTotalVotos(pautaId);

        if (nonNull(totalVotosResponseModel)) {
            return ResponseEntity.ok(totalVotosResponseModel);
        }
        return ResponseEntity.noContent().build();
    }

}
