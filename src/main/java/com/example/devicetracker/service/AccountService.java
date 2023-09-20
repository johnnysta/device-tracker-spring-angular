package com.example.devicetracker.service;


import com.example.devicetracker.dto.AccountListItem;
import com.example.devicetracker.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
