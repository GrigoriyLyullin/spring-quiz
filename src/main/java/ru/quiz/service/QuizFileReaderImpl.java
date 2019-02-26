package ru.quiz.service;

import ru.quiz.entity.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizFileReaderImpl implements QuizFileReader {

    private static final String COMMA_SEPARATOR = ",";

    private File questionsFile;

    public QuizFileReaderImpl(File questionsFile) {
        this.questionsFile = questionsFile;
    }

    @Override
    public List<Question> readAllQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        if (questionsFile != null && questionsFile.exists() && questionsFile.canRead()) {

            BufferedReader reader = new BufferedReader(new FileReader(questionsFile));
            String line = reader.readLine();
            while (line != null) {
                questions.add(parseQuestionString(line));
                line = reader.readLine();
            }
        } else {
            throw new IllegalArgumentException("File does not exist or cannot be read");
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
