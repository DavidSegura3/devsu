package com.devsu.demo.ms.client.controllers;


import com.devsu.demo.ms.client.models.dtos.ClientDTO;
import com.devsu.demo.ms.client.models.dtos.ClientPasswordDTO;
import com.devsu.demo.ms.client.models.dtos.ContactInformationClientDTO;
import com.devsu.demo.ms.client.models.entities.Client;
import com.devsu.demo.ms.client.services.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

class ClientControllerTest {

    @Mock
    private IClientService clientService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void clientSave_shouldReturnCreatedResponseWithClientDTO() {

        Client client = new Client();
        ClientDTO savedClientDTO = new ClientDTO();
        savedClientDTO.setId(1L);
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("client", savedClientDTO);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.save(client)).thenReturn(savedClientDTO);


        ResponseEntity<Map<String, Object>> response = clientController.clientSave(client, bindingResult);


        assertEquals(CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(clientService, times(1)).save(client);
    }

    @Test
    void clientSave_shouldReturnBadRequestIfBindingResultHasErrors() {

        Client client = new Client();
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("client", "name", "Name is required"));
        BindingResult bindingResult = mock(BindingResult.class); // Create a mock BindingResult
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);


        ResponseEntity<Map<String, Object>> response = clientController.clientSave(client, bindingResult);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(clientService, never()).save(client);
    }

    @Test
    void findClient_shouldReturnOkResponseWithClientDTO() {

        Long clientId = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);

        when(clientService.findByClientId(clientId)).thenReturn(clientDTO);


        ResponseEntity<ClientDTO> response = clientController.findClient(clientId);


        assertEquals(OK, response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
        verify(clientService, times(1)).findByClientId(clientId);
    }

    @Test
    void findClients_shouldReturnOkResponseWithListOfClientDTOs() {

        List<ClientDTO> clientDTOList = new ArrayList<>();
        ClientDTO dto1 = new ClientDTO();
        dto1.setId(1L);
        ClientDTO dto2 = new ClientDTO();
        dto2.setId(2L);
        clientDTOList.add(dto1);
        clientDTOList.add(dto2);

        when(clientService.findAll()).thenReturn(clientDTOList);

        ResponseEntity<List<ClientDTO>> response = clientController.findClients();

        assertEquals(OK, response.getStatusCode());
        assertEquals(clientDTOList, response.getBody());
        verify(clientService, times(1)).findAll();
    }

    @Test
    void deleteClient_shouldReturnNoContentResponse() {
        Long clientId = 1L;

        ResponseEntity<Void> response = clientController.deleteClient(clientId);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(clientService, times(1)).deleteByClientId(clientId);
    }

    @Test
    void updateClient_shouldReturnOkResponseWithUpdatedClientDTO() {
        Long clientId = 1L;
        ContactInformationClientDTO updateDTO = new ContactInformationClientDTO();
        updateDTO.setName("New Name");
        ClientDTO updatedClientDTO = new ClientDTO();
        updatedClientDTO.setId(1L);
        updatedClientDTO.setName("New Name");
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("client", updatedClientDTO);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.update(updateDTO, clientId)).thenReturn(updatedClientDTO);

        ResponseEntity<Map<String, Object>> response = clientController.updateClient(updateDTO, bindingResult, clientId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(clientService, times(1)).update(updateDTO, clientId);
    }

    @Test
    void updateClient_shouldReturnBadRequestIfBindingResultHasErrors() {
        Long clientId = 1L;
        ContactInformationClientDTO updateDTO = new ContactInformationClientDTO();
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("client", "name", "Name is required"));
        BindingResult bindingResult = mock(BindingResult.class);  // Create a mock BindingResult
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        ResponseEntity<Map<String, Object>> response = clientController.updateClient(updateDTO, bindingResult, clientId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(clientService, never()).update(any(), anyLong());
    }

    @Test
    void updateClientPassword_shouldReturnOkResponseWithUpdatedClientDTO() {
        Long clientId = 1L;
        ClientPasswordDTO passwordDTO = new ClientPasswordDTO();
        passwordDTO.setPassword("newPassword");
        ClientDTO updatedClientDTO = new ClientDTO();
        updatedClientDTO.setId(1L);
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("client", updatedClientDTO);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(clientService.updatePassword(passwordDTO, clientId)).thenReturn(updatedClientDTO);

        ResponseEntity<Map<String, Object>> response = clientController.updateClientPassword(passwordDTO, bindingResult, clientId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(clientService, times(1)).updatePassword(passwordDTO, clientId);
    }

    @Test
    void updateClientPassword_shouldReturnBadRequestIfBindingResultHasErrors() {
        Long clientId = 1L;
        ClientPasswordDTO passwordDTO = new ClientPasswordDTO();
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("client", "password", "Password is required"));
        BindingResult bindingResult = mock(BindingResult.class); // Create a mock BindingResult
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        ResponseEntity<Map<String, Object>> response = clientController.updateClientPassword(passwordDTO, bindingResult, clientId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(clientService, never()).updatePassword(any(), anyLong());
    }
}
