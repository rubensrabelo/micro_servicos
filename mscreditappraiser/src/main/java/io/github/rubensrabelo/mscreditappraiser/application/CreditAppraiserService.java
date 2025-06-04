package io.github.rubensrabelo.mscreditappraiser.application;

import ch.qos.logback.core.net.SyslogOutputStream;
import feign.Client;
import feign.FeignException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.ClientDataNotFoundException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.MicroservicesCommunicationErrorException;
import io.github.rubensrabelo.mscreditappraiser.domain.model.*;
import io.github.rubensrabelo.mscreditappraiser.infra.clients.CardResourceClient;
import io.github.rubensrabelo.mscreditappraiser.infra.clients.ClientResourceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditAppraiserService {

    private final ClientResourceClient clientResourceClient;
    private final CardResourceClient cardResourceClient;

    public CreditAppraiserService(ClientResourceClient clientResourceClient, CardResourceClient cardResourceClient) {
        this.clientResourceClient = clientResourceClient;
        this.cardResourceClient = cardResourceClient;
    }

    public ClientStatus getCustomerStatus(String cpf) throws ClientDataNotFoundException, MicroservicesCommunicationErrorException {
        try {
            ResponseEntity<ClientData> clientDataResponse = clientResourceClient.clientData(cpf);
            ResponseEntity<List<CardClient>> cardResponse = cardResourceClient.getCardsByCustomer(cpf);

            return ClientStatus
                    .builder()
                    .client(clientDataResponse.getBody())
                    .cards(cardResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(status == HttpStatus.NOT_FOUND.value())
                throw new ClientDataNotFoundException();
            throw new MicroservicesCommunicationErrorException(e.getMessage(), status);
        }
    }

    public FeedbackCustomerEvaluation performEvaluation(String cpf, long income) throws ClientDataNotFoundException, MicroservicesCommunicationErrorException {
        try {
            ResponseEntity<ClientData> clientDataResponse = clientResourceClient.clientData(cpf);
            ResponseEntity<List<Card>> cardsResponse = cardResourceClient.getIncomeUntil(income);

            List<Card> cards = cardsResponse.getBody();

            cards.forEach(System.out::println);

            var listApprovedCards = cards.stream().map(card -> {
                ClientData clientData = clientDataResponse.getBody();

                BigDecimal basicLimit = card.getBasicLimit();
                BigDecimal ageBD = BigDecimal.valueOf(clientData.age());
                var factor = ageBD.divide(BigDecimal.valueOf(10));
                BigDecimal approvedLimit = factor.multiply(basicLimit);

                ApprovedCard approved = new ApprovedCard();
                approved.setCard(card.getName());
                approved.setFlag(card.getFlag());
                approved.setApprovedLimit(approvedLimit);

                return approved;
            }).collect(Collectors.toList());

            return new FeedbackCustomerEvaluation(listApprovedCards);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(status == HttpStatus.NOT_FOUND.value())
                throw new ClientDataNotFoundException();
            throw new MicroservicesCommunicationErrorException(e.getMessage(), status);
        }
    }
}
