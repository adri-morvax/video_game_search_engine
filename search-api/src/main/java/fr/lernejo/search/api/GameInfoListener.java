package fr.lernejo.search.api;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GameInfoListener {
    private final RestHighLevelClient client;
    public GameInfoListener(RestHighLevelClient client){
        this.client = client;
    }

    @RabbitListener(queues = "game_info")
    public void onMessage(String document,@Header("game_id") String id) throws IOException {
        IndexRequest request = new IndexRequest("games").id(id).source(document, XContentType.JSON);
        this.client.index(request, RequestOptions.DEFAULT);
    }
}

