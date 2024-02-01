package com.example.devicetracker.controller;

import com.example.devicetracker.dto.in.AccountRegistrationData;
import com.example.devicetracker.dto.out.AccountListItem;
import com.example.devicetracker.dto.out.AuthenticatedUserDataDto;
import com.example.devicetracker.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
@Slf4j
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
    public ResponseEntity<Void> saveAccount(@RequestBody AccountRegistrationData accountRegistrationData) {
        accountService.saveRegisteredAccount(accountRegistrationData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/register")
    public void hello() {
        log.info("ALLOWED");
    }

    @GetMapping("/userInfo")
    public ResponseEntity<AuthenticatedUserDataDto> getUserInfo(Principal principal) {
        AuthenticatedUserDataDto authenticatedUserDataDto = null;
        if (principal != null) {
            authenticatedUserDataDto = accountService.mapPrincipalToUserData(principal);
        }
        return new ResponseEntity<>(authenticatedUserDataDto, HttpStatus.OK);
    }

    @GetMapping("/secure")
    public ResponseEntity<String> hello(Principal principal) {
        log.info("PRINCI PÁL" + principal.toString());
        return new ResponseEntity("Hello, secured", HttpStatus.OK);
    }

    @GetMapping("/github_oauth_result")
    public void hello_result(HttpServletResponse response, Principal principal) throws IOException {
        log.debug("User successfully logged in to application!");
        log.info("PRINCI PÁL II." + principal.toString());
//        return new ResponseEntity("Hello, secured by github", HttpStatus.OK);
        response.sendRedirect("http://localhost:4200/");

    }

}
