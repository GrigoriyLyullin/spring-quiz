package ru.quiz.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserIOImplTest {

    @Test
    void printLine() {
        List<Integer> output = new ArrayList<>();

        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                output.add(b);
            }
        });

        new UserIOImpl(printStream, mock(InputStream.class)).printLine("test");

        byte[] byteArray = getBytes(output);
        String outputString = new String(byteArray);

        assertEquals("test\n", outputString);
    }

    @Test
    void readValidAnswer() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        int answer = new UserIOImpl(mock(PrintStream.class), in).readAnswer(1);
        assertEquals(1, answer);
    }

    @Test
    void readInvalidNumberFormatAnswer() {
        String separator = System.lineSeparator();
        String inputString = "a" + separator + "b" + separator + "c" + separator + "d" + separator + "e" + separator + "f";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        PrintStream printStream = mock(PrintStream.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new UserIOImpl(printStream, in).readAnswer(1);
        });
        verify(printStream, times(5)).println("Wrong number format!");
    }

    @Test
    void readAnswerBiggerThanMax() {
        String separator = System.lineSeparator();
        String inputString = "8" + separator + "7";
        ByteArrayInputStream in = new ByteArrayInputStream(inputString.getBytes());
        PrintStream printStream = mock(PrintStream.class);
        int max = 7;
        int answer = new UserIOImpl(printStream, in).readAnswer(max);

        verify(printStream, times(1)).println("Answer is a number from 1 to " + max);
        assertEquals(7, answer);
    }

    @Test
    void readAnswerWhenMaxIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new UserIOImpl(mock(PrintStream.class), mock(InputStream.class)).readAnswer(0));

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