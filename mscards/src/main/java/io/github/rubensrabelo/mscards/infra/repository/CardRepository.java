package io.github.rubensrabelo.mscards.infra.repository;

import io.github.rubensrabelo.mscards.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByIncomeLessThanEqual(BigDecimal income);
}
