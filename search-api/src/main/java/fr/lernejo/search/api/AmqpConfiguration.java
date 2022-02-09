package fr.lernejo.search.api;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class AmqpConfiguration {

    @Bean
    Queue queue() {
        return new Queue("game_info", true);
    }
}
