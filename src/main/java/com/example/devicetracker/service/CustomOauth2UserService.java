package com.example.devicetracker.service;

import com.example.devicetracker.config.CustomOAuth2User;
import com.example.devicetracker.domain.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final AccountService accountService;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User();
        Account account = new Account();
        boolean isNewUser = false;
        switch (determineAuthenticationProvider(oAuth2User)) {
            case "Google": {
                String oAuthGmail = (String) oAuth2User.getAttributes().get("email");
                account = accountService.findAccountByGoogleUser(oAuthGmail);
                if (account != null) {
                    customOAuth2User = new CustomOAuth2User(account, oAuth2User);
                }
                else {
                    isNewUser = true;
                    account = new Account();
                    account.setGoogleUser(oAuthGmail);
                    account.setEmail(oAuthGmail);
                    account.setUserName((String) oAuth2User.getAttributes().get("name"));
                    customOAuth2User = new CustomOAuth2User(account, oAuth2User);
                }
                break;
            }
            case "GitHub": {
                String oAuthGitHubUser = (String) oAuth2User.getAttributes().get("login");
                account = accountService.findAccountByGitHubUser(oAuthGitHubUser);
                if (account != null) {
                    customOAuth2User = new CustomOAuth2User(account, oAuth2User);
                }
                else {
                    isNewUser = true;
                    account = new Account();
                    account.setGitHubUser(oAuthGitHubUser);
                    account.setUserName(oAuthGitHubUser);
                    account.setUserName((String) oAuth2User.getAttributes().get("name"));
                    customOAuth2User = new CustomOAuth2User(account, oAuth2User);
                }
                break;
            }
        }
        if (isNewUser) {
            account = accountService.saveAccount(account);
            customOAuth2User = new CustomOAuth2User(account, oAuth2User);
        }
        return customOAuth2User;
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
