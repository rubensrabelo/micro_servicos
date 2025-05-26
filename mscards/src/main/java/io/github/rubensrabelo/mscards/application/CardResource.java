package io.github.rubensrabelo.mscards.application;

import io.github.rubensrabelo.mscards.application.representation.CardSaveRequest;
import io.github.rubensrabelo.mscards.domain.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cards")
public class CardResource {

    private static final Logger log = LoggerFactory.getLogger(CardResource.class);

    private CardService service;

    public CardResource(CardService service) {
        this.service = service;
    }

    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de cartões");
        return "ok";
    }

    @PostMapping
    public ResponseEntity register(@RequestBody CardSaveRequest request) {
        Card card = request.toModel();
        service.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "income")
    public ResponseEntity getIncomeUntil(@RequestParam("income") long income) {
        List<Card> cards = service.getCardWithIncomeLessThanEqual(income);
        return ResponseEntity.ok(cards);
    }
}
