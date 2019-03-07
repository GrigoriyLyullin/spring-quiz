package ru.quiz.service.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Locale;

@Component
public class QuestionsFileProviderImpl implements QuestionsFileProvider {

    private final static String UNDERSCORE = "_";

    private final LocaleProvider localeProvider;

    private final String baseName;

    private final String extension;

    public QuestionsFileProviderImpl(@Value("${question.file.base}") String baseName,
                                     @Value("${question.file.ext}") String extension,
                                     LocaleProvider localeProvider) {
        this.baseName = baseName;
        this.extension = extension;
        this.localeProvider = localeProvider;
    }

    @Override
    public File getQuestionsFile() {
        Locale locale = localeProvider.getLocale();
        String language = locale.getLanguage();
        String country = locale.getCountry();
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
