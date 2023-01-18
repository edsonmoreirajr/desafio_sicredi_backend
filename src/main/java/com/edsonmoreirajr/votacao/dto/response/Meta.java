package com.edsonmoreirajr.votacao.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class Meta implements Serializable {

    private Long totalRecords;
    private Integer totalPages;
}
