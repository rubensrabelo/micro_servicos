package io.github.rubensrabelo.mscreditappraiser.domain.model;

import java.util.Objects;

public class ProtocolRequestCard {
    String protocol;

    public ProtocolRequestCard() {
    }

    public ProtocolRequestCard(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolRequestCard that = (ProtocolRequestCard) o;
        return Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(protocol);
    }
}
