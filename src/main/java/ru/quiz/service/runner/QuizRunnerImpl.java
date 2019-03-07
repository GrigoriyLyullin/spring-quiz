package ru.quiz.service.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.quiz.domain.Question;
import ru.quiz.domain.QuizResult;
import ru.quiz.service.util.QuizFileReader;
import ru.quiz.service.util.UserIO;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service
public class QuizRunnerImpl implements QuizRunner {

    private final UserIO userIO;

    private final QuizFileReader fileReader;

    private final MessageSource messageSource;

    private final Locale locale;

    @Autowired
    public QuizRunnerImpl(UserIO userIO, QuizFileReader fileReader, MessageSource messageSource,
                          @Value("${language}") String language, @Value("${country}") String country) {
        this.userIO = userIO;
        this.fileReader = fileReader;
        this.messageSource = messageSource;
        this.locale = new Locale(language, country);
    }

    @Override
    public QuizResult performQuiz() throws IOException {
        String greeting = messageSource.getMessage("quiz.greeting", null, locale);
        userIO.printLine(greeting);
        List<Question> questions = fileReader.readAllQuestions();
        int size = questions.size();
        int correctAnswersCount = 0;
        for (int i = 0; i < size; i++) {
            int currentNumber = i + 1;

            String[] parameters = {String.valueOf(currentNumber), String.valueOf(size)};
            userIO.printLine(messageSource.getMessage("quiz.question.number", parameters, locale));

            if (askQuestion(questions.get(i))) {
                userIO.printLine(messageSource.getMessage("quiz.answer.correct", null, locale));
                correctAnswersCount++;
            } else {
                userIO.printLine(messageSource.getMessage("quiz.answer.wrong", null, locale));
            }

        }
        return new QuizResult(correctAnswersCount, size);
    }

    @Override
    public void printResult(QuizResult quizResult) {
        String[] parameters = {String.valueOf(quizResult.getCorrectAnswers()), String.valueOf(quizResult.getTotalQuestions())};
        userIO.printLine(messageSource.getMessage("quiz.answer.number", parameters, locale));
    }

    private boolean askQuestion(Question question) {
        String[] parameters = {question.getQuestion()};
        userIO.printLine(messageSource.getMessage("quiz.question", parameters, locale));
        List<String> answers = question.getAnswers();
        int size = answers.size();
        for (int i = 0; i < size; i++) {
            userIO.printLine("(" + (i + 1) + ") : " + answers.get(i));
        }
        int userAnswer = userIO.readAnswer(size);
        return userAnswer == question.getNumberOfCorrectAnswer();
    }
}
