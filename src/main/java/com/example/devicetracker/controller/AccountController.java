package com.example.devicetracker.controller;

import com.example.devicetracker.dto.AccountListItem;
import com.example.devicetracker.service.AccountService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Data
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

}
