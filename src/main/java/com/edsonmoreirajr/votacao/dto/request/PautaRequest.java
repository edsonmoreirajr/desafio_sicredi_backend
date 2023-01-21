package com.edsonmoreirajr.votacao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "{validation.pauta.titulo.notnull}")
    @NotBlank(message = "{validation.pauta.titulo.notblank}")
    @Pattern(regexp = "^[a-zA-Z0-9.+_\\- ]+$", message = "{validation.pauta.titulo.regex}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Titulo da pauta.")
    private String titulo;

    @Size(max = 400)
    @NotNull(message = "{validation.pauta.descricao.notnull}")
    @NotBlank(message = "{validation.pauta.descricao.notblank}")
    @Pattern(regexp = "^[a-zA-Z0-9.+_\\- ]+$", message = "{validation.pauta.descricao.regex}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Descrição da pauta.")
    private String descricao;

}
