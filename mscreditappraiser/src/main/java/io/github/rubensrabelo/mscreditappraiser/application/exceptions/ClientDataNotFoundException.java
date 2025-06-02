package io.github.rubensrabelo.mscreditappraiser.application.exceptions;

public class ClientDataNotFoundException extends Exception {

    public ClientDataNotFoundException() {
        super("Client data not found for the CPF provided.");
    }
}
