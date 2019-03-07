package ru.quiz.service.runner;

import ru.quiz.domain.QuizResult;

import java.io.IOException;

public interface QuizRunner {

    QuizResult performQuiz() throws IOException;

    void printResult(QuizResult quizResult);
}
