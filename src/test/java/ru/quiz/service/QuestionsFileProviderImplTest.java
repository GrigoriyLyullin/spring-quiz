package ru.quiz.service;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionsFileProviderImplTest {

    @Test
    void getQuestionsFileWithLocale() {
        File questionsFile = new QuestionsFileProviderImpl("ru", "RU", "questions", ".csv")
                .getQuestionsFile();
        assertEquals("questions_ru_RU.csv", questionsFile.getPath());
    }

    @Test
    void getQuestionsFileWithoutLocale() {
        File questionsFile = new QuestionsFileProviderImpl("ru", "XX", "questions", ".csv")
                .getQuestionsFile();
        assertEquals("questions.csv", questionsFile.getPath());
    }
}