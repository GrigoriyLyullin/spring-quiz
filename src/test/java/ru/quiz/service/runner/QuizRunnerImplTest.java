package ru.quiz.service.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.quiz.domain.Question;
import ru.quiz.domain.QuizResult;
import ru.quiz.service.runner.QuizRunnerImpl;
import ru.quiz.service.util.QuizFileReader;
import ru.quiz.service.util.UserIO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizRunnerImplTest {

    private static final String LANGUAGE = "ru";

    private static final String COUNTRY = "RU";

    private QuizFileReader fileReader;

    private MessageSource messageSource;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = mock(QuizFileReader.class);
        Question question = new Question("Question?", Arrays.asList("correct answer", "wrong answer"), 1);
        when(fileReader.readAllQuestions()).thenReturn(Collections.singletonList(question));

        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/message");
        ms.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        messageSource = ms;
    }

    @Test
    void performQuizWithOneQuestionCorrectAnswer() throws IOException {
        UserIO userIO = mock(UserIO.class);
        // returns correct answer number
        when(userIO.readAnswer(2)).thenReturn(1);

        QuizRunnerImpl quizAction = new QuizRunnerImpl(userIO, fileReader, messageSource, LANGUAGE, COUNTRY);
        QuizResult quizResult = quizAction.performQuiz();

        assertEquals(1, quizResult.getCorrectAnswers());
        assertEquals(1, quizResult.getTotalQuestions());
    }

    @Test
    void performQuizWithOneQuestionIncorrectAnswer() throws IOException {
        UserIO userIO = mock(UserIO.class);
        // returns incorrect answer number
        when(userIO.readAnswer(1)).thenReturn(2);

        QuizRunnerImpl quizAction = new QuizRunnerImpl(userIO, fileReader, messageSource, LANGUAGE, COUNTRY);
        QuizResult quizResult = quizAction.performQuiz();

        assertEquals(0, quizResult.getCorrectAnswers());
        assertEquals(1, quizResult.getTotalQuestions());

        verify(userIO, atLeastOnce()).printLine("Викторина начинается!");
        verify(userIO, atLeastOnce()).printLine("Извините, ответ неверный.");
    }

    @Test
    void performQuizWithOneQuestionIncorrectAnswerEnglish() throws IOException {
        UserIO userIO = mock(UserIO.class);
        // returns incorrect answer number
        when(userIO.readAnswer(1)).thenReturn(2);

        QuizRunnerImpl quizAction = new QuizRunnerImpl(userIO, fileReader, messageSource, "en", "US");
        QuizResult quizResult = quizAction.performQuiz();

        assertEquals(0, quizResult.getCorrectAnswers());
        assertEquals(1, quizResult.getTotalQuestions());

        verify(userIO, atLeastOnce()).printLine("Quiz is starting now!");
        verify(userIO, atLeastOnce()).printLine("Sorry, but wrong answer.");
    }
}