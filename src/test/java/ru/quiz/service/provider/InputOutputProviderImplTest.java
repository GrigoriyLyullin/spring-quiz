package ru.quiz.service.provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.quiz.domain.IOType;
import ru.quiz.service.provider.InputOutputProvider;
import ru.quiz.service.provider.InputOutputProviderImpl;

import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputOutputProviderImplTest {

    private InputOutputProvider inputOutputProvider;

    @BeforeEach
    void setUp() {
        inputOutputProvider = new InputOutputProviderImpl(IOType.CONSOLE, IOType.CONSOLE);
    }

    @Test
    void getPrintStream() {
        PrintStream printStream = inputOutputProvider.getPrintStream();
        assertSame(printStream, System.out);
    }

    @Test
    void getInputStream() {
        InputStream inputStream = inputOutputProvider.getInputStream();
        assertSame(inputStream, System.in);
    }
}