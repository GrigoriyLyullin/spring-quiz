package ru.quiz.service;

import ru.quiz.entity.Question;
import ru.quiz.entity.QuizResult;

import java.io.IOException;
import java.util.List;

public class QuizActionImpl implements QuizAction {

    private UserIO userIO;

    private QuizFileReader fileReader;

    public QuizActionImpl(UserIO userIO, QuizFileReader fileReader) {
        this.userIO = userIO;
        this.fileReader = fileReader;
    }

    @Override
    public QuizResult performQuiz() throws IOException {
        userIO.printLine("Quiz is starting now!");
        List<Question> questions = fileReader.readAllQuestions();
        int size = questions.size();
        int correctAnswersCount = 0;
        for (int i = 0; i < size; i++) {
            userIO.printLine("Question # " + (i + 1) + " from " + size);
            if (askQuestion(questions.get(i))) {
                userIO.printLine("Correct!");
                correctAnswersCount++;
            } else {
                userIO.printLine("Sorry, but wrong answer.");
            }

        }
        return new QuizResult(correctAnswersCount, size);
    }

    @Override
    public void printResult(QuizResult quizResult) {
        userIO.printLine("Correct answers " + quizResult.getCorrectAnswers() + " from " + quizResult.getTotalQuestions());
    }

    private boolean askQuestion(Question question) {
        userIO.printLine("Question: " + question.getQuestion());
        List<String> answers = question.getAnswers();
        int size = answers.size();
        for (int i = 0; i < size; i++) {
            userIO.printLine("(" + (i + 1) + ") : " + answers.get(i));
        }
        int userAnswer = userIO.readAnswer(size);
        return userAnswer == question.getNumberOfCorrectAnswer();
    }
}
