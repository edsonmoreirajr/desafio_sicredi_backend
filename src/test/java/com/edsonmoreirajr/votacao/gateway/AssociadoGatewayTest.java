package com.edsonmoreirajr.votacao.gateway;

import com.edsonmoreirajr.votacao.config.message.MessageSourceService;
import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.gateway.impl.AssociadoGatewayImpl;
import com.edsonmoreirajr.votacao.repository.AssociadoRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssociadoGatewayTest {

    private final EasyRandom easyRandom = new EasyRandom();
    @Mock
    private AssociadoRepository associadoRepository;
    @InjectMocks
    private AssociadoGatewayImpl associadoGateway;
    @Mock
    private MessageSourceService messageSourceService;

    @Test
    void deveRetornarPageDeAssociadoQuandoFindAllDoRepositorioDeAssociado_QuandoGetAllAssociados() {
        var pageable = PageRequest.of(0, 10, Sort.by("status", "nome"));
        var associadoPage = new PageImpl<>(easyRandom.objects(Associado.class, 10).toList(), pageable, 30);

        when(associadoRepository.findAll(pageable)).thenReturn(associadoPage);
        var associadosResult = associadoGateway.getAllAssociados(pageable);

        assertNotNull(associadosResult);
        assertEquals(associadoPage.getContent().size(), associadosResult.getContent().size());
        assertThat(associadoPage).usingRecursiveComparison().isEqualTo(associadosResult);
        verify(associadoRepository).findAll(pageable);
    }
}
