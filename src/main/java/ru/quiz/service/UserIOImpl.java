package ru.quiz.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserIOImpl implements UserIO {

    private static final int COUNT_OF_ATTEMPTS = 5;

    private PrintStream printStream;

    private Scanner scanner;

    public UserIOImpl(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void printLine(String line) {
        printStream.println(line);
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
                    printLine("Answer is a number from 1 to " + max);
                }
            } catch (NumberFormatException e) {
                printLine("Wrong number format!");
            }
        }
        throw new IllegalArgumentException("Too many wrong attempts: " + COUNT_OF_ATTEMPTS);
    }
}
