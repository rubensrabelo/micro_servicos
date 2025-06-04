package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Card {

    private Long id;
    private String name;
    private String flag;
    private BigDecimal basicLimit;

    public Card() {
    }

    public Card(Long id, String name, String flag, BigDecimal basicLimit) {
        this.id = id;
        this.name = name;
        this.flag = flag;
        this.basicLimit = basicLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public BigDecimal getBasicLimit() {
        return basicLimit;
    }

    public void setBasicLimit(BigDecimal basicLimit) {
        this.basicLimit = basicLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(name, card.name) && Objects.equals(flag, card.flag) && Objects.equals(basicLimit, card.basicLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flag, basicLimit);
    }
}
