package io.github.rubensrabelo.mscards.infra.repository.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubensrabelo.mscards.domain.Card;
import io.github.rubensrabelo.mscards.domain.ClientCard;
import io.github.rubensrabelo.mscards.domain.DataRequestIssueCard;
import io.github.rubensrabelo.mscards.infra.repository.CardRepository;
import io.github.rubensrabelo.mscards.infra.repository.ClientCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SubscriberCardIssuance {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberCardIssuance.class);

    private final CardRepository cardRepository;
    private final ClientCardRepository clientCardRepository;

    public SubscriberCardIssuance(CardRepository cardRepository, ClientCardRepository clientCardRepository) {
        this.cardRepository = cardRepository;
        this.clientCardRepository = clientCardRepository;
    }

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receiveRequestIssue(@Payload String payload) throws JsonProcessingException {
        try{
            var mapper = new ObjectMapper();

            DataRequestIssueCard data = mapper.readValue(payload, DataRequestIssueCard.class);
            Card card = cardRepository.findById(data.getIdCard()).orElseThrow();

            ClientCard clientCard = new ClientCard();
            clientCard.setCard(card);
            clientCard.setCpf(data.getCpf());
            clientCard.setLimit(data.getLimitReleased());

            clientCardRepository.save(clientCard);
        } catch (Exception e){
            logger.error("Error receiving card issuance request: {}", e.getMessage());
        }
    }
}
