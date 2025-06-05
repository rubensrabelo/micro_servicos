package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class DataRequestIssueCard {

    private Long idCard;
    private String cpf;
    private String address;
    private BigDecimal limitReleased;

    public DataRequestIssueCard() {
    }

    public DataRequestIssueCard(Long idCard, String cpf, String address, BigDecimal limitReleased) {
        this.idCard = idCard;
        this.cpf = cpf;
        this.address = address;
        this.limitReleased = limitReleased;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLimitReleased() {
        return limitReleased;
    }

    public void setLimitReleased(BigDecimal limitReleased) {
        this.limitReleased = limitReleased;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DataRequestIssueCard that = (DataRequestIssueCard) o;
        return Objects.equals(idCard, that.idCard) && Objects.equals(cpf, that.cpf) && Objects.equals(address, that.address) && Objects.equals(limitReleased, that.limitReleased);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCard, cpf, address, limitReleased);
    }
}
