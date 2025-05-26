package io.github.rubensrabelo.mscards.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private FlagCard flag;
    private BigDecimal income;
    private BigDecimal basicLimit;

    public Card() {
    }

    public Card(String name, FlagCard flag, BigDecimal income, BigDecimal basicLimit) {
        this.name = name;
        this.flag = flag;
        this.income = income;
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

    public FlagCard getFlag() {
        return flag;
    }

    public void setFlag(FlagCard flag) {
        this.flag = flag;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
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
        return Objects.equals(id, card.id) && Objects.equals(name, card.name) && Objects.equals(flag, card.flag) && Objects.equals(income, card.income) && Objects.equals(basicLimit, card.basicLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flag, income, basicLimit);
    }
}
