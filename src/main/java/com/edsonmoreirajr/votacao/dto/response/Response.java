package com.edsonmoreirajr.votacao.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class Response<T> implements Serializable {

    private final T data;
    private final Links links;
}
