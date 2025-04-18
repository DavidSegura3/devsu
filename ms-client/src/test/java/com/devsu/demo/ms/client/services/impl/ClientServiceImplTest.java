package com.devsu.demo.ms.client.services.impl;

import com.devsu.demo.ms.client.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.client.models.dtos.ClientDTO;
import com.devsu.demo.ms.client.models.dtos.ClientPasswordDTO;
import com.devsu.demo.ms.client.models.dtos.ContactInformationClientDTO;
import com.devsu.demo.ms.client.models.entities.Client;
import com.devsu.demo.ms.client.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper defaultMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveClientAndReturnDTO() {

        Client clientToSave = new Client();
        clientToSave.setPassword("password");
        String encodedPassword = "encodedPassword";
        Client savedClient = new Client();
        savedClient.setId(1L);
        ClientDTO expectedDTO = new ClientDTO();
        expectedDTO.setId(1L);

        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(clientRepository.save(clientToSave)).thenReturn(savedClient);
        when(defaultMapper.map(savedClient, ClientDTO.class)).thenReturn(expectedDTO);


        ClientDTO result = clientService.save(clientToSave);


        assertEquals(expectedDTO, result);
        assertEquals(encodedPassword, clientToSave.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(clientRepository, times(1)).save(clientToSave);
        verify(defaultMapper, times(1)).map(savedClient, ClientDTO.class);
    }

    @Test
    void findByClientId_shouldReturnClientDTOIfExists() {

        Long clientId = 1L;
        Client foundClient = new Client();
        foundClient.setId(1L);
        ClientDTO expectedDTO = new ClientDTO();
        expectedDTO.setId(1L);

        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(foundClient));
        when(defaultMapper.map(foundClient, ClientDTO.class)).thenReturn(expectedDTO);


        ClientDTO result = clientService.findByClientId(clientId);


        assertEquals(expectedDTO, result);
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(defaultMapper, times(1)).map(foundClient, ClientDTO.class);
    }

    @Test
    void findByClientId_shouldThrowResourceNotFoundExceptionIfClientDoesNotExist() {

        Long clientId = 1L;
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> clientService.findByClientId(clientId));
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(defaultMapper, never()).map(any(), any());
    }

    @Test
    void update_shouldUpdateClientAndReturnDTO() {

        Long clientId = 1L;
        ContactInformationClientDTO updateDTO = new ContactInformationClientDTO();
        updateDTO.setName("New Name");
        updateDTO.setGender("New Gender");
        updateDTO.setAddress("New Address");
        updateDTO.setPhone("New Phone");

        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setName("Old Name");
        existingClient.setGender("Old Gender");
        existingClient.setAddress("Old Address");
        existingClient.setPhone("Old Phone");

        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("New Name");
        updatedClient.setGender("New Gender");
        updatedClient.setAddress("New Address");
        updatedClient.setPhone("New Phone");

        ClientDTO expectedDTO = new ClientDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("New Name");
        expectedDTO.setGender("New Gender");
        expectedDTO.setAddress("New Address");
        expectedDTO.setPhone("New Phone");

        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(updatedClient);
        when(defaultMapper.map(updatedClient, ClientDTO.class)).thenReturn(expectedDTO);


        ClientDTO result = clientService.update(updateDTO, clientId);


        assertEquals(expectedDTO, result);
        assertEquals("New Name", existingClient.getName());
        assertEquals("New Gender", existingClient.getGender());
        assertEquals("New Address", existingClient.getAddress());
        assertEquals("New Phone", existingClient.getPhone());
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(clientRepository, times(1)).save(existingClient);
        verify(defaultMapper, times(1)).map(updatedClient, ClientDTO.class);
    }

    @Test
    void update_shouldThrowResourceNotFoundExceptionIfClientDoesNotExist() {

        Long clientId = 1L;
        ContactInformationClientDTO updateDTO = new ContactInformationClientDTO();
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> clientService.update(updateDTO, clientId));
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(clientRepository, never()).save(any());
        verify(defaultMapper, never()).map(any(), any());
    }

    @Test
    void updatePassword_shouldUpdatePasswordAndReturnDTO() {

        Long clientId = 1L;
        ClientPasswordDTO passwordDTO = new ClientPasswordDTO();
        passwordDTO.setPassword("newPassword");
        String encodedPassword = "encodedNewPassword";

        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setPassword("oldPassword");

        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setPassword(encodedPassword);

        ClientDTO expectedDTO = new ClientDTO();
        expectedDTO.setId(1L);

        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(existingClient));
        when(passwordEncoder.encode("newPassword")).thenReturn(encodedPassword);
        when(clientRepository.save(existingClient)).thenReturn(updatedClient);
        when(defaultMapper.map(updatedClient, ClientDTO.class)).thenReturn(expectedDTO);


        ClientDTO result = clientService.updatePassword(passwordDTO, clientId);


        assertEquals(expectedDTO, result);
        assertEquals(encodedPassword, existingClient.getPassword());
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(clientRepository, times(1)).save(existingClient);
        verify(defaultMapper, times(1)).map(updatedClient, ClientDTO.class);
    }

    @Test
    void updatePassword_shouldThrowResourceNotFoundExceptionIfClientDoesNotExist() {

        Long clientId = 1L;
        ClientPasswordDTO passwordDTO = new ClientPasswordDTO();
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> clientService.updatePassword(passwordDTO, clientId));
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(passwordEncoder, never()).encode(any());
        verify(clientRepository, never()).save(any());
        verify(defaultMapper, never()).map(any(), any());
    }

    @Test
    void deleteByClientId_shouldDeleteClientIfExists() {

        Long clientId = 1L;
        Client existingClient = new Client();
        existingClient.setId(1L);

        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.of(existingClient));


        clientService.deleteByClientId(clientId);


        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(clientRepository, times(1)).delete(existingClient);
    }

    @Test
    void deleteByClientId_shouldThrowResourceNotFoundExceptionIfClientDoesNotExist() {

        Long clientId = 1L;
        when(clientRepository.findByClientId(clientId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteByClientId(clientId));
        verify(clientRepository, times(1)).findByClientId(clientId);
        verify(clientRepository, never()).delete(any());
    }

    @Test
    void findAll_shouldReturnListOfClientDTOs() {

        List<Client> clientList = new ArrayList<>();
        Client client1 = new Client();
        client1.setId(1L);
        Client client2 = new Client();
        client2.setId(2L);
        clientList.add(client1);
        clientList.add(client2);

        ClientDTO dto1 = new ClientDTO();
        dto1.setId(1L);
        ClientDTO dto2 = new ClientDTO();
        dto2.setId(2L);
        List<ClientDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(dto1);
        expectedDTOList.add(dto2);

        when(clientRepository.findAll()).thenReturn(clientList);
        when(defaultMapper.map(client1, ClientDTO.class)).thenReturn(dto1);
        when(defaultMapper.map(client2, ClientDTO.class)).thenReturn(dto2);


        List<ClientDTO> result = clientService.findAll();


        assertEquals(expectedDTOList, result);
        verify(clientRepository, times(1)).findAll();
        verify(defaultMapper, times(1)).map(client1, ClientDTO.class);
        verify(defaultMapper, times(1)).map(client2, ClientDTO.class);
    }
}
