package ru.quiz.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.quiz.service.provider.LocaleProvider;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LanguageChangerImpl implements LanguageChanger {

    private static final Locale RU = new Locale("ru", "RU");

    private static final Locale EN = new Locale("en", "US");

    private final LocaleProvider localeProvider;


    @Override
    public void changeLanguage(Locale locale) {
        localeProvider.setLocale(locale);
    }

    @Override
    public void changeLanguageToRussian() {
        localeProvider.setLocale(RU);
    }

    @Override
    public void changeLanguageToEnglish() {
        localeProvider.setLocale(EN);
    }
}
