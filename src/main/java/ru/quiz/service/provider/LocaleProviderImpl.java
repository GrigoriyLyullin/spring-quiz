package ru.quiz.service.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleProviderImpl implements LocaleProvider {

    private final String language;

    private final String country;

    private final Locale locale;

    public LocaleProviderImpl(@Value("${language}") String language,
                              @Value("${country}") String country) {
        this.language = language;
        this.country = country;
        this.locale = new Locale(language, country);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
