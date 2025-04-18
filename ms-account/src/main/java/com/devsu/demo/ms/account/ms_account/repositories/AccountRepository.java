package com.devsu.demo.ms.account.ms_account.repositories;


import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByAccountNumber(Integer accountNumber);

}