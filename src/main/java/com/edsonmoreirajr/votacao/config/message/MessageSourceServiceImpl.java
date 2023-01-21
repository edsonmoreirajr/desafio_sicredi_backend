package com.edsonmoreirajr.votacao.config.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSourceServiceImpl implements MessageSourceService {

    private final MessageSource messageSource;

    @Override
    public String getMessage(String messageProperties) {
        return messageSource.getMessage(messageProperties, null, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String messageProperties, Object... args) {
        return messageSource.getMessage(messageProperties, args, LocaleContextHolder.getLocale());
    }
}
