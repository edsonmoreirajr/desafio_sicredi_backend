package com.edsonmoreirajr.votacao.controller.impl;

import com.edsonmoreirajr.votacao.controller.VotoController;
import com.edsonmoreirajr.votacao.dto.VotoDto;
import com.edsonmoreirajr.votacao.dto.request.VotoRequest;
import com.edsonmoreirajr.votacao.usecase.VotoUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votos")
@RequiredArgsConstructor
public class VotoControllerImpl implements VotoController {

    private final VotoUseCase votoUseCase;
    private final HateoasUtil<?> hateoasUtil;

    @PostMapping(value = "/votar")
    @Override
    public ResponseEntity<VotoDto> createVoto(
            @RequestBody VotoRequest votoRequest) {

        var votoDto = votoUseCase.createVoto(votoRequest);

        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(VotoControllerImpl.class, votoDto.getId()))
                .body(votoDto);
    }
}
