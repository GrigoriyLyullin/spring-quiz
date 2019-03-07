package ru.quiz.service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.quiz.service.provider.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserIOImplTest {

    private static final String LANGUAGE = "en";

    private static final String COUNTRY = "US";

    private InputOutputProvider inputOutputProvider;

    private PrintStream printStream;

    private MessageProvider messageProvider;

    private LocaleProvider localeProvider;

    @BeforeEach
    void setUp() {
        inputOutputProvider = mock(InputOutputProviderImpl.class);
        printStream = mock(PrintStream.class);
        messageProvider = mock(MessageProvider.class);
        localeProvider = new LocaleProviderImpl(LANGUAGE, COUNTRY);

        when(inputOutputProvider.getPrintStream()).thenReturn(printStream);
        when(inputOutputProvider.getInputStream()).thenReturn(mock(InputStream.class));

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/message");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());

        messageProvider = new MessageProviderImpl(messageSource, localeProvider);
    }

    @Test
    void printLine() {
        List<Integer> output = new ArrayList<>();

        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                output.add(b);
            }
        });

        when(inputOutputProvider.getPrintStream()).thenReturn(printStream);
        new UserIOImpl(inputOutputProvider, messageProvider).printLine("test");

        byte[] byteArray = getBytes(output);
        String outputString = new String(byteArray);

        assertEquals("test\n", outputString);
    }

    @Test
    void readValidAnswer() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        when(inputOutputProvider.getInputStream()).thenReturn(in);

        int answer = new UserIOImpl(inputOutputProvider, messageProvider).readAnswer(1);
        assertEquals(1, answer);
    }

    @Test
    void readInvalidNumberFormatAnswer() {
        String separator = System.lineSeparator();
        String inputString = "a" + separator + "b" + separator + "c" + separator + "d" + separator + "e" + separator + "f";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        when(inputOutputProvider.getInputStream()).thenReturn(in);

        assertThrows(IllegalArgumentException.class, () -> new UserIOImpl(inputOutputProvider, messageProvider).readAnswer(1));
        verify(printStream, times(5)).println("Wrong number format!");
    }

    @Test
    void readAnswerBiggerThanMax() {
        String separator = System.lineSeparator();
        String inputString = "8" + separator + "7";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        when(inputOutputProvider.getInputStream()).thenReturn(in);
        int max = 7;
        int answer = new UserIOImpl(inputOutputProvider, messageProvider).readAnswer(max);

        verify(printStream, times(1)).println("Answer is a number from 1 to " + max);
        assertEquals(7, answer);
    }

    @Test
    void readAnswerWhenMaxIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new UserIOImpl(inputOutputProvider, messageProvider).readAnswer(0));

    }

    private byte[] getBytes(List<Integer> output) {
        int[] outputArray = output.stream().mapToInt(i -> i).toArray();
        byte[] byteArray = new byte[outputArray.length];
        for (int i = 0; i < outputArray.length; i++) {
            byteArray[i] = (byte) outputArray[i];
        }
        return byteArray;
    }
}