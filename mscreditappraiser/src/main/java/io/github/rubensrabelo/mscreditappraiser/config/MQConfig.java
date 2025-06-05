package io.github.rubensrabelo.mscreditappraiser.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.emissao-cartoes}")
    private String issuanceCardsQueue;

    @Bean
    Queue queueIssueCards() {
        return new Queue(issuanceCardsQueue, true);
    }
}
