package com.devsu.demo.ms.account.ms_account.controllers;


import com.devsu.demo.ms.account.ms_account.exceptions.errors.ErrorMessage;
import com.devsu.demo.ms.account.ms_account.models.dtos.TransactionDetailDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Movement;
import com.devsu.demo.ms.account.ms_account.services.IMovementService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devsu.demo.ms.account.ms_account.utils.ValidateMovementType.validateMovementType;
import static com.devsu.demo.ms.account.ms_account.utils.ValidationError.createValidationErrorResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/movement")
@RequiredArgsConstructor
@Tag(name = "Movements", description = "Operations related to movements")
public class MovementController {

    private final IMovementService movementService;
    @Operation(summary = "Create a new movement", description = "Creates a new movement with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movement object to create", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Movement.class))),
            @ApiResponse(responseCode = "404", description = "Invalid movement data provided", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> movementSave(@Valid @RequestBody TransactionDetailDTO transactionDetailDTO, BindingResult result, @RequestParam String movementType) {

        validateMovementType(movementType);

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("movement", movementService.save(transactionDetailDTO, movementType));
        return new ResponseEntity<>(response, CREATED);
    }

    @Operation(summary = "Find a movement by their MovementId", description = "Retrieves a movement based on the provided movement ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the movement", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Movement.class))),
            @ApiResponse(responseCode = "404", description = "Movement not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/movement-id/{movementId}")
    public ResponseEntity<Movement> findMovement(@PathVariable Long movementId) {
        return new ResponseEntity<>(movementService.findById(movementId), OK);
    }

    @Operation(summary = "Find movements", description = "Retrieves a list of movements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the movement", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Movement.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/movements")
    public ResponseEntity<List<Movement>> findMovements() {
        return new ResponseEntity<>(movementService.findAll(), OK);
    }

    @Operation(summary = "Delete a movement by their MovementId", description = "Deletes a movement based on the provided movement ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the movement"),
            @ApiResponse(responseCode = "404", description = "Movement not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/movement-id/{movementId}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long movementId) {
        movementService.deleteById(movementId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
