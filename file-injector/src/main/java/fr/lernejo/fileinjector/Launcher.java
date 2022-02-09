package fr.lernejo.fileinjector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            Message[] games = new ObjectMapper().readValue(Paths.get(args[0]).toFile(), Message[].class);
            RabbitTemplate rabbitTemplate = springContext.getBean(RabbitTemplate.class);
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            for (Message game : games) {
                rabbitTemplate.convertAndSend("", "game_info", game, message -> {
                    message.getMessageProperties().getHeaders().put("game_id", game.id);
                    return message;
                });
            }
        }
    }
}

