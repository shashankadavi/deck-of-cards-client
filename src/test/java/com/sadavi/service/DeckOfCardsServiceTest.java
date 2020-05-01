package com.sadavi.service;

import com.sadavi.bean.Card;
import com.sadavi.bean.DeckResponse;
import com.sadavi.bean.DrawCardResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DeckOfCardsService.class)
public class DeckOfCardsServiceTest {
    @Autowired
    DeckOfCardsService deckOfCardsService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetNewDeck(){
        DeckResponse mockDeckResponse = getTemplateDeckResponse();

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class),
                any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(mockDeckResponse, HttpStatus.OK));

        DeckResponse deckResponse = deckOfCardsService.getNewDeck();

        assertEquals(deckResponse.getDeckId(), mockDeckResponse.getDeckId());
        assertEquals(deckResponse.getShuffled(), mockDeckResponse.getShuffled());
        assertEquals(deckResponse.getRemaining(), mockDeckResponse.getRemaining());
        assertEquals(deckResponse.getSuccess(), mockDeckResponse.getSuccess());

    }

    @Test
    public void testDrawCardsFromDeckReturnsCard(){
        DrawCardResponse drawCardResponse = getDrawCardResponse();

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class),
                any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(drawCardResponse, HttpStatus.OK));

        DrawCardResponse cardResponse = getDrawCardResponse();

        assertEquals(cardResponse.getSuccess(), drawCardResponse.getSuccess());
        assertEquals(cardResponse.getDeckId(), drawCardResponse.getDeckId());
        assertEquals(cardResponse.getCards().size(), drawCardResponse.getCards().size());
        assertEquals(cardResponse.getRemaining(), drawCardResponse.getRemaining());

    }

    private DeckResponse getTemplateDeckResponse(){
        DeckResponse mockDeckResponse = new DeckResponse();
        mockDeckResponse.setSuccess(true);
        mockDeckResponse.setDeckId("1");
        mockDeckResponse.setRemaining(52);
        mockDeckResponse.setShuffled(false);

        return mockDeckResponse;
    }

    private DrawCardResponse getDrawCardResponse(){
        DrawCardResponse cardResponse = new DrawCardResponse();
        List<Card> cards = getCards();

        cardResponse.setSuccess(true);
        cardResponse.setCards(cards);
        cardResponse.setDeckId("1");
        cardResponse.setRemaining(12);

        return cardResponse;
    }

    private List<Card> getCards(){
        List<Card> cards = new ArrayList();

        Card a = new Card();
        Card b = new Card();

        a.setCode("1");
        a.setImage("IMG1-Format1");
        a.setSuit("A");
        a.setValue("2");

        b.setCode("e");
        b.setImage("IMG2-Format1");
        b.setSuit("B");
        b.setValue("3");

        cards.add(a);
        cards.add(b);

        return cards;
    }
}
