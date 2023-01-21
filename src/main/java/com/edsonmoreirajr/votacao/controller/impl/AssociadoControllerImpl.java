package com.edsonmoreirajr.votacao.controller.impl;

import com.edsonmoreirajr.votacao.controller.AssociadoController;
import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.AssociadoUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AssociadoControllerImpl implements AssociadoController {

    private final AssociadoUseCase associadoUseCase;
    private final HateoasUtil<AssociadoDto> hateoasUtil;

    @GetMapping
    @Override
    public ResponseEntity<PagedResponse<AssociadoDto>> getAllAssociados(
            @SortDefaults({
                    @SortDefault(sort = "status", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "nome", direction = Sort.Direction.ASC)
            })
            Pageable pageable) {

        Page<AssociadoDto> associadoDtos = associadoUseCase.getAllAssociados(pageable);
        return ResponseEntity.ok(hateoasUtil.buildResponseList(associadoDtos));
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<AssociadoDto> createAssociado(
            @Valid @RequestBody AssociadoRequest associadoRequest) {

        AssociadoDto associadoDto = associadoUseCase.createAssociado(associadoRequest);
        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(AssociadoControllerImpl.class, associadoDto.getId()))
                .body(associadoDto);
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<AssociadoDto> updateAssociado(
            @PathVariable("id") @Positive Long id,
            @Valid @RequestBody AssociadoRequest associadoRequest) {

        AssociadoDto associadoDto = associadoUseCase.updateAssociado(id, associadoRequest);
        return ResponseEntity.ok(associadoDto);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<AssociadoDto> getAssociadoById(
            @PathVariable("id") @Positive Long id) {

        AssociadoDto associadoDto = associadoUseCase.getAssociadoById(id);

        if (nonNull(associadoDto)) {
            return ResponseEntity.ok(associadoDto);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteAssociado(
            @PathVariable("id") @Positive Long id) {

        associadoUseCase.deleteAssociado(id);
        return ResponseEntity.noContent().build();
    }
}
