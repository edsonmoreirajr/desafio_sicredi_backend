package com.edsonmoreirajr.votacao.mapper;

import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.request.AssociadoRequest;
import com.edsonmoreirajr.votacao.entity.Associado;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssociadoMapperTest {

    private final EasyRandom easyRandom = new EasyRandom();
    private AssociadoRequest associadoRequest = easyRandom.nextObject(AssociadoRequest.class);

    @Test
    void deveRetornarAssociadoDtoComMesmoDadosDeAssociadoPeloMapper_QuandoToAssociadoDto() {
        Associado associado = easyRandom.nextObject(Associado.class);
        var associadoDtoFixture = AssociadoDto.builder()
                .cpf(associado.getCpf())
                .id(associado.getId())
                .nome(associado.getNome())
                .status(associado.getStatus())
                .build();

        var associadoDtoResult = AssociadoMapper.INSTANCE.toAssociadoDto(associado);
        assertNotNull(associadoDtoResult);
        assertThat(associadoDtoResult).usingRecursiveComparison().isEqualTo(associadoDtoFixture);
    }

    @Test
    void deveRetornarAssociadoComMesmoDadosDeAssociadoRequestPeloMapper_QuandoToAssociado() {
        associadoRequest = easyRandom.nextObject(AssociadoRequest.class);
        var associadoFixture = Associado.builder()
                .cpf(associadoRequest.getCpf())
                .nome(associadoRequest.getNome())
                .status(associadoRequest.getStatus())
                .build();

        var associadoResult = AssociadoMapper.INSTANCE.toAssociado(associadoRequest);
        assertNotNull(associadoResult);
        assertThat(associadoResult).usingRecursiveComparison().isEqualTo(associadoFixture);
    }

    @Test
    void deveCopiarOsDadosDeAssociadoRequestParaAssociadoPeloMapper_QuandoUpdateAssociadoFromAssociadoRequest() {
        Associado associado = new Associado();
        Associado associadoFixture = Associado.builder()
                .cpf(associadoRequest.getCpf())
                .nome(associadoRequest.getNome())
                .status(associadoRequest.getStatus())
                .build();

        AssociadoMapper.INSTANCE.updateAssociadoFromAssociadoRequest(associadoRequest, associado);
        assertNotNull(associado);
        assertThat(associado).usingRecursiveComparison().isEqualTo(associadoFixture);
    }
}
