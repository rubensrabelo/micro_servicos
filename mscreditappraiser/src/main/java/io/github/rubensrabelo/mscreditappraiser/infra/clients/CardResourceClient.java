package io.github.rubensrabelo.mscreditappraiser.infra.clients;

import io.github.rubensrabelo.mscreditappraiser.domain.model.Card;
import io.github.rubensrabelo.mscreditappraiser.domain.model.CardClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscards", path = "/cards")
public interface CardResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CardClient>> getCardsByCustomer(@RequestParam("cpf") String cpf);

    @GetMapping(params = "income")
    ResponseEntity<List<Card>> getIncomeUntil(@RequestParam("income") Long income);
}
