package com.devsu.demo.ms.client.controllers;


import com.devsu.demo.ms.client.exceptions.errors.ErrorMessage;
import com.devsu.demo.ms.client.models.dtos.ClientDTO;
import com.devsu.demo.ms.client.models.dtos.ClientPasswordDTO;
import com.devsu.demo.ms.client.models.dtos.ContactInformationClientDTO;
import com.devsu.demo.ms.client.models.entities.Client;
import com.devsu.demo.ms.client.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devsu.demo.ms.client.utils.ValidationError.createValidationErrorResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operations related to clients")
public class ClientController {

    private final IClientService clientService;
    @Operation(summary = "Create a new client", description = "Creates a new client with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client object to create", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Invalid client data provided", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> clientSave(@Valid @RequestBody Client client, BindingResult result) {

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("client", clientService.save(client));
        return new ResponseEntity<>(response, CREATED);
    }

    @Operation(summary = "Find a client by their ClientId", description = "Retrieves a client based on the provided client ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the client", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/client-id/{clientId}")
    public ResponseEntity<ClientDTO> findClient(@PathVariable Long clientId) {
        return new ResponseEntity<>(clientService.findByClientId(clientId), OK);
    }

    @Operation(summary = "Find clients", description = "Retrieves a list of clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the client", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> findClients() {
        return new ResponseEntity<>(clientService.findAll(), OK);
    }

    @Operation(summary = "Delete a client by their ClientId", description = "Deletes a client based on the provided client ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the client"),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/client-id/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteByClientId(clientId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Operation(summary = "Find a client by their clientId", description = "Retrieves a client based on the provided client ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the client", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/client-id/{clientId}")
    public ResponseEntity<Map<String, Object>> updateClient(@Valid @RequestBody ContactInformationClientDTO client, BindingResult result, @PathVariable Long clientId) {

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("client", clientService.update(client, clientId));
        return new ResponseEntity<>(response, OK);
    }

    @Operation(summary = "Find a client by their clientId", description = "Search for a client by  clientId and change its password if found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the client", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/password/client-id/{clientId}")
    public ResponseEntity<Map<String, Object>> updateClientPassword(@Valid @RequestBody ClientPasswordDTO client, BindingResult result, @PathVariable Long clientId) {

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("client", clientService.updatePassword(client, clientId));
        return new ResponseEntity<>(response, OK);
    }
}
