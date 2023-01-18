package com.edsonmoreirajr.votacao.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringParameterValidatorUtils {

    private static final String CARACTERES_PERMITIDOS_PARA_NOMES_REGEX = "^[a-zA-Z0-9.+_\\-]+$";

    public static boolean contemCaracteresInvalidosParaNomes(String parameter) {
        return !Pattern.compile(CARACTERES_PERMITIDOS_PARA_NOMES_REGEX).matcher(parameter).find();
    }
}
