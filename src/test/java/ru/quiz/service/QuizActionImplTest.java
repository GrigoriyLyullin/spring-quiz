package ru.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.quiz.entity.Question;
import ru.quiz.entity.QuizResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuizActionImplTest {

    private QuizFileReader fileReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = mock(QuizFileReader.class);
        Question question = new Question("Question?", Arrays.asList("correct answer", "wrong answer"), 1);
        when(fileReader.readAllQuestions()).thenReturn(Collections.singletonList(question));
    }

    @Test
    void performQuizWithOneQuestionCorrectAnswer() throws IOException {
        UserIO userIO = mock(UserIO.class);
        // returns correct answer number
        when(userIO.readAnswer(2)).thenReturn(1);

        QuizActionImpl quizAction = new QuizActionImpl(userIO, fileReader);
        QuizResult quizResult = quizAction.performQuiz();

        assertEquals(1, quizResult.getCorrectAnswers());
        assertEquals(1, quizResult.getTotalQuestions());
    }

    @Test
    void performQuizWithOneQuestionIncorrectAnswer() throws IOException {
        UserIO userIO = mock(UserIO.class);
        // returns incorrect answer number
        when(userIO.readAnswer(1)).thenReturn(2);

        QuizActionImpl quizAction = new QuizActionImpl(userIO, fileReader);
        QuizResult quizResult = quizAction.performQuiz();

        assertEquals(0, quizResult.getCorrectAnswers());
        assertEquals(1, quizResult.getTotalQuestions());
    }
}