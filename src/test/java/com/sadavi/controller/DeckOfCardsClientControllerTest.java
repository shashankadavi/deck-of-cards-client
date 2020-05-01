package com.sadavi.controller;

import com.sadavi.bean.Card;
import com.sadavi.bean.DeckResponse;
import com.sadavi.bean.DrawCardResponse;
import com.sadavi.service.DeckOfCardsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DeckOfCardsClientController.class)
public class DeckOfCardsClientControllerTest {

    MockMvc mockMvc;

    @Autowired
    DeckOfCardsClientController deckOfCardsClientController;

    @MockBean
    DeckOfCardsService deckOfCardsService;

    private DeckResponse mockDeckResponse;
    private DrawCardResponse mockCardResponse;
    private List<Card> cards;


    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(this.deckOfCardsClientController).build();

        mockDeckResponse = new DeckResponse();
        mockDeckResponse.setDeckId("1");
        mockDeckResponse.setShuffled(false);
        mockDeckResponse.setRemaining(2);
        mockDeckResponse.setSuccess(true);

        mockCardResponse = getDrawCardResponse();



    }

    @Test
    public void testGetNewDeck() throws Exception {

        when(deckOfCardsService.getNewDeck()).thenReturn(mockDeckResponse);

        mockMvc.perform(get("/deck/new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(mockDeckResponse.getSuccess()))
                .andExpect(jsonPath("$.deck_id").value(mockDeckResponse.getDeckId()))
                .andExpect(jsonPath("$.shuffled").value(mockDeckResponse.getShuffled()))
                .andExpect(jsonPath("$.remaining").value(mockDeckResponse.getRemaining()));

    }

    @Test
    public void testDrawCardsFromDeck() throws Exception {

        when(deckOfCardsService.drawCardsFromDeck(any(String.class), any(Integer.class)))
                .thenReturn(mockCardResponse);


        mockMvc.perform(get("/deck/draw/?deckId=new&count=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(mockCardResponse.getSuccess()))
                .andExpect(jsonPath("$.deck_id").value(mockCardResponse.getDeckId()))
                .andExpect(jsonPath("$.remaining").value(mockCardResponse.getRemaining()))
                .andExpect(jsonPath("$.cards.*", hasSize(2)));

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
