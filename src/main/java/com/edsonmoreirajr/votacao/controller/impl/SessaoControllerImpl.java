package com.edsonmoreirajr.votacao.controller.impl;

import com.edsonmoreirajr.votacao.controller.SessaoController;
import com.edsonmoreirajr.votacao.dto.SessaoDto;
import com.edsonmoreirajr.votacao.dto.request.SessaoRequest;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.SessaoUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
public class SessaoControllerImpl implements SessaoController {

    private final SessaoUseCase sessaoUseCase;
    private final HateoasUtil<SessaoDto> hateoasUtil;

    @GetMapping
    @Override
    public ResponseEntity<PagedResponse<SessaoDto>> getAllSessoes(
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "abertura", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "ativo", direction = Sort.Direction.DESC)
            })
            Pageable pageable) {

        Page<SessaoDto> sessaoDtos = sessaoUseCase.getAllSessoes(pageable);

        return ResponseEntity.ok(hateoasUtil.buildResponseList(sessaoDtos));
    }

    @PutMapping(value = "/{id}")
    @Override
    public ResponseEntity<SessaoDto> updateSessao(
            @PathVariable("id") @Positive Long id,
            @Valid @RequestBody SessaoRequest sessaoRequest) {

        SessaoDto sessaoDto = sessaoUseCase.updateSessao(id, sessaoRequest);

        return ResponseEntity.ok(sessaoDto);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ResponseEntity<SessaoDto> getSessaoById(
            @PathVariable("id") @Positive Long id) {

        SessaoDto sessaoDto = sessaoUseCase.getSessaoById(id);

        if (nonNull(sessaoDto)) {
            return ResponseEntity.ok(sessaoDto);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> deleteSessao(@Parameter(description = "Id da sessão.") Long id) {

        sessaoUseCase.deleteSessao(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/abrir-sessao")
    @Override
    public ResponseEntity<SessaoDto> abrirSessao(
            @Valid @RequestBody SessaoRequest sessaoRequest) {

        SessaoDto sessaoDto = sessaoUseCase.abrirSessao(sessaoRequest);
        return ResponseEntity
                .created(hateoasUtil.getHateoasSelLik(SessaoControllerImpl.class, sessaoDto.getId()))
                .body(sessaoDto);
    }

    @GetMapping(value = "/{id}/fechar-sessao")
    @Override
    public ResponseEntity<?> fecharSessao(
            @PathVariable("id") @Positive Long id) {

        sessaoUseCase.fecharSessao(id);
        return ResponseEntity.ok().build();
    }

}
