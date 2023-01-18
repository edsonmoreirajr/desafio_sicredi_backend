package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;
import com.edsonmoreirajr.votacao.usecase.VotoUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votos")
@Tag(name = "Votos", description = "Votos endpoints")
@RequiredArgsConstructor
public class VotoController {

    private final VotoUseCase votoUseCase;
    private final HateoasUtil<?> hateoasUtil;

    @PostMapping(value = "/votar")
    @Operation(description = "Cria um voto.")
    public ResponseEntity<VotoDto> createVoto(
            @Parameter(description = "Objeto com dados do voto para criação.") @RequestBody VotoRequest votoRequest) {

        var votoDto = votoUseCase.createVoto(votoRequest);

        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(VotoController.class, votoDto.getId()))
                .body(votoDto);
    }
}
