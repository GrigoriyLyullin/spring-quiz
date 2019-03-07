package ru.quiz.service.runner;

import org.springframework.stereotype.Service;
import ru.quiz.domain.Question;
import ru.quiz.domain.QuizResult;
import ru.quiz.service.provider.MessageProvider;
import ru.quiz.service.reader.QuizFileReader;
import ru.quiz.service.util.UserIO;

import java.io.IOException;
import java.util.List;

@Service
public class QuizRunnerImpl implements QuizRunner {

    private final UserIO userIO;

    private final QuizFileReader fileReader;

    private final MessageProvider messageProvider;

    public QuizRunnerImpl(UserIO userIO, QuizFileReader fileReader, MessageProvider messageProvider) {
        this.userIO = userIO;
        this.fileReader = fileReader;
        this.messageProvider = messageProvider;
    }

    @Override
    public QuizResult performQuiz() throws IOException {
        String greeting = messageProvider.getMessage("quiz.greeting");
        userIO.printLine(greeting);
        List<Question> questions = fileReader.readAllQuestions();
        int size = questions.size();
        int correctAnswersCount = 0;
        for (int i = 0; i < size; i++) {
            int currentNumber = i + 1;

            String[] parameters = {String.valueOf(currentNumber), String.valueOf(size)};
            userIO.printLine(messageProvider.getMessage("quiz.question.number", parameters));

            if (askQuestion(questions.get(i))) {
                userIO.printLine(messageProvider.getMessage("quiz.answer.correct"));
                correctAnswersCount++;
            } else {
                userIO.printLine(messageProvider.getMessage("quiz.answer.wrong"));
            }

        }
        return new QuizResult(correctAnswersCount, size);
    }

    @Override
    public void printResult(QuizResult quizResult) {
        String[] parameters = {String.valueOf(quizResult.getCorrectAnswers()), String.valueOf(quizResult.getTotalQuestions())};
        userIO.printLine(messageProvider.getMessage("quiz.answer.number", parameters));
    }

    private boolean askQuestion(Question question) {
        String[] parameters = {question.getQuestion()};
        userIO.printLine(messageProvider.getMessage("quiz.question", parameters));
        List<String> answers = question.getAnswers();
        int size = answers.size();
        for (int i = 0; i < size; i++) {
            userIO.printLine("(" + (i + 1) + ") : " + answers.get(i));
        }
        int userAnswer = userIO.readAnswer(size);
        return userAnswer == question.getNumberOfCorrectAnswer();
    }
}
