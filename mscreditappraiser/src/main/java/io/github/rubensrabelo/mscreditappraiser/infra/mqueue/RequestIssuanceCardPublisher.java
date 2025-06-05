package io.github.rubensrabelo.mscreditappraiser.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.rubensrabelo.mscreditappraiser.domain.model.DataRequestIssueCard;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class RequestIssuanceCardPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueIssueCards;

    public RequestIssuanceCardPublisher(RabbitTemplate rabbitTemplate, Queue queueIssueCards) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueIssueCards = queueIssueCards;
    }

    public void requestCard(DataRequestIssueCard data) throws JsonProcessingException {
        var json = convertIntoJson(data);
        rabbitTemplate.convertAndSend(queueIssueCards.getName(), json);
    }

    private Object convertIntoJson(DataRequestIssueCard data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
}
