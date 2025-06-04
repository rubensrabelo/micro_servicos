package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ApprovedCard {

    private String card;
    private String flag;
    private BigDecimal approvedLimit;

    public ApprovedCard() {
    }

    public ApprovedCard(String card, String flag, BigDecimal approvedLimit) {
        this.card = card;
        this.flag = flag;
        this.approvedLimit = approvedLimit;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public BigDecimal getApprovedLimit() {
        return approvedLimit;
    }

    public void setApprovedLimit(BigDecimal approvedLimit) {
        this.approvedLimit = approvedLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ApprovedCard that = (ApprovedCard) o;
        return Objects.equals(card, that.card) && Objects.equals(flag, that.flag) && Objects.equals(approvedLimit, that.approvedLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card, flag, approvedLimit);
    }
}
