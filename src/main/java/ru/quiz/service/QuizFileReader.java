package ru.quiz.service;

import ru.quiz.entity.Question;

import java.io.IOException;
import java.util.List;

public interface QuizFileReader {

    List<Question> readAllQuestions() throws IOException;
}
