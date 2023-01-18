package com.edsonmoreirajr.votacao.util;

import com.edsonmoreirajr.votacao.dto.response.Links;
import com.edsonmoreirajr.votacao.dto.response.Meta;
import com.edsonmoreirajr.votacao.dto.response.PagedResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@AllArgsConstructor
public class HateoasUtil<T> {

    private PagedResourcesAssembler<T> pagedResourcesAssembler;

    private Meta buildResponseMeta(final Page<T> page) {
        return Meta.builder()
                .totalPages(page.getTotalPages())
                .totalRecords(page.getTotalElements())
                .build();
    }

    private Links buildResponseLinks(final Page<T> page) {
        var pagedModel = pagedResourcesAssembler.toModel(page);
        return Links.builder()
                .self(getLinks(pagedModel.getLink(IanaLinkRelations.SELF).orElse(null)))
                .first(getLinks(pagedModel.getLink(IanaLinkRelations.FIRST).orElse(null)))
                .next(getLinks(pagedModel.getNextLink().orElse(null)))
                .prev(getLinks(pagedModel.getPreviousLink().orElse(null)))
                .last(getLinks(pagedModel.getLink(IanaLinkRelations.LAST).orElse(null)))
                .build();
    }

    private String getLinks(final Link link) {
        if (link != null) {
            var uriBuilder = UriComponentsBuilder.fromHttpUrl(link.getHref());
            return uriBuilder.build().toUriString();
        }
        return "";
    }

    public PagedResponse<T> buildResponseList(Page<T> pageList) {
        return new PagedResponse<>(pageList.getContent(), buildResponseMeta(pageList), buildResponseLinks(pageList));
    }

    public URI getHateoasSelLik(Class<?> controllerClass, Number id) {
        return linkTo(controllerClass).slash(id).withSelfRel().toUri();
    }
}
