package com.edsonmoreirajr.votacao.controller.impl;

import com.edsonmoreirajr.votacao.controller.PautaController;
import com.edsonmoreirajr.votacao.dto.PautaDto;
import com.edsonmoreirajr.votacao.dto.TotalVotosDto;
import com.edsonmoreirajr.votacao.dto.request.PautaRequest;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.PautaUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PautaControllerImpl implements PautaController {

    private final PautaUseCase pautaUseCase;
    private final HateoasUtil<PautaDto> hateoasUtil;

    @GetMapping
    @Override
    public ResponseEntity<PagedResponse<PautaDto>> getAllPautas(
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "titulo", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "status", direction = Sort.Direction.ASC)
            })
            Pageable pageable) {

        Page<PautaDto> pautaDtos = pautaUseCase.getAllPautas(pageable);
        return ResponseEntity.ok(hateoasUtil.buildResponseList(pautaDtos));
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<PautaDto> createPauta(@Valid @RequestBody PautaRequest pautaRequest) {
        PautaDto pautaDto = pautaUseCase.createPauta(pautaRequest);
        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(PautaControllerImpl.class, pautaDto.getId()))
                .body(pautaDto);
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<PautaDto> updatePauta(
            @PathVariable("id") @Positive Long id,
            @Valid @RequestBody PautaRequest pautaRequest) {

        PautaDto pautaDto = pautaUseCase.updatePauta(id, pautaRequest);
        return ResponseEntity.ok(pautaDto);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<PautaDto> getPautaById(
            @PathVariable("id") @Positive Long id) {

        PautaDto pautaDto = pautaUseCase.getPautaById(id);

        if (nonNull(pautaDto)) {
            return ResponseEntity.ok(pautaDto);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deletePauta(
            @PathVariable("id") @Positive Long id) {

        pautaUseCase.deletePauta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{pautaId}/total-votos")
    @Override
    public ResponseEntity<TotalVotosDto> getTotalVotos(
            @PathVariable("pautaId") @Positive Long pautaId) {

        TotalVotosDto totalVotosResponseModel = pautaUseCase.getTotalVotos(pautaId);

        if (nonNull(totalVotosResponseModel)) {
            return ResponseEntity.ok(totalVotosResponseModel);
        }
        return ResponseEntity.noContent().build();
    }
}
