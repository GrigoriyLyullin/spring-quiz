package ru.quiz;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.quiz.domain.QuizResult;
import ru.quiz.service.runner.QuizRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class Main {


    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();

        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        QuizResult quizResult = quizRunner.performQuiz();
        quizRunner.printResult(quizResult);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/message");
        ms.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        return ms;
    }
}
