package com.edsonmoreirajr.votacao.validator;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringParameterValidatorTest {

    private static final String NOME_VALIDO = "nomevalido9+._-";
    private static final String NOME_INVALIDO = "nomeinvalido&";

    @Test
    void assertThatClassStringParameterValidator_DeveTerApenasUmConstrutorPrivado() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        var constructor = StringParameterValidator.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void deveRetornarFalse_SeOParametroForNomeValido() {
        assertFalse(StringParameterValidator.contemCaracteresInvalidosParaNomes(NOME_VALIDO));
    }

    @Test
    void deveRetornarVerdadeiro_SeParametroForNomeInvalido() {
        assertTrue(StringParameterValidator.contemCaracteresInvalidosParaNomes(NOME_INVALIDO));
    }
}
