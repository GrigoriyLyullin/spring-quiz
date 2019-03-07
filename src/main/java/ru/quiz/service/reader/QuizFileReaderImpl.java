package ru.quiz.service.reader;

import org.springframework.stereotype.Service;
import ru.quiz.domain.Question;
import ru.quiz.service.provider.QuestionsFileProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizFileReaderImpl implements QuizFileReader {

    private static final String COMMA_SEPARATOR = ",";

    private final QuestionsFileProvider questionsFileProvider;

    public QuizFileReaderImpl(QuestionsFileProvider questionsFileProvider) {
        this.questionsFileProvider = questionsFileProvider;
    }

    @Override
    public List<Question> readAllQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(questionsFileProvider.getQuestionsFile()));
        String line = reader.readLine();
        while (line != null) {
            questions.add(parseQuestionString(line));
            line = reader.readLine();
        }
        return questions;
    }

    private Question parseQuestionString(String questionString) {
        String[] parts = questionString.split(COMMA_SEPARATOR);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid question string");
        }
        String question = null;
        List<String> answers = new ArrayList<>();
        int numberOfCorrectAnswer = 0;
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                question = parts[i];
            } else if (i < parts.length - 1) {
                answers.add(parts[i]);
            } else {
                numberOfCorrectAnswer = Integer.valueOf(parts[i]);
            }
        }
        return new Question(question, answers, numberOfCorrectAnswer);
    }
}
