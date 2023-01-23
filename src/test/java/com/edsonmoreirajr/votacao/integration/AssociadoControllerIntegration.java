package com.edsonmoreirajr.votacao.integration;

import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.entity.Associado;
import com.edsonmoreirajr.votacao.entity.Voto;
import com.edsonmoreirajr.votacao.repository.AssociadoRepository;
import com.edsonmoreirajr.votacao.repository.VotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssociadoControllerIntegration {

    private static final String LINK_SELF = "http://localhost/api/v1/associados?page=0&size=5&sort=status,nome,asc";
    private static final String LINK_FIRST = "http://localhost/api/v1/associados?page=0&size=5&sort=status,nome,asc";
    private static final String LINK_PREV = "";
    private static final String LINK_NEXT = "http://localhost/api/v1/associados?page=1&size=5&sort=status,nome,asc";
    private static final String LINK_LAST = "http://localhost/api/v1/associados?page=2&size=5&sort=status,nome,asc";
    @Autowired
    protected MockMvc mvc;
    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(FieldPredicates.named("id"))
            .stringLengthRange(11, 11);
    private final EasyRandom easyRandom = new EasyRandom(parameters);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private VotoRepository votoRepository;
    private List<Voto> todosVotos;
    private List<Associado> todosAssociados;

    @Test
    void deveRetornarResponseOKComPagedResponsePouladoComListaAssociadoDtoEMetaELinks_QuandoChamaEndPointAssociados() throws Exception {

        var pageable = PageRequest.of(0, 5, Sort.by("status", "nome"));
        var associados = associadoRepository.findAll(pageable);

        var result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/associados")
                        .param("page", "0")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseResult = result.getResponse().getContentAsString();

        assertFalse(responseResult.isEmpty());
        PagedResponse<?> resultPagedResponse = new ObjectMapper().readValue(responseResult, PagedResponse.class);
        assertEquals(5, resultPagedResponse.getData().size());

        List<AssociadoDto> associadoDtos = objectMapper.convertValue(
                resultPagedResponse.getData(),
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, AssociadoDto.class));

        assertThat(associados.getContent()).usingRecursiveComparison().isEqualTo(associadoDtos);
        assertEquals(3, resultPagedResponse.getMeta().getTotalPages());
        assertEquals(11, resultPagedResponse.getMeta().getTotalRecords());

        assertEquals(LINK_SELF, resultPagedResponse.getLinks().getSelf());
        assertEquals(LINK_FIRST, resultPagedResponse.getLinks().getFirst());
        assertEquals(LINK_PREV, resultPagedResponse.getLinks().getPrev());
        assertEquals(LINK_NEXT, resultPagedResponse.getLinks().getNext());
        assertEquals(LINK_LAST, resultPagedResponse.getLinks().getLast());
    }
}