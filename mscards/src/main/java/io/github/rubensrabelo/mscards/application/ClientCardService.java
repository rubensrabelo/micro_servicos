package io.github.rubensrabelo.mscards.application;

import io.github.rubensrabelo.mscards.domain.ClientCard;
import io.github.rubensrabelo.mscards.infra.repository.ClientCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientCardService {

    private final ClientCardRepository repository;

    public ClientCardService(ClientCardRepository repository) {
        this.repository = repository;
    }

    public List<ClientCard> listCardByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
