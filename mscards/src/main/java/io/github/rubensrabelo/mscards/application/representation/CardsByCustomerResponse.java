package io.github.rubensrabelo.mscards.application.representation;

import io.github.rubensrabelo.mscards.domain.ClientCard;

import java.math.BigDecimal;

public record CardsByCustomerResponse(
        String name,
        String flag,
        BigDecimal limitReleased
) {

    public static CardsByCustomerResponse fromModel(ClientCard card) {
        return new CardsByCustomerResponse(
                card.getCard().getName(),
                card.getCard().getFlag().toString(),
                card.getLimit()
        );
    }
}
