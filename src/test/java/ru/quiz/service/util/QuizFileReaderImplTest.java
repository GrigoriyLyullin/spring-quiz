package ru.quiz.service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.quiz.domain.Question;
import ru.quiz.service.provider.QuestionsFileProvider;
import ru.quiz.service.util.QuizFileReaderImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuizFileReaderImplTest {

    private QuestionsFileProvider questionsFileProvider;

    @BeforeEach
    void setUp() {
        questionsFileProvider = mock(QuestionsFileProvider.class);
    }

    @Test
    void readAllQuestionsWhenFileCannotBeRead() {
        File file = new File("non-existent file");
        when(questionsFileProvider.getQuestionsFile()).thenReturn(file);

        QuizFileReaderImpl fileReader = new QuizFileReaderImpl(questionsFileProvider);
        assertThrows(FileNotFoundException.class, fileReader::readAllQuestions);
    }

    @Test
    void readAllQuestionsMinimalQuestionWithOneAnswer() throws IOException {
        File tmpFile = Files.createTempFile("questions", "csv").toFile();
        try (PrintWriter out = new PrintWriter(tmpFile)) {
            out.println("Which is the largest planet in our solar system?,Mercury,1");
        }
        when(questionsFileProvider.getQuestionsFile()).thenReturn(tmpFile);

        QuizFileReaderImpl fileReader = new QuizFileReaderImpl(questionsFileProvider);
        List<Question> questions = fileReader.readAllQuestions();
        Question question = questions.get(0);
        List<String> answers = question.getAnswers();

        assertEquals(1, questions.size());
        assertEquals("Which is the largest planet in our solar system?", question.getQuestion());
        assertEquals(1, question.getNumberOfCorrectAnswer());
        assertEquals(1, answers.size());
        assertEquals("Mercury", answers.get(0));
        assertTrue(tmpFile.delete());
    }
}