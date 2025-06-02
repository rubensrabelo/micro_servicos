package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.util.List;
import java.util.Objects;

public class ClientStatus {

    private ClientData client;
    private List<CardClient> cards;

    public ClientStatus() {
    }

    public ClientStatus(ClientData client, List<CardClient> cards) {
        this.client = client;
        this.cards = cards;
    }

    public ClientData getClient() {
        return client;
    }

    public void setClient(ClientData client) {
        this.client = client;
    }

    public List<CardClient> getCards() {
        return cards;
    }

    public void setCards(List<CardClient> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientStatus that = (ClientStatus) o;
        return Objects.equals(client, that.client) && Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, cards);
    }

    @Override
    public String toString() {
        return "ClientStatus{" +
                "client=" + client +
                ", cards=" + cards +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ClientData client;
        private List<CardClient> cards;

        public Builder client(ClientData client) {
            this.client = client;
            return this;
        }

        public Builder cards(List<CardClient> cards) {
            this.cards = cards;
            return this;
        }

        public ClientStatus build() {
            return new ClientStatus(client, cards);
        }
    }
}
