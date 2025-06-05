package io.github.rubensrabelo.mscreditappraiser.application.exceptions;

public class CardRequestErrorException extends RuntimeException{
    public CardRequestErrorException(String message) {
        super(message);
    }
}
