package io.github.rubensrabelo.mscreditappraiser.infra.clients;

import io.github.rubensrabelo.mscreditappraiser.domain.model.ClientData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclients", path = "/clients")
public interface ClientResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<ClientData> clientData(@RequestParam("cpf") String cpf);
}
