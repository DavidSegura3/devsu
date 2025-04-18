package com.devsu.demo.ms.account.ms_account.services;


import com.devsu.demo.ms.account.ms_account.models.dtos.MovementDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.TransactionDetailDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Movement;

import java.util.List;

public interface IMovementService {

    MovementDTO save(TransactionDetailDTO transactionDetailDTO, String movementType);
    Movement findById(Long movementId);
    void deleteById(Long movementId);
    List<Movement> findAll();
}
