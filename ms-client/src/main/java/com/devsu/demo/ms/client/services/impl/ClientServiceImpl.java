package com.devsu.demo.ms.client.services.impl;

import com.devsu.demo.ms.client.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.client.models.dtos.ClientDTO;
import com.devsu.demo.ms.client.models.dtos.ClientPasswordDTO;
import com.devsu.demo.ms.client.models.dtos.ContactInformationClientDTO;
import com.devsu.demo.ms.client.models.entities.Client;
import com.devsu.demo.ms.client.repositories.ClientRepository;
import com.devsu.demo.ms.client.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private static final String CLIENT = "client";
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Qualifier("defaultMapper")
    private final ModelMapper defaultMapper;

    @Override
    public ClientDTO save(Client client) {
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        return convertClientToDto(clientRepository.save(client));
    }

    @Override
    public ClientDTO findByClientId(Long clientId) {
        return convertClientToDto(clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId)));
    }

    @Override
    public ClientDTO update(ContactInformationClientDTO client, Long clientId) {

        Client foundClient = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));

        foundClient.setName(client.getName());
        foundClient.setGender(client.getGender());
        foundClient.setAddress(client.getAddress());
        foundClient.setPhone(client.getPhone());
        return convertClientToDto(clientRepository.save(foundClient));
    }

    @Override
    public ClientDTO updatePassword(ClientPasswordDTO client, Long clientId) {

        Client foundClient = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));

        foundClient.setPassword(passwordEncoder.encode(client.getPassword()));
        return convertClientToDto(clientRepository.save(foundClient));
    }

    @Override
    public void deleteByClientId(Long clientId) {
        Client foundClient = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENT, clientId));
        clientRepository.delete(foundClient);
    }

    @Override
    public List<ClientDTO> findAll() {

        List<Client> clients = (List<Client>) clientRepository.findAll();
        return clients
                .stream()
                .map(this::convertClientToDto)
                .toList();
    }

    private ClientDTO convertClientToDto(Client client) {
        return defaultMapper.map(client, ClientDTO.class);
    }
}
