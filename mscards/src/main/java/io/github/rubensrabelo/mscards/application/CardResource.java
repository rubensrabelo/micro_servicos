package io.github.rubensrabelo.mscards.application;

import io.github.rubensrabelo.mscards.application.representation.CardSaveRequest;
import io.github.rubensrabelo.mscards.application.representation.CardsByCustomerResponse;
import io.github.rubensrabelo.mscards.domain.Card;
import io.github.rubensrabelo.mscards.domain.ClientCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cards")
public class CardResource {

    private static final Logger log = LoggerFactory.getLogger(CardResource.class);

    private final CardService service;
    private final ClientCardService clientCardService;

    public CardResource(CardService service, ClientCardService clientCardService) {
        this.service = service;
        this.clientCardService = clientCardService;
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

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CardsByCustomerResponse>> getCardsByCustomer(@RequestParam("cpf") String cpf) {
        List<ClientCard> result = clientCardService.listCardByCpf(cpf);
        List<CardsByCustomerResponse> resultLimit = result.stream()
                .map(CardsByCustomerResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultLimit);
    }
}
