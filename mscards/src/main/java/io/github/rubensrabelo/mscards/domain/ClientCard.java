package io.github.rubensrabelo.mscards.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_client_card")
public class ClientCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card card;
    private BigDecimal creditLimit;

    public ClientCard() {
    }

    public ClientCard(String cpf, Card card, BigDecimal creditLimit) {
        this.cpf = cpf;
        this.card = card;
        this.creditLimit = creditLimit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BigDecimal getLimit() {
        return creditLimit;
    }

    public void setLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientCard that = (ClientCard) o;
        return id == that.id && Objects.equals(cpf, that.cpf) && Objects.equals(card, that.card) && Objects.equals(creditLimit, that.creditLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, card, creditLimit);
    }
}
