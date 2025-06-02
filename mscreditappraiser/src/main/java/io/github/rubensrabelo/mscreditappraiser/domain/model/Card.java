package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.math.BigDecimal;

public record Card(
        Long id,
        String name,
        String flag,
        BigDecimal limitBasic
) {
}
