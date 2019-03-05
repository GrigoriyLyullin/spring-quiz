package ru.quiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.quiz.utils.IOType;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class InputOutputProviderImpl implements InputOutputProvider {

    private static final String FILE_OPTION_IS_UNSUPPORTED_NOW = "FILE option is unsupported";

    private static final String IS_INVALID_OPTION = "%s is invalid option!";

    private IOType input;

    private IOType output;

    public InputOutputProviderImpl(@Value("${user.input}") IOType input, @Value("${user.output}") IOType output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public PrintStream getPrintStream() {
        switch (output) {
            case CONSOLE:
                return System.out;
            case FILE:
                throw new UnsupportedOperationException(FILE_OPTION_IS_UNSUPPORTED_NOW);
            default:
                throw new IllegalArgumentException(String.format(IS_INVALID_OPTION, output));
        }
    }

    @Override
    public InputStream getInputStream() {
        switch (input) {
            case CONSOLE:
                return System.in;
            case FILE:
                throw new UnsupportedOperationException(FILE_OPTION_IS_UNSUPPORTED_NOW);
            default:
                throw new IllegalArgumentException(String.format(IS_INVALID_OPTION, input));
        }
    }
}
