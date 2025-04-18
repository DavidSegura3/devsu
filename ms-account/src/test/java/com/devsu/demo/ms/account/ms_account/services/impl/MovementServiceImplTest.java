package com.devsu.demo.ms.account.ms_account.services.impl;

import com.devsu.demo.ms.account.ms_account.exceptions.business.InsufficientBalanceException;
import com.devsu.demo.ms.account.ms_account.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.account.ms_account.models.dtos.MovementDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.TransactionDetailDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import com.devsu.demo.ms.account.ms_account.models.entities.Movement;
import com.devsu.demo.ms.account.ms_account.repositories.AccountRepository;
import com.devsu.demo.ms.account.ms_account.repositories.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

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

class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper movementMapper;

    @InjectMocks
    private MovementServiceImpl movementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldSaveDepositAndReturnDTO() {

        TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
        transactionDetailDTO.setAccountNumber(12345);
        transactionDetailDTO.setTransactionValue(BigDecimal.valueOf(100));
        String movementType = "deposit";

        Account account = new Account();
        account.setAccountNumber(12345);
        account.setInitialBalance(BigDecimal.valueOf(50));

        Movement savedMovement = new Movement();
        savedMovement.setId(1L);
        savedMovement.setBalance(BigDecimal.valueOf(150));

        MovementDTO expectedDTO = new MovementDTO();
        expectedDTO.setId(1L);
        expectedDTO.setBalance(BigDecimal.valueOf(150));

        when(accountRepository.findByAccountNumber(transactionDetailDTO.getAccountNumber())).thenReturn(Optional.of(account));
        when(movementRepository.findFirstByAccountOrderByCreatedDateDesc(account)).thenReturn(null);
        when(movementRepository.save(any(Movement.class))).thenReturn(savedMovement);
        when(movementMapper.map(savedMovement, MovementDTO.class)).thenReturn(expectedDTO);


        MovementDTO result = movementService.save(transactionDetailDTO, movementType);


        assertEquals(expectedDTO, result);
        verify(accountRepository, times(1)).findByAccountNumber(transactionDetailDTO.getAccountNumber());
        verify(movementRepository, times(1)).save(any(Movement.class));
        verify(movementMapper, times(1)).map(savedMovement, MovementDTO.class);
    }

    @Test
    void save_shouldSaveWithdrawalAndReturnDTO() {

        TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
        transactionDetailDTO.setAccountNumber(12345);
        transactionDetailDTO.setTransactionValue(BigDecimal.valueOf(30));
        String movementType = "withdrawal";

        Account account = new Account();
        account.setAccountNumber(12345);
        account.setInitialBalance(BigDecimal.valueOf(50));

        Movement savedMovement = new Movement();
        savedMovement.setId(1L);
        savedMovement.setBalance(BigDecimal.valueOf(20));

        MovementDTO expectedDTO = new MovementDTO();
        expectedDTO.setId(1L);
        expectedDTO.setBalance(BigDecimal.valueOf(20));

        when(accountRepository.findByAccountNumber(transactionDetailDTO.getAccountNumber())).thenReturn(Optional.of(account));
        when(movementRepository.findFirstByAccountOrderByCreatedDateDesc(account)).thenReturn(null);
        when(movementRepository.save(any(Movement.class))).thenReturn(savedMovement);
        when(movementMapper.map(savedMovement, MovementDTO.class)).thenReturn(expectedDTO);


        MovementDTO result = movementService.save(transactionDetailDTO, movementType);


        assertEquals(expectedDTO, result);
        verify(accountRepository, times(1)).findByAccountNumber(transactionDetailDTO.getAccountNumber());
        verify(movementRepository, times(1)).save(any(Movement.class));
        verify(movementMapper, times(1)).map(savedMovement, MovementDTO.class);
    }

    @Test
    void save_shouldThrowResourceNotFoundExceptionIfAccountDoesNotExist() {

        TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
        transactionDetailDTO.setAccountNumber(12345);
        String movementType = "deposit";

        when(accountRepository.findByAccountNumber(transactionDetailDTO.getAccountNumber())).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> movementService.save(transactionDetailDTO, movementType));
        verify(accountRepository, times(1)).findByAccountNumber(transactionDetailDTO.getAccountNumber());
        verify(movementRepository, never()).save(any(Movement.class));
        verify(movementMapper, never()).map(any(), any());
    }

    @Test
    void save_shouldThrowInsufficientBalanceExceptionForInvalidWithdrawal() {

        TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
        transactionDetailDTO.setAccountNumber(12345);
        transactionDetailDTO.setTransactionValue(BigDecimal.valueOf(100));
        String movementType = "withdrawal";

        Account account = new Account();
        account.setAccountNumber(12345);
        account.setInitialBalance(BigDecimal.valueOf(50));

        when(accountRepository.findByAccountNumber(transactionDetailDTO.getAccountNumber())).thenReturn(Optional.of(account));
        when(movementRepository.findFirstByAccountOrderByCreatedDateDesc(account)).thenReturn(null);


        assertThrows(InsufficientBalanceException.class, () -> movementService.save(transactionDetailDTO, movementType));
        verify(accountRepository, times(1)).findByAccountNumber(transactionDetailDTO.getAccountNumber());
        verify(movementRepository, never()).save(any(Movement.class));
        verify(movementMapper, never()).map(any(), any());
    }

    @Test
    void findById_shouldReturnMovementIfExists() {

        Long movementId = 1L;
        Movement expectedMovement = new Movement();
        expectedMovement.setId(1L);

        when(movementRepository.findById(movementId)).thenReturn(Optional.of(expectedMovement));


        Movement result = movementService.findById(movementId);


        assertEquals(expectedMovement, result);
        verify(movementRepository, times(1)).findById(movementId);
    }

    @Test
    void findById_shouldThrowResourceNotFoundExceptionIfMovementDoesNotExist() {

        Long movementId = 1L;
        when(movementRepository.findById(movementId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> movementService.findById(movementId));
        verify(movementRepository, times(1)).findById(movementId);
    }

    @Test
    void deleteById_shouldDeleteMovementIfExists() {

        Long movementId = 1L;
        Movement existingMovement = new Movement();
        existingMovement.setId(1L);

        when(movementRepository.findById(movementId)).thenReturn(Optional.of(existingMovement));


        movementService.deleteById(movementId);


        verify(movementRepository, times(1)).findById(movementId);
        verify(movementRepository, times(1)).delete(existingMovement);
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundExceptionIfMovementDoesNotExist() {

        Long movementId = 1L;
        when(movementRepository.findById(movementId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> movementService.deleteById(movementId));
        verify(movementRepository, times(1)).findById(movementId);
        verify(movementRepository, never()).delete(any());
    }

    @Test
    void findAll_shouldReturnListOfMovements() {

        List<Movement> expectedMovements = new ArrayList<>();
        Movement movement1 = new Movement();
        movement1.setId(1L);
        Movement movement2 = new Movement();
        movement2.setId(2L);
        expectedMovements.add(movement1);
        expectedMovements.add(movement2);

        when(movementRepository.findAll()).thenReturn(expectedMovements);


        List<Movement> result = movementService.findAll();


        assertEquals(expectedMovements, result);
        verify(movementRepository, times(1)).findAll();
    }
}
