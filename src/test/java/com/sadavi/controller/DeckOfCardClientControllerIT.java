package com.sadavi.controller;

import com.sadavi.bean.DeckResponse;
import com.sadavi.service.DeckOfCardsService;
import com.sadavi.spring.DeckOfCardsClient;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DeckOfCardsClientController.class,
        DeckOfCardsService.class, DeckOfCardsClient.class})
public class DeckOfCardClientControllerIT {
    MockMvc mockMvc;

    @Autowired
    DeckOfCardsClientController deckOfCardsClientController;

    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(this.deckOfCardsClientController).build();
    }

    @Test
    public void testGetNewDeck() throws Exception {
        mockMvc.perform(get("/deck/new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.deck_id", is(notNullValue())))
                .andExpect(jsonPath("$.shuffled").value(false))
                .andExpect(jsonPath("$.remaining").value(52));
    }

    @Test
    public void testDrawCardsFromDeck() throws Exception {
        mockMvc.perform(get("/deck/draw/?deckId=new&count=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.deck_id").value(is(notNullValue())))
                .andExpect(jsonPath("$.remaining").value(51))
                .andExpect(jsonPath("$.cards.*", hasSize(1)));
    }

}
