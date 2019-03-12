package ru.quiz.service.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleProviderImpl implements LocaleProvider {

    private final Locale locale;

    public LocaleProviderImpl(@Value("${locale.language}") String language,
                              @Value("${locale.country}") String country) {
        this.locale = new Locale(language, country);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
