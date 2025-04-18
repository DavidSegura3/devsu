package com.devsu.demo.ms.client.services;

import com.devsu.demo.ms.client.models.dtos.ClientDTO;
import com.devsu.demo.ms.client.models.dtos.ClientPasswordDTO;
import com.devsu.demo.ms.client.models.dtos.ContactInformationClientDTO;
import com.devsu.demo.ms.client.models.entities.Client;

import java.util.List;

public interface IClientService {

    ClientDTO save(Client client);
    ClientDTO findByClientId(Long clientId);
    ClientDTO update(ContactInformationClientDTO client, Long clientId);
    ClientDTO updatePassword(ClientPasswordDTO client, Long clientId);
    void deleteByClientId(Long clientId);
    List<ClientDTO> findAll();
}
