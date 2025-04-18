package com.devsu.demo.ms.account.ms_account.services.impl;

import com.devsu.demo.ms.account.ms_account.exceptions.business.ClientNotFoundException;
import com.devsu.demo.ms.account.ms_account.models.dtos.ClientDTO;
import com.devsu.demo.ms.account.ms_account.services.IClientService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    private final WebClient webClient;

    public ClientServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://ms-client:8000").build();
    }

    public Mono<List<ClientDTO>> findAllClients() {
        return webClient
                .get()
                .uri("/client/clients")
                .retrieve()
                .bodyToFlux(ClientDTO.class)
                .collectList();
    }

    @Override
    public Mono<ClientDTO> findByClientId(Long clientId) {
        return webClient
                .get()
                .uri("/client/client-id/{clientId}", clientId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new ClientNotFoundException("ms-client", clientId)))
                .bodyToMono(ClientDTO.class);
    }
}
