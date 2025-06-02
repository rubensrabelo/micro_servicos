package io.github.rubensrabelo.mscreditappraiser.application;

import feign.FeignException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.ClientDataNotFoundException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.MicroservicesCommunicationErrorException;
import io.github.rubensrabelo.mscreditappraiser.domain.model.CardClient;
import io.github.rubensrabelo.mscreditappraiser.domain.model.ClientData;
import io.github.rubensrabelo.mscreditappraiser.domain.model.ClientStatus;
import io.github.rubensrabelo.mscreditappraiser.infra.clients.CardResourceClient;
import io.github.rubensrabelo.mscreditappraiser.infra.clients.ClientResourceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
