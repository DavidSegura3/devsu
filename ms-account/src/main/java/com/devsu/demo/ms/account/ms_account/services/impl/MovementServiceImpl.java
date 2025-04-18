package com.devsu.demo.ms.account.ms_account.services.impl;


import com.devsu.demo.ms.account.ms_account.exceptions.business.InsufficientBalanceException;
import com.devsu.demo.ms.account.ms_account.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.account.ms_account.models.dtos.MovementDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.TransactionDetailDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import com.devsu.demo.ms.account.ms_account.models.entities.Movement;
import com.devsu.demo.ms.account.ms_account.repositories.AccountRepository;
import com.devsu.demo.ms.account.ms_account.repositories.MovementRepository;
import com.devsu.demo.ms.account.ms_account.services.IMovementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements IMovementService {

    private static final String MOVEMENT = "movement";
    private static final String WITHDRAWAL = "withdrawal";
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Qualifier("movementMapper")
    private final ModelMapper movementMapper;


    @Override
    public MovementDTO save(TransactionDetailDTO transactionDetailDTO, String movementType) {

        Account account = accountRepository.findByAccountNumber(transactionDetailDTO.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("account", Long.valueOf(transactionDetailDTO.getAccountNumber())));

        BigDecimal transactionValue = transactionDetailDTO.getTransactionValue();
        BigDecimal currentBalance = getCurrentBalance(account);
        BigDecimal newBalance;

        if (movementType.equalsIgnoreCase(WITHDRAWAL)) {
            transactionValid(currentBalance, transactionValue);
            newBalance = currentBalance.subtract(transactionValue);
        } else {
            newBalance = currentBalance.add(transactionValue);
        }
        Movement movement = Movement
                .builder()
                .movementType(movementType)
                .transactionValue(transactionValue)
                .balance(newBalance)
                .account(account)
                .build();
        return convertMovementToDto(movementRepository.save(movement));
    }

    @Override
    public Movement findById(Long movementId) {
        return movementRepository.findById(movementId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVEMENT, movementId));
    }

    @Override
    public void deleteById(Long movementId) {
        Movement foundMovement = movementRepository.findById(movementId)
                .orElseThrow(() -> new ResourceNotFoundException(MOVEMENT, movementId));
        movementRepository.delete(foundMovement);
    }

    @Override
    public List<Movement> findAll() {
        return (List<Movement>) movementRepository.findAll();
    }

    private MovementDTO convertMovementToDto(Movement movement) {
        return movementMapper.map(movement, MovementDTO.class);
    }

    private void transactionValid(BigDecimal balance, BigDecimal transactionValue) {
        if (transactionValue.compareTo(balance) > 0) {
            throw new InsufficientBalanceException();
        }
    }

    private BigDecimal getCurrentBalance(Account account) {
        Movement lastMovement = movementRepository.findFirstByAccountOrderByCreatedDateDesc(account);
        if (lastMovement != null) {
            return lastMovement.getBalance();
        } else {
            return account.getInitialBalance();
        }
    }
}
