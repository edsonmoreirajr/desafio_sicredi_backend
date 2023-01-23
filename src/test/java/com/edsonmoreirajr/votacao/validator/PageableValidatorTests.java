package com.edsonmoreirajr.votacao.validator;

import com.edsonmoreirajr.votacao.exception.entities.InvalidArgumentRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageableValidatorTests {

    private Pageable pageable;

    @Test
    void assertThatClassPageableValidator_DeveTerApenasUmConstrutorPrivado() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var constructor = PageableValidator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void naoDeveLancarExecption_QuandoNomeColunaValido() {
        var colunaComNomeValido = Sort.Order.desc("coluna com nome valido 9 - _");
        var sort = Sort.by(colunaComNomeValido);
        pageable = PageRequest.of(0, 10, sort);
        assertDoesNotThrow(() ->
                PageableValidator.validaPaginaENomeColunasOrdenacaoDoPageable(pageable));
    }

    @Test
    void deveLancarExecption_QuandoNomeColunaInvalido() {
        var colunaComNomeInvalido = Sort.Order.desc("colunainvalida#@");
        var sort = Sort.by(colunaComNomeInvalido);
        pageable = PageRequest.of(0, 10, sort);

        var exception = assertThrows(InvalidArgumentRequestException.class, () ->
                PageableValidator.validaPaginaENomeColunasOrdenacaoDoPageable(pageable));

        assertEquals(colunaComNomeInvalido.getProperty() + " não é um nome válido para uma coluna de ordenação.", exception.getMessage());
    }
}
