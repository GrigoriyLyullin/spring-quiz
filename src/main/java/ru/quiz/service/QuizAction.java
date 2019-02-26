package ru.quiz.service;

import ru.quiz.entity.QuizResult;

import java.io.IOException;

public interface QuizAction {

    QuizResult performQuiz() throws IOException;

    void printResult(QuizResult quizResult);
}
