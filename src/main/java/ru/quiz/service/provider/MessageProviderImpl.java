package ru.quiz.service.provider;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageProviderImpl implements MessageProvider {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public MessageProviderImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getMessage(String messageName) {
        return messageSource.getMessage(messageName, null, localeProvider.getLocale());
    }

    @Override
    public String getMessage(String messageName, String[] parameters) {
        return messageSource.getMessage(messageName, parameters, localeProvider.getLocale());
    }
}
