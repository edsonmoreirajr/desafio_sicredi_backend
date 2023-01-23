package com.edsonmoreirajr.votacao.validator;

import com.edsonmoreirajr.votacao.exception.entities.InvalidArgumentRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageableValidator {

    public static void validaPaginaENomeColunasOrdenacaoDoPageable(Pageable pageable) {
        pageable.getSort().get().forEach(order -> {
            if (StringParameterValidator.contemCaracteresInvalidosParaNomes(order.getProperty())) {
                throw new InvalidArgumentRequestException(order.getProperty() + " não é um nome válido para uma coluna de ordenação.");
            }
        });
    }
}
