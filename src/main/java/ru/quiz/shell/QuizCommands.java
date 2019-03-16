package ru.quiz.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.quiz.domain.QuizResult;
import ru.quiz.service.runner.QuizRunner;
import ru.quiz.service.util.LanguageChanger;

import java.io.IOException;
import java.util.Locale;

@ShellComponent
@RequiredArgsConstructor
public class QuizCommands {

    private static final String EN = "EN";

    private static final String RU = "RU";

    private final QuizRunner quizRunner;

    private final LanguageChanger languageChanger;

    @ShellMethod("Start the quiz")
    public void startQuiz() {
        try {
            QuizResult quizResult = quizRunner.performQuiz();
            quizRunner.printResult(quizResult);
        } catch (IOException e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    @ShellMethod(value = "Set language: [EN, RU]", key = "set-lang")
    public void changeLanguage(@ShellOption(defaultValue = EN) String language) {
        switch (language) {
            case EN:
                languageChanger.changeLanguageToEnglish();
                break;
            case RU:
                languageChanger.changeLanguageToRussian();
                break;
            default:
                System.err.println(language + " is not supported yet");
        }
    }

    @ShellMethod(value = "Change language to custom language and country", key = "set-custom-lang")
    public void changeLanguage(@ShellOption String language,
                               @ShellOption String country) {
        languageChanger.changeLanguage(new Locale(language.toLowerCase(), country.toUpperCase()));
    }

}
