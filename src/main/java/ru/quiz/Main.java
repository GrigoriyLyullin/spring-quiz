package ru.quiz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.quiz.entity.QuizResult;
import ru.quiz.service.QuizAction;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QuizAction quizAction = context.getBean(QuizAction.class);
        QuizResult quizResult = quizAction.performQuiz();
        quizAction.printResult(quizResult);
    }
}
