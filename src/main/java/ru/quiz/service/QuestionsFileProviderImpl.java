package ru.quiz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class QuestionsFileProviderImpl implements QuestionsFileProvider {

    private final static String UNDERSCORE = "_";

    private final String language;

    private final String country;

    private final String baseName;

    private final String extension;

    public QuestionsFileProviderImpl(@Value("${language}") String language, @Value("${country}") String country,
                                     @Value("${question.file.base}") String baseName, @Value("${question.file.ext}") String extension) {
        this.language = language;
        this.country = country;
        this.baseName = baseName;
        this.extension = extension;
    }

    @Override
    public File getQuestionsFile() {
        File questionsFile = new File(baseName + UNDERSCORE + language + UNDERSCORE + country + extension);
        if (existsAndCanBeRead(questionsFile)) {
            return questionsFile;
        }
        questionsFile = new File(baseName + extension);
        if (existsAndCanBeRead(questionsFile)) {
            return questionsFile;
        }
        throw new IllegalArgumentException("File does not exist or cannot be read: " + questionsFile);
    }

    private boolean existsAndCanBeRead(File questionsFile) {
        return questionsFile.exists() && questionsFile.canRead();
    }
}
