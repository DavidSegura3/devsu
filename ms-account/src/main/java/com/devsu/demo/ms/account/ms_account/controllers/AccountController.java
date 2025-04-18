package com.devsu.demo.ms.account.ms_account.controllers;

import com.devsu.demo.ms.account.ms_account.exceptions.errors.ErrorMessage;
import com.devsu.demo.ms.account.ms_account.models.dtos.AccountDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.BalanceDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import com.devsu.demo.ms.account.ms_account.services.IAccountService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devsu.demo.ms.account.ms_account.utils.ValidationError.createValidationErrorResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Operations related to accounts")
public class AccountController {

    private final IAccountService accountService;
    @Operation(summary = "Create a new account", description = "Creates a new account with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account object to create", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "404", description = "Invalid account data provided", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> accountSave(@Valid @RequestBody Account account, BindingResult result) {

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("account", accountService.save(account));
        return new ResponseEntity<>(response, CREATED);
    }

    @Operation(summary = "Create a new account and associate client", description = "Creates a new account with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account object to create", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "404", description = "Invalid account data provided", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping("/client-id/{clientId}")
    public ResponseEntity<Map<String, Object>> createAccountAndAssociateToClient(@Valid @RequestBody Account account, BindingResult result, @PathVariable Long clientId) {

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("account", accountService.createAccountAndAssociateToClient(account, clientId));
        return new ResponseEntity<>(response, CREATED);
    }

    @Operation(summary = "Find a account by their account-number", description = "Retrieves a account based on the provided account number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the account", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/account-number/{accountNumber}")
    public ResponseEntity<AccountDTO> findAccount(@PathVariable Integer accountNumber) {
        return new ResponseEntity<>(accountService.findAccountNumber(accountNumber), OK);
    }

    @Operation(summary = "Find accounts", description = "Retrieves a list of accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the account", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> findAccounts() {
        return new ResponseEntity<>(accountService.findAll(), OK);
    }

    @Operation(summary = "Delete a account by their account-id", description = "Deletes a account based on the provided account ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the account"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/account-id/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteById(accountId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Operation(summary = "Find and modify account by their account-id", description = "Search for a account by  accountId and change its password if found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the account", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/balance/account-id/{accountId}")
    public ResponseEntity<Map<String, Object>> updateBalance(@Valid @RequestBody BalanceDTO account, BindingResult result, @PathVariable Long accountId) {

        if (result.hasErrors()) {
            return createValidationErrorResponse(result.getFieldErrors());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("account", accountService.updateBalance(account, accountId));
        return new ResponseEntity<>(response, OK);
    }
}
