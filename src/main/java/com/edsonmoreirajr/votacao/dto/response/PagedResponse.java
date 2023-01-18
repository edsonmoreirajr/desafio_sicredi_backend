package com.edsonmoreirajr.votacao.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PagedResponse<T> implements Serializable {

    private final List<T> data;
    private final Meta meta;
    private final Links links;
}
