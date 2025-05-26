package io.github.rubensrabelo.mscards.application;

import io.github.rubensrabelo.mscards.domain.Card;
import io.github.rubensrabelo.mscards.infra.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService {

    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Card save(Card card) {
        return repository.save(card);
    }

    public List<Card> getCardWithIncomeLessThanEqual(long income) {
        var incomeBigDecimal = BigDecimal.valueOf(income);
        return repository.findByIncomeLessThanEqual(incomeBigDecimal);
    }
}
