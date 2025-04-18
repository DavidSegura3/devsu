package com.devsu.demo.ms.account.ms_account.repositories;


import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import com.devsu.demo.ms.account.ms_account.models.entities.Movement;
import org.springframework.data.repository.CrudRepository;

public interface MovementRepository extends CrudRepository<Movement, Long> {

    Movement findFirstByAccountOrderByCreatedDateDesc(Account account);
}