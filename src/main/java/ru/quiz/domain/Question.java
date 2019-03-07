package ru.quiz.domain;

import java.util.List;

public class Question {

    private String question;

    private List<String> answers;

    private int numberOfCorrectAnswer;

    public Question(String question, List<String> answers, int numberOfCorrectAnswer) {
        this.question = question;
        this.answers = answers;
        this.numberOfCorrectAnswer = numberOfCorrectAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getNumberOfCorrectAnswer() {
        return numberOfCorrectAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", numberOfCorrectAnswer=" + numberOfCorrectAnswer +
                '}';
    }
}
