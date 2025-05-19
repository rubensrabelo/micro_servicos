package io.github.rubensrabelo.msclients.application;

import io.github.rubensrabelo.msclients.application.representation.ClientSaveRequest;
import io.github.rubensrabelo.msclients.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Provider;

@RestController
@RequestMapping("clients")
public class ClientResource {

    private static final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final ClientService service;

    public ClientResource(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClientSaveRequest request) {
        log.info("POST /clientes - Recebendo solicitação para salvar cliente: {}", request);
        var client = request.toModel();
        service.save(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(client.getCpf())
                .toUri();
        log.info("Cliente salvo com sucesso. URI de localização: {}", uri);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity clientData(@RequestParam("cpf") String cpf) {
        log.info("GET /clientes?cpf={} - Buscando dados do cliente", cpf);
        var client = service.getByCpf(cpf);
        if(client.isEmpty()) {
            log.warn("Cliente com CPF {} não encontrado", cpf);
            return ResponseEntity.notFound().build();
        }
        log.info("Cliente encontrado: {}", client);
        return ResponseEntity.ok(client);
    }
}
