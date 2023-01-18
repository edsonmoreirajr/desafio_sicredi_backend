package com.edsonmoreirajr.votacao.dto.request;

import com.edsonmoreirajr.votacao.entity.enums.StatusAssociadoEnum;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Nome do associado não deve ser nulo")
    private String nome;

    @Size(max = 11)
    @NotNull(message = "CPF do associado não deve ser nulo")
    @CPF
    private String cpf;

    @NotNull
    private StatusAssociadoEnum status;
}
