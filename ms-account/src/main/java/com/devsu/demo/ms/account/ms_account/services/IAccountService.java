package com.devsu.demo.ms.account.ms_account.services;


import com.devsu.demo.ms.account.ms_account.models.dtos.AccountDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.BalanceDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Account;

import java.util.List;

public interface IAccountService {

    AccountDTO save(Account account);
    AccountDTO findAccountNumber(Integer accountNumber);
    AccountDTO updateBalance(BalanceDTO balance, Long accountId);
    void deleteById(Long accountId);
    List<AccountDTO> findAll();
    AccountDTO createAccountAndAssociateToClient(Account account, Long clientId);
}
