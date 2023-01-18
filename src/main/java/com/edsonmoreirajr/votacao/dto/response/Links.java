package com.edsonmoreirajr.votacao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Links implements Serializable {

    @JsonProperty("self")
    private String self;

    @JsonProperty("first")
    private String first;

    @JsonProperty("prev")
    private String prev;

    @JsonProperty("next")
    private String next;

    @JsonProperty("last")
    private String last;
}
