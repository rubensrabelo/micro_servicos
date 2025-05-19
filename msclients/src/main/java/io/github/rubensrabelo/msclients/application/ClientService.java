package io.github.rubensrabelo.msclients.application;

import io.github.rubensrabelo.msclients.domain.Client;
import io.github.rubensrabelo.msclients.infra.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Client save(Client client) {
        return repository.save(client);
    }

    public Optional<Client> getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
