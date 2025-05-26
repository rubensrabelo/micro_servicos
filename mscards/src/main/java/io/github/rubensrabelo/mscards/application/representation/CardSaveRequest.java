package io.github.rubensrabelo.mscards.application.representation;

import io.github.rubensrabelo.mscards.domain.Card;
import io.github.rubensrabelo.mscards.domain.FlagCard;

import java.math.BigDecimal;

public record CardSaveRequest(
        String name,
        FlagCard flag,
        BigDecimal income,
        BigDecimal limit
) {

    public Card toModel() {
        return new Card(name, flag, income, limit);
    }
}
