package com.edsonmoreirajr.votacao.dto.request;

import com.edsonmoreirajr.votacao.entity.enums.EnumStatusAssociado;
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
import org.hibernate.validator.constraints.br.CPF;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoRequest {

    @Size(max = 200)
    @NotNull(message = "{validation.associado.nome.notnull}")
    @NotBlank(message = "{validation.associado.nome.notblank}")
    @Pattern(regexp = "^[a-zA-Z0-9.+_\\- ]+$", message = "{validation.associado.nome.regex}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do associado.")
    private String nome;

    @Size(max = 11)
    @NotNull(message = "{validation.associado.cpf.notnull}")
    @CPF(message = "{validation.associado.cpf}")
    @Pattern(regexp = "^[0-9]+$", message = "{validation.associado.cpf.regex}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Cpf do associado.")
    private String cpf;

    @NotNull(message = "{validation.associado.status.notnull}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Status do associado que define se ele está apto ou não a votar.")
    private EnumStatusAssociado status;
}
