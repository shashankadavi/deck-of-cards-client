package com.sadavi.controller;

import com.sadavi.bean.DeckResponse;
import com.sadavi.bean.DrawCardResponse;
import com.sadavi.service.DeckOfCardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/deck")
public class DeckOfCardsClientController {

    DeckOfCardsService deckOfCardsService;

    @Autowired
    DeckOfCardsClientController(DeckOfCardsService deckOfCardsService){
        this.deckOfCardsService =   deckOfCardsService;
    }

    @GetMapping(value = "/new")
    public ResponseEntity<?> getNewDeck(){
        DeckResponse deckResponse = deckOfCardsService.getNewDeck();
        if(deckResponse == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(deckResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<DeckResponse> getNewDeckWithJokers(boolean jokersEnabled){
        DeckResponse deckResponse = deckOfCardsService.getNewDeckWithJokers(jokersEnabled);
        return new ResponseEntity<>(deckResponse, HttpStatus.CREATED);

    }

    @GetMapping(value = "/draw")
    public ResponseEntity<Object> drawCardsFromDeck(@RequestParam(defaultValue = "new") String deckId,
                                                    @RequestParam("count") Integer count){
        DrawCardResponse drawCardResponse = deckOfCardsService.drawCardsFromDeck(deckId, count);

        if(drawCardResponse == null){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drawCardResponse, HttpStatus.OK);
    }
}
