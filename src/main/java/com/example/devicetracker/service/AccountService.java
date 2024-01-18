package com.example.devicetracker.service;


import com.example.devicetracker.domain.Account;
import com.example.devicetracker.dto.incoming.AccountRegistrationData;
import com.example.devicetracker.dto.outgoing.AccountListItem;
import com.example.devicetracker.dto.outgoing.AuthenticatedUserDataDto;
import com.example.devicetracker.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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

    public void saveAccount(Account account) {
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

    public AuthenticatedUserDataDto mapPrincipalToUserData(Principal principal) {
        AuthenticatedUserDataDto authenticatedUserDataDto = new AuthenticatedUserDataDto();
        if (principal instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            OAuth2User oAuth2User = (OAuth2User) oAuth2AuthenticationToken.getPrincipal();
            switch (determineAuthenticationProvider(oAuth2User)) {
                case "Google": {
                    authenticatedUserDataDto.setEmail((String) oAuth2User.getAttributes().get("email"));
                    authenticatedUserDataDto.setUserName((String) oAuth2User.getAttributes().get("name"));
                }
                break;
                case "GitHub": {
                    authenticatedUserDataDto.setEmail((String) oAuth2User.getAttributes().get(""));
                    authenticatedUserDataDto.setUserName((String) oAuth2User.getAttributes().get("login"));
                }
            }
        } else if (principal instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            //Place for normal (not Oauth2) authentication
        }
        return authenticatedUserDataDto;
    }


    private String determineAuthenticationProvider(OAuth2User oAuth2User) {
        // Check for attributes specific to Google or GitHub OAuth2User
        if (oAuth2User.getAttribute("sub") != null) {
            // "sub" attribute is present, indicating Google
            return "Google";
        } else if (oAuth2User.getAttribute("login") != null) {
            // "login" attribute is present, indicating GitHub
            return "GitHub";
        }
        // Return a default value or handle the case when the provider cannot be determined
        return "Unknown";
    }

}
