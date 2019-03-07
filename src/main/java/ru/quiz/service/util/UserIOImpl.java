package ru.quiz.service.util;

import org.springframework.stereotype.Service;
import ru.quiz.service.provider.InputOutputProvider;
import ru.quiz.service.provider.MessageProvider;

import java.util.Scanner;

@Service
public class UserIOImpl implements UserIO {

    private static final int COUNT_OF_ATTEMPTS = 5;

    private final InputOutputProvider inputOutputProvider;

    private final Scanner scanner;

    private final MessageProvider messageProvider;

    public UserIOImpl(InputOutputProvider inputOutputProvider, MessageProvider messageProvider) {
        this.inputOutputProvider = inputOutputProvider;
        this.scanner = new Scanner(inputOutputProvider.getInputStream());

        this.messageProvider = messageProvider;
    }

    @Override
    public void printLine(String line) {
        inputOutputProvider.getPrintStream().println(line);
    }

    @Override
    public int readAnswer(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("Max answer cannot be <= 0");
        }
        int answer;
        for (int i = 0; i < COUNT_OF_ATTEMPTS; i++) {
            try {
                String answerString = scanner.nextLine();
                answer = Integer.valueOf(answerString);
                if (answer > 0 && answer <= max) {
                    return answer;
                } else {
                    printLine(messageProvider.getMessage("quiz.message.answer.out.of.bounds",
                            new String[]{String.valueOf(max)}));
                }
            } catch (NumberFormatException e) {
                printLine(messageProvider.getMessage("quiz.message.wrong.number.format"));
            }
        }
        throw new IllegalArgumentException("Too many wrong attempts: " + COUNT_OF_ATTEMPTS);
    }
}
