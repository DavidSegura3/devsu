package com.devsu.demo.ms.account.ms_account.services.impl;

import com.devsu.demo.ms.account.ms_account.exceptions.business.ResourceNotFoundException;
import com.devsu.demo.ms.account.ms_account.models.dtos.AccountDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.BalanceDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.ClientDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Account;
import com.devsu.demo.ms.account.ms_account.repositories.AccountRepository;
import com.devsu.demo.ms.account.ms_account.services.IAccountService;
import com.devsu.demo.ms.account.ms_account.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private static final String ACCOUNT = "account";
    private final AccountRepository accountRepository;
    private final IClientService clientService;

    @Qualifier("defaultMapper")
    private final ModelMapper defaultMapper;



    @Override
    public AccountDTO save(Account account) {
        return convertAccountToDto(accountRepository.save(account));
    }

    @Override
    public AccountDTO findAccountNumber(Integer accountNumber) {
        return convertAccountToDto(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT, Long.valueOf(accountNumber))));
    }

    @Override
    public AccountDTO updateBalance(BalanceDTO balance, Long accountId) {

        Account foundAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT, accountId));

        BigDecimal newTotalBalance = foundAccount.getInitialBalance().add(balance.getInitialBalance());
        foundAccount.setInitialBalance(newTotalBalance);
        return convertAccountToDto(accountRepository.save(foundAccount));
    }

    @Override
    public void deleteById(Long accountId) {
        Account foundAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT, accountId));
        accountRepository.delete(foundAccount);
    }

    @Override
    public List<AccountDTO> findAll() {

        List<Account> accounts = (List<Account>) accountRepository.findAll();
        return accounts
                .stream()
                .map(this::convertAccountToDto)
                .toList();
    }

    @Override
    public AccountDTO createAccountAndAssociateToClient(Account account, Long clientId) {

        ClientDTO client = clientService.findByClientId(clientId).block();
        AccountDTO accountDTO = convertAccountToDto(accountRepository.save(account));
        accountDTO.setClient(client);
        return accountDTO;
    }

    private AccountDTO convertAccountToDto(Account account) {
        return defaultMapper.map(account, AccountDTO.class);
    }
}
