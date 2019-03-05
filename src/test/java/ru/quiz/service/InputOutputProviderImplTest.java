package ru.quiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.quiz.utils.IOType;

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