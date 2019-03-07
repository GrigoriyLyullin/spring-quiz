package ru.quiz.service.provider;

import java.io.InputStream;
import java.io.PrintStream;

public interface InputOutputProvider {

    PrintStream getPrintStream();

    InputStream getInputStream();
}
