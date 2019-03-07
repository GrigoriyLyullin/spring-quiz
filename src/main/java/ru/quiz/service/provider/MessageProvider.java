package ru.quiz.service.provider;

public interface MessageProvider {

    String getMessage(String messageName);

    String getMessage(String messageName, String[] parameters);

}
