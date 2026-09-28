package io.github.claus7777.game_crm;


import java.net.http.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import io.github.claus7777.game_crm.dto.GameSearchResult;


public class ApiService {
    private static final String BASE_GAMES_URL = "api.thegamesdb.net";

    private final RestTemplate restTemplate;
    private final String clientID;
    private final String clientSecret;

    private String cachedToken;
    private long tokenExpiresAt = 0L;

    public ApiService(RestTemplate restTemplate, 
        @Value("${api.client-id}") String clientId,
        @Value("${api.client-secret}") String clientSecret) {
            this.restTemplate = restTemplate;
            this.clientID = clientId;
            this.clientSecret = clientSecret;
    }

    public List<GameSearchResult> searchGamesByName(String query){
        if (query == null || query.isBlank()){
            return List.of();
        }

        String token = getAccessToken();

        HttpHeaders headers = new HttpHeaders();

    }

}
