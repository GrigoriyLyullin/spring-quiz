package ru.quiz.shell;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.quiz.service.runner.QuizRunner;
import ru.quiz.service.util.LanguageChanger;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
class QuizCommandsTest {

    @MockBean
    QuizRunner quizRunner;

    @MockBean
    LanguageChanger languageChanger;

    @Autowired
    QuizCommands quizCommands;

    @Test
    void startQuiz() throws IOException {
        doThrow(IOException.class).when(quizRunner).performQuiz();
        quizCommands.startQuiz();
        verify(quizRunner, never()).printResult(any());
    }

    @Test
    void changeLanguage() {
        quizCommands.changeLanguage("RU");
        verify(languageChanger, atLeastOnce()).changeLanguageToRussian();
    }
}