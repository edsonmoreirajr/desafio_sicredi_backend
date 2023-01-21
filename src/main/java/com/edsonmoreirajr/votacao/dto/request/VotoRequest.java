package com.edsonmoreirajr.votacao.dto.request;

import com.edsonmoreirajr.votacao.entity.enums.EnumVoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class VotoRequest {

    @NotNull(message = "{validation.voto.associadoid.notnull}")
    @Positive(message = "{validation.voto.associadoid.positive}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Identificador do associado gerado pelo banco de dados. Maior que zero.")
    private Long associadoId;
    @NotNull(message = "{validation.voto.sessaoid.notnull}")
    @Positive(message = "{validation.voto.sessaoid.positive}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Identificador da sessão gerado pelo banco de dados. Maior que zero.")
    private Long sessaoId;
    @NotNull(message = "{validation.voto.voto.notnull}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Voto do associado.")
    private EnumVoto voto;

}
