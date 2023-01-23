package com.edsonmoreirajr.votacao.controller;

import com.edsonmoreirajr.votacao.HateaosUtilsForTests;
import com.edsonmoreirajr.votacao.controller.impl.AssociadoControllerImpl;
import com.edsonmoreirajr.votacao.dto.AssociadoDto;
import com.edsonmoreirajr.votacao.dto.response.Links;
import com.edsonmoreirajr.votacao.dto.response.Meta;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import com.edsonmoreirajr.votacao.usecase.AssociadoUseCase;
import com.edsonmoreirajr.votacao.util.HateoasUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssociadoControllerImpl.class)
public class AssociadoControllerTest {
    private static final String LINK_SELF = "http://localhost:8080/api/v1/associados?page=0&size=10&sort=status,nome,asc";
    private static final String LINK_FIRST = "http://localhost:8080/api/v1/associados?page=0&size=10&sort=status,nome,asc";
    private static final String LINK_PREV = "";
    private static final String LINK_NEXT = "http://localhost:8080/api/v1/associados?page=1&size=10&sort=status,nome,asc";
    private static final String LINK_LAST = "http://localhost:8080/api/v1/associados?page=2&size=10&sort=status,nome,asc";

    private final Link linkSelf = Link.of(LINK_SELF, IanaLinkRelations.SELF);
    private final Link linkFirst = Link.of(LINK_FIRST, IanaLinkRelations.FIRST);
    private final Link linkNext = Link.of(LINK_NEXT, IanaLinkRelations.NEXT);
    private final Link linkLast = Link.of(LINK_LAST, IanaLinkRelations.LAST);

    private final EasyRandom easyRandom = new EasyRandom();
    @Autowired
    protected MockMvc mvc;
    Sort.Order orderStatus = Sort.Order.asc("status");
    Sort.Order orderName = Sort.Order.asc("nome");
    private final Sort sort = Sort.by(orderStatus, orderName);
    private final Pageable pageable = PageRequest.of(0, 10, sort);
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AssociadoUseCase associadoUseCase;
    @MockBean
    private HateoasUtil<AssociadoDto> hateoasUtil;
    @Mock
    private PagedResourcesAssembler<AssociadoDto> pagedResourcesAssembler;

    @Test
    void deveRetornarResponseOKComPagedResponsePouladoComListaAssociadoDtoEMetaELinks_QuandoChamaEndPointAssociados() throws Exception {

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(10, 0, 30, 3);
        Page<AssociadoDto> associadoDtos = new PageImpl<>(easyRandom.objects(AssociadoDto.class, 10).toList(), pageable, 30);
        PagedModel<EntityModel<AssociadoDto>> pagedModel = PagedModel.of(HateaosUtilsForTests.buildEntityModelList(associadoDtos.getContent()), pageMetadata, linkSelf, linkFirst, linkNext, linkLast);
        PagedResponse<AssociadoDto> pagedResponse = new PagedResponse<>(associadoDtos.getContent(), Meta.builder()
                .totalRecords(30L)
                .totalPages(3)
                .build(),
                Links.builder()
                        .self(LINK_SELF)
                        .first(LINK_FIRST)
                        .prev(LINK_PREV)
                        .next(LINK_NEXT)
                        .last(LINK_LAST)
                        .build());

        doReturn(associadoDtos).when(associadoUseCase).getAllAssociados(pageable);
        when(pagedResourcesAssembler.toModel(any())).thenReturn(pagedModel);
        when(hateoasUtil.buildResponseList(associadoDtos)).thenReturn(pagedResponse);

        var result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/associados")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseResult = result.getResponse().getContentAsString();

        assertFalse(responseResult.isEmpty());
        PagedResponse<?> resultPagedResponse = new ObjectMapper().readValue(responseResult, PagedResponse.class);
        assertEquals(10, resultPagedResponse.getData().size());

        List<AssociadoDto> associadoDtoList = objectMapper.convertValue(resultPagedResponse.getData(), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, AssociadoDto.class));
        assertThat(associadoDtoList).usingRecursiveComparison().isEqualTo(associadoDtos.getContent());
        assertEquals(3, resultPagedResponse.getMeta().getTotalPages());
        assertEquals(30, resultPagedResponse.getMeta().getTotalRecords());

        assertEquals(LINK_SELF, resultPagedResponse.getLinks().getSelf());
        assertEquals(LINK_FIRST, resultPagedResponse.getLinks().getFirst());
        assertEquals(LINK_PREV, resultPagedResponse.getLinks().getPrev());
        assertEquals(LINK_NEXT, resultPagedResponse.getLinks().getNext());
        assertEquals(LINK_LAST, resultPagedResponse.getLinks().getLast());
        verify(associadoUseCase).getAllAssociados(pageable);
        verify(hateoasUtil).buildResponseList(associadoDtos);
    }
}