package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.util.List;

public class FeedbackCustomerEvaluation {

    private List<ApprovedCard> cards;

    public FeedbackCustomerEvaluation(List<ApprovedCard> cards) {
        this.cards = cards;
    }

    public List<ApprovedCard> getCards() {
        return cards;
    }

    public void setCards(List<ApprovedCard> cards) {
        this.cards = cards;
    }
}
