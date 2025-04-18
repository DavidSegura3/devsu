package com.devsu.demo.ms.client.repositories;


import com.devsu.demo.ms.client.models.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findByClientId(Long clientId);
}