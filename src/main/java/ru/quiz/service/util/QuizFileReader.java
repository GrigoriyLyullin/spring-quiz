package ru.quiz.service.util;

import ru.quiz.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuizFileReader {

    List<Question> readAllQuestions() throws IOException;
}
