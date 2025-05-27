package io.github.rubensrabelo.mscards.domain;

import java.math.BigDecimal;

public record DataRequestIssueCard(
        Long idCard,
        String cpf,
        String address,
        BigDecimal limitReleased
) {
}
