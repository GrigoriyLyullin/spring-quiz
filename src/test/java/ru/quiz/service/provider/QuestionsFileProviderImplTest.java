package ru.quiz.service.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionsFileProviderImplTest {

    private static final String LANGUAGE = "ru";

    private static final String COUNTRY = "RU";

    private LocaleProvider localeProvider;

    @BeforeEach
    void setUp() {
        localeProvider = new LocaleProviderImpl(LANGUAGE, COUNTRY);
    }

    @Test
    void getQuestionsFileWithLocale() {
        File questionsFile = new QuestionsFileProviderImpl("questions", ".csv", localeProvider)
                .getQuestionsFile();
        assertEquals("questions_ru_RU.csv", questionsFile.getPath());
    }

    @Test
    void getQuestionsFileWithoutLocale() {
        File questionsFile = new QuestionsFileProviderImpl("questions", ".csv",
                new LocaleProviderImpl("xx", COUNTRY)).getQuestionsFile();
        assertEquals("questions.csv", questionsFile.getPath());
    }
}