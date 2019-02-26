package ru.quiz.service;

import org.junit.jupiter.api.Test;
import ru.quiz.entity.Question;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuizFileReaderImplTest {

    @Test
    void readAllQuestionsWhenFileIsNull() {
        QuizFileReaderImpl fileReader = new QuizFileReaderImpl(null);
        assertThrows(IllegalArgumentException.class, fileReader::readAllQuestions);
    }

    @Test
    void readAllQuestionsWhenFileCannotBeRead() {
        File file = new File("non-existent file");
        QuizFileReaderImpl fileReader = new QuizFileReaderImpl(file);
        assertThrows(IllegalArgumentException.class, fileReader::readAllQuestions);
    }

    @Test
    void readAllQuestionsMinimalQuestionWithOneAnswer() throws IOException {
        File tmpFile = Files.createTempFile("questions", "csv").toFile();
        try (PrintWriter out = new PrintWriter(tmpFile)) {
            out.println("Which is the largest planet in our solar system?,Mercury,1");
        }
        QuizFileReaderImpl fileReader = new QuizFileReaderImpl(tmpFile);
        List<Question> questions = fileReader.readAllQuestions();
        Question question = questions.get(0);
        List<String> answers = question.getAnswers();

        assertEquals(1, questions.size());
        assertEquals("Which is the largest planet in our solar system?", question.getQuestion());
        assertEquals(1, question.getNumberOfCorrectAnswer());
        assertEquals(1, answers.size());
        assertEquals("Mercury", answers.get(0));
    }
}