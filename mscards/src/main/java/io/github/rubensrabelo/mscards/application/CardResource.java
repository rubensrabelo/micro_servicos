package io.github.rubensrabelo.mscards.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cards")
public class CardResource {

    private static final Logger log = LoggerFactory.getLogger(CardResource.class);

    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de cartões");
        return "ok";
    }
}
