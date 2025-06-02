package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.math.BigDecimal;

public record CardClient(
        String name,
        String flag,
        BigDecimal limitReleased
) {
}
