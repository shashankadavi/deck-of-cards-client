package com.sadavi.service;

import com.sadavi.bean.DeckResponse;
import com.sadavi.bean.DrawCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

@Service
public class DeckOfCardsService {

    @Autowired
    RestTemplate restTemplate;

    public DeckResponse getNewDeck(){
        String url = "https://deckofcardsapi.com/api/deck/new/";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity request = new HttpEntity(headers);


        ResponseEntity<DeckResponse> responseEntity =
        restTemplate.exchange(url,HttpMethod.GET,request,DeckResponse.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }

        return null;
    }

    //TODO: Postman/CURL returning 403 Forbidden CSRF Verification Failed for API Call
    public DeckResponse getNewDeckWithJokers( boolean jokersEnabled){
        String url = "https://deckofcardsapi.com/api/deck/new/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/54.0.2840.99 Safari/537.36");

        Map<String, Object> map = new HashMap<>();
        map.put("jokers_enabled", jokersEnabled);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<DeckResponse> response =
                restTemplate.exchange(url,HttpMethod.POST,entity,DeckResponse.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }


    public DrawCardResponse drawCardsFromDeck(String deckId, Integer count){
        String baseUrl = "https://deckofcardsapi.com/api/deck/";
        String endUrl = "/draw/";

        String url = baseUrl + deckId + endUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/54.0.2840.99 Safari/537.36");

        Map<String, Object> map = new HashMap<>();
        map.put("count", count);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity <DrawCardResponse> responseEntity =
                restTemplate.exchange(url,HttpMethod.GET,entity, DrawCardResponse.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }

        return null;
    }

}
