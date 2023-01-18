package com.edsonmoreirajr.votacao.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PautaRequest {

    @Size(max = 160)
    @NotNull(message = "O título da pauta não pode ser nulo.")
    private String titulo;

    @Size(max = 400)
    @NotNull(message = "Descrição da pauta não pode ser nula.")
    private String descricao;

}
