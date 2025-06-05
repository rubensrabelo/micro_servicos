package io.github.rubensrabelo.mscreditappraiser.application;

import feign.FeignException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.CardRequestErrorException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.ClientDataNotFoundException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.MicroservicesCommunicationErrorException;
import io.github.rubensrabelo.mscreditappraiser.domain.model.*;
import io.github.rubensrabelo.mscreditappraiser.infra.clients.CardResourceClient;
import io.github.rubensrabelo.mscreditappraiser.infra.clients.ClientResourceClient;
import io.github.rubensrabelo.mscreditappraiser.infra.mqueue.RequestIssuanceCardPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreditAppraiserService {

    private final ClientResourceClient clientResourceClient;
    private final CardResourceClient cardResourceClient;
    private final RequestIssuanceCardPublisher issuanceCardPublisher;

    public CreditAppraiserService(
            ClientResourceClient clientResourceClient,
            CardResourceClient cardResourceClient,
            RequestIssuanceCardPublisher issuanceCardPublisher) {
        this.clientResourceClient = clientResourceClient;
        this.cardResourceClient = cardResourceClient;
        this.issuanceCardPublisher = issuanceCardPublisher;
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

    public ProtocolRequestCard requestIssueCard(DataRequestIssueCard data) {
        try {
            issuanceCardPublisher.requestCard(data);
            var protocol = UUID.randomUUID().toString();
            return new ProtocolRequestCard(protocol);
        } catch (Exception e) {
            throw new CardRequestErrorException(e.getMessage());
        }
    }
}
