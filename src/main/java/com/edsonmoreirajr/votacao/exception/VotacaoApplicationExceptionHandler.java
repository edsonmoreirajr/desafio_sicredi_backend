package com.edsonmoreirajr.votacao.exception;

import com.edsonmoreirajr.votacao.dto.response.ErrorMessage;
import com.edsonmoreirajr.votacao.dto.response.ErrorMessages;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
@Slf4j
@ResponseBody
public class VotacaoApplicationExceptionHandler {

    private static final Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: \\[.+\\]");
    private static final Pattern ENUM_VALUES = Pattern.compile("\\[.+\\]");

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleBaseException(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getDescription(false));
        log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        return errorMessage;
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleBusinessException(RuntimeException ex, WebRequest request) {
        log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleAsNotFound(RuntimeException ex, WebRequest request) {
        log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            InvalidArgumentRequestException.class,
            jakarta.validation.ConstraintViolationException.class,
            org.hibernate.exception.ConstraintViolationException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleAsBadRequest(RuntimeException ex, WebRequest request) {
        log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessages handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> messages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            messages.add(error.getDefaultMessage());
        }
        log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage() + " [" + messages + "]");
        return new ErrorMessages(HttpStatus.BAD_REQUEST.value(), messages, request.getDescription(false));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorMessage handleJsonErrors(HttpMessageNotReadableException ex, WebRequest request) {
        if (ex.getCause() != null && ex.getCause() instanceof InvalidFormatException) {
            Matcher matchEnumMessage = ENUM_MSG.matcher(ex.getCause().getMessage());

            if (matchEnumMessage.find()) {
                Matcher matchEnumValues = ENUM_VALUES.matcher(ex.getCause().getMessage());
                String campo = ((InvalidFormatException) ex.getCause()).getPath().get(0).getFieldName();
                matchEnumValues.find();
                String message = "Valor inválido para o campo: " + campo + ". Os valores possiveis são: " + matchEnumValues.group(0);

                log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage() + " " + message);
                return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), message, request.getDescription(false));
            }
        }
        log.error(ex.getClass().getCanonicalName() + ": " + ex.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
    }

}
