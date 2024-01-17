package com.example.devicetracker.service;


import com.example.devicetracker.domain.Account;
import com.example.devicetracker.dto.incoming.AccountRegistrationData;
import com.example.devicetracker.dto.outgoing.AccountListItem;
import com.example.devicetracker.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountListItem> getAllAccounts() {
        return this.accountRepository.findAll().stream().map(AccountListItem::new).
                collect(Collectors.toList());
    }

    public void saveRegisteredAccount(AccountRegistrationData accountRegistrationData) {
        accountRepository.save(accountFromAccountRegistrationData(accountRegistrationData));
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    private Account accountFromAccountRegistrationData(AccountRegistrationData accountRegistrationData) {
        Account account = new Account();
        account.setUserName(accountRegistrationData.getUser_name());
        account.setEmail(accountRegistrationData.getUser_email());
        account.setPassword(accountRegistrationData.getUser_password());
        return account;
    }

    public boolean existsAccountByGoogleUser(String oAuthGmail) {
        return accountRepository.existsAccountByGoogleUser(oAuthGmail);
    }

    public boolean existsAccountByGitHubUser(String oAuthGitHubUser) {
        return accountRepository.existsAccountByGitHubUser(oAuthGitHubUser);
    }
}
