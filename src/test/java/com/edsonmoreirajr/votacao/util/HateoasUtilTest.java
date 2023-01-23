package com.edsonmoreirajr.votacao.util;

import com.edsonmoreirajr.votacao.HateaosUtilsForTests;
import com.edsonmoreirajr.votacao.controller.impl.AssociadoControllerImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HateoasUtilTest {

    private static final String LINK_SELF = "http://localhost:8080/objects/1?page=1&size=10&sort=coluna-nome,desc";
    private static final String LINK_FIRST = "http://localhost:8080/objects/1?page=0&size=10&sort=coluna-nome,desc";
    private static final String LINK_PREV = "";
    private static final String LINK_NEXT = "http://localhost:8080/objects/1?page=2&size=10&sort=coluna-nome,desc";
    private static final String LINK_LAST = "http://localhost:8080/objects/1?page=2&size=10&sort=coluna-nome,desc";
    private static final String LINK_SELF_CREATE = "/api/v1/associados/1";


    private final EasyRandom easyRandom = new EasyRandom();
    private final Pageable pageable = PageRequest.of(0, 10);
    private final Link linkSelf = Link.of(LINK_SELF, IanaLinkRelations.SELF);
    private final Link linkFirst = Link.of(LINK_FIRST, IanaLinkRelations.FIRST);
    private final Link linkNext = Link.of(LINK_NEXT, IanaLinkRelations.NEXT);
    private final Link linkLast = Link.of(LINK_LAST, IanaLinkRelations.LAST);

    @InjectMocks
    HateoasUtil<Object> hateoasUtil;

    @Mock
    private PagedResourcesAssembler<Object> pagedResourcesAssembler;

    @Test
    void deveRetornarPagedResponseDeObjectsComMetaELinksPopulados_QuandoBuildResponseList() {
        var objectPage = new PageImpl<>(easyRandom.objects(Object.class, 10).toList(), pageable, 30);
        var pageMetadata = new PagedModel.PageMetadata(10, 0, 30, 3);
        var pagedModel = PagedModel.of(HateaosUtilsForTests.buildEntityModelList(objectPage.getContent()), pageMetadata, linkSelf, linkFirst, linkNext, linkLast);
        when(pagedResourcesAssembler.toModel(any())).thenReturn(pagedModel);

        var pagedResponse = hateoasUtil.buildResponseList(objectPage);

        assertNotNull(pagedResponse);
        assertNotNull(pagedResponse.getData());
        assertNotNull(pagedResponse.getMeta());
        assertEquals(10, pagedResponse.getData().size());
        assertThat(pagedResponse.getData()).usingRecursiveComparison().isEqualTo(objectPage.getContent());

        assertEquals(30, pagedResponse.getMeta().getTotalRecords());
        assertEquals(3, pagedResponse.getMeta().getTotalPages());
        assertEquals(LINK_SELF, pagedResponse.getLinks().getSelf());
        assertEquals(LINK_FIRST, pagedResponse.getLinks().getFirst());
        assertEquals(LINK_PREV, pagedResponse.getLinks().getPrev());
        assertEquals(LINK_NEXT, pagedResponse.getLinks().getNext());
        assertEquals(LINK_LAST, pagedResponse.getLinks().getLast());
    }

    @Test
    void deveRetornarPagedResponseVazioComMetaVazioELinksComSomenteLinkSelf_QuandoBuildResponseList() {
        var objectPage = new PageImpl<>(new ArrayList<>(), pageable, 0);
        var pageMetadata = new PagedModel.PageMetadata(0, 0, 0, 0);
        var pagedModel = PagedModel.of(HateaosUtilsForTests.buildEntityModelList(objectPage.getContent()), pageMetadata, linkSelf);
        when(pagedResourcesAssembler.toModel(any())).thenReturn(pagedModel);

        var pagedResponse = hateoasUtil.buildResponseList(objectPage);

        assertNotNull(pagedResponse);
        assertNotNull(pagedResponse.getData());
        assertNotNull(pagedResponse.getMeta());
        assertTrue(pagedResponse.getData().isEmpty());
        assertEquals(0, pagedResponse.getMeta().getTotalRecords());
        assertEquals(0, pagedResponse.getMeta().getTotalPages());
        assertEquals(LINK_SELF, pagedResponse.getLinks().getSelf());
        assertEquals("", pagedResponse.getLinks().getFirst());
        assertEquals("", pagedResponse.getLinks().getPrev());
        assertEquals("", pagedResponse.getLinks().getNext());
        assertEquals("", pagedResponse.getLinks().getLast());
    }

    @Test
    void deveRetornarUmaUriFormadaPorPathEncontradoNoControllerBarraIdDoObjetoInserido() {
        var uri = hateoasUtil.getHateoasSelLik(AssociadoControllerImpl.class, 1).toString();
        assertEquals(LINK_SELF_CREATE, uri);
    }
}
