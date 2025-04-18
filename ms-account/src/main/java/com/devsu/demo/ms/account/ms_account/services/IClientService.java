package com.devsu.demo.ms.account.ms_account.services;


import com.devsu.demo.ms.account.ms_account.models.dtos.ClientDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IClientService {

    Mono<List<ClientDTO>> findAllClients();
    Mono<ClientDTO> findByClientId(Long clientId);
}
