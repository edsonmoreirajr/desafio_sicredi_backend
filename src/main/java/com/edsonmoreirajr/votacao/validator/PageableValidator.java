package com.edsonmoreirajr.votacao.validator;

import com.edsonmoreirajr.votacao.exception.InvalidArgumentRequestException;
import com.edsonmoreirajr.votacao.util.StringParameterValidatorUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageableValidator {

    public static void validaPaginaENomeColunasOrdenacaoDoPageable(Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new InvalidArgumentRequestException("Número da página deve ser maior igual a zero.");
        }

        pageable.getSort().get().forEach(order -> {
            if (StringParameterValidatorUtils.contemCaracteresInvalidosParaNomes(order.getProperty())) {
                throw new InvalidArgumentRequestException(order.getProperty() + " não é um nome válido para uma coluna de ordenação");
            }
        });

    }
}
