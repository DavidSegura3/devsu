package com.devsu.demo.ms.account.ms_account.services.impl;

import com.devsu.demo.ms.account.ms_account.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.account.ms_account.models.dtos.AccountDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.BalanceDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.ClientDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import com.devsu.demo.ms.account.ms_account.repositories.AccountRepository;
import com.devsu.demo.ms.account.ms_account.services.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private IClientService clientService;

    @Mock
    private ModelMapper defaultMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveAccountAndReturnDTO() {

        Account accountToSave = new Account();
        Account savedAccount = new Account();
        savedAccount.setId(1L);
        AccountDTO expectedDTO = new AccountDTO();
        expectedDTO.setId(1L);

        when(accountRepository.save(accountToSave)).thenReturn(savedAccount);
        when(defaultMapper.map(savedAccount, AccountDTO.class)).thenReturn(expectedDTO);


        AccountDTO result = accountService.save(accountToSave);


        assertEquals(expectedDTO, result);
        verify(accountRepository, times(1)).save(accountToSave);
        verify(defaultMapper, times(1)).map(savedAccount, AccountDTO.class);
    }

    @Test
    void findAccountNumber_shouldReturnAccountDTOIfExists() {

        Integer accountNumber = 12345;
        Account foundAccount = new Account();
        foundAccount.setId(1L);
        AccountDTO expectedDTO = new AccountDTO();
        expectedDTO.setId(1L);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(foundAccount));
        when(defaultMapper.map(foundAccount, AccountDTO.class)).thenReturn(expectedDTO);


        AccountDTO result = accountService.findAccountNumber(accountNumber);


        assertEquals(expectedDTO, result);
        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(defaultMapper, times(1)).map(foundAccount, AccountDTO.class);
    }

    @Test
    void findAccountNumber_shouldThrowResourceNotFoundExceptionIfAccountDoesNotExist() {

        Integer accountNumber = 12345;
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> accountService.findAccountNumber(accountNumber));
        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(defaultMapper, never()).map(any(), any());
    }

    @Test
    void updateBalance_shouldUpdateBalanceAndReturnDTO() {

        Long accountId = 1L;
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setInitialBalance(BigDecimal.TEN);

        Account foundAccount = new Account();
        foundAccount.setId(1L);
        foundAccount.setInitialBalance(BigDecimal.valueOf(100));

        Account updatedAccount = new Account();
        updatedAccount.setId(1L);
        updatedAccount.setInitialBalance(BigDecimal.valueOf(110));

        AccountDTO expectedDTO = new AccountDTO();
        expectedDTO.setId(1L);
        expectedDTO.setInitialBalance(BigDecimal.valueOf(110));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(foundAccount));
        when(accountRepository.save(foundAccount)).thenReturn(updatedAccount);
        when(defaultMapper.map(updatedAccount, AccountDTO.class)).thenReturn(expectedDTO);


        AccountDTO result = accountService.updateBalance(balanceDTO, accountId);


        assertEquals(expectedDTO, result);
        assertEquals(BigDecimal.valueOf(110), foundAccount.getInitialBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(foundAccount);
        verify(defaultMapper, times(1)).map(updatedAccount, AccountDTO.class);
    }

    @Test
    void updateBalance_shouldThrowResourceNotFoundExceptionIfAccountDoesNotExist() {

        Long accountId = 1L;
        BalanceDTO balanceDTO = new BalanceDTO();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> accountService.updateBalance(balanceDTO, accountId));
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).save(any());
        verify(defaultMapper, never()).map(any(), any());
    }

    @Test
    void deleteById_shouldDeleteAccountIfExists() {

        Long accountId = 1L;
        Account existingAccount = new Account();
        existingAccount.setId(1L);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));


        accountService.deleteById(accountId);


        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).delete(existingAccount);
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundExceptionIfAccountDoesNotExist() {

        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> accountService.deleteById(accountId));
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, never()).delete(any());
    }

    @Test
    void findAll_shouldReturnListOfAccountDTOs() {

        List<Account> accountList = new ArrayList<>();
        Account account1 = new Account();
        account1.setId(1L);
        Account account2 = new Account();
        account2.setId(2L);
        accountList.add(account1);
        accountList.add(account2);

        AccountDTO dto1 = new AccountDTO();
        dto1.setId(1L);
        AccountDTO dto2 = new AccountDTO();
        dto2.setId(2L);
        List<AccountDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(dto1);
        expectedDTOList.add(dto2);

        when(accountRepository.findAll()).thenReturn(accountList);
        when(defaultMapper.map(account1, AccountDTO.class)).thenReturn(dto1);
        when(defaultMapper.map(account2, AccountDTO.class)).thenReturn(dto2);


        List<AccountDTO> result = accountService.findAll();


        assertEquals(expectedDTOList, result);
        verify(accountRepository, times(1)).findAll();
        verify(defaultMapper, times(1)).map(account1, AccountDTO.class);
        verify(defaultMapper, times(1)).map(account2, AccountDTO.class);
    }

    @Test
    void createAccountAndAssociateToClient_shouldSaveAccountAndReturnDTO() {

        Account accountToSave = new Account();
        accountToSave.setId(1L);
        Long clientId = 1L;
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(clientId);

        Account savedAccount = new Account();
        savedAccount.setId(1L);
        AccountDTO expectedDTO = new AccountDTO();
        expectedDTO.setId(1L);
        expectedDTO.setClient(clientDTO);

        when(clientService.findByClientId(clientId)).thenReturn(Mono.just(clientDTO));
        when(accountRepository.save(accountToSave)).thenReturn(savedAccount);
        when(defaultMapper.map(savedAccount, AccountDTO.class)).thenReturn(expectedDTO);


        AccountDTO result = accountService.createAccountAndAssociateToClient(accountToSave, clientId);


        assertEquals(expectedDTO, result);
        assertEquals(clientDTO, result.getClient());
        verify(clientService, times(1)).findByClientId(clientId);
        verify(accountRepository, times(1)).save(accountToSave);
        verify(defaultMapper, times(1)).map(savedAccount, AccountDTO.class);
    }
}
