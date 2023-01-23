package com.edsonmoreirajr.votacao.usecase;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.gateway.AssociadoGateway;
import com.edsonmoreirajr.votacao.usecase.impl.AssociadoUseCaseImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssociadoUseCaseTest {

    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private AssociadoGateway associadoGateway;
    @Mock
    private MessageSourceService messageSourceService;
    @InjectMocks
    private AssociadoUseCaseImpl associadoUseCase;

    @Test
    void deveRetornarPageDeAssociadoDto_QuandoGetAllAssociados() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("status", "nome"));
        Page<Associado> associadoPage = new PageImpl<>(easyRandom.objects(Associado.class, 10).toList(), pageable, 15);

        when(associadoGateway.getAllAssociados(pageable)).thenReturn(associadoPage);
        Page<AssociadoDto> associadoDtoPageResult = associadoUseCase.getAllAssociados(pageable);

        assertNotNull(associadoDtoPageResult);
        assertEquals(associadoPage.getContent().size(), associadoDtoPageResult.getContent().size());
        assertThat(associadoPage.getContent()).usingRecursiveComparison().isEqualTo(associadoDtoPageResult.getContent());
        verify(associadoGateway).getAllAssociados(pageable);
    }
}
