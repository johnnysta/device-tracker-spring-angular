package com.example.devicetracker.controller;

import com.example.devicetracker.dto.incoming.AccountRegistrationData;
import com.example.devicetracker.dto.outgoing.AccountListItem;
import com.example.devicetracker.service.AccountService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountListItem>> getAllAccounts() {
        return new ResponseEntity(this.accountService.getAllAccounts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> saveAccount(@RequestBody AccountRegistrationData accountRegistrationData){
        accountService.saveAccount(accountRegistrationData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
