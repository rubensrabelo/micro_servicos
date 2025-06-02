package io.github.rubensrabelo.mscreditappraiser.application.exceptions;

public class MicroservicesCommunicationErrorException extends Exception {

    private Integer status;

    public MicroservicesCommunicationErrorException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
