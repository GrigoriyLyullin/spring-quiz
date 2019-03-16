package ru.quiz.service.util;

import java.util.Locale;

public interface LanguageChanger {

    void changeLanguage(Locale locale);

    void changeLanguageToRussian();

    void changeLanguageToEnglish();
}
