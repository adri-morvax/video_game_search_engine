package fr.lernejo.search.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
class ElasticSearchController {
    private final RestHighLevelClient client;

    ElasticSearchController(RestHighLevelClient client) {
        this.client = client;
    }

    @GetMapping("/api/games")
    ArrayList<Object> getGames(@RequestParam(name = "query") String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest().source(SearchSourceBuilder.searchSource().query(new QueryStringQueryBuilder(query)));
        SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);
        ArrayList responseList = new ArrayList();
        response.getHits().forEach(hit -> responseList.add(hit.getSourceAsMap()));
        return responseList;
    }
}
