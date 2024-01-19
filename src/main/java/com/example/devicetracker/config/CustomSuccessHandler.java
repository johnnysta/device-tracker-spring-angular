package com.example.devicetracker.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.security.Principal;

@Configuration("oauth2SuccessHandler")
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {


    private String successfulRedirectUrl = "http://localhost:4200/home";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
//        log.info("PRINCI PÁL II." + principal.toString());
//        System.out.println("User successfully logged in to application!");

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        // Extract details like Principal, Authorities, etc.
        OAuth2User oauth2User = oauthToken.getPrincipal();

        String provider = determineAuthenticationProvider(authentication);

        log.info("PROVIDDER:");
        log.info(provider);


        // You can now extract attributes from the OAuth2User
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");

        String redirectUrl = successfulRedirectUrl + "?name=" + name + "&email=" + email + "&provider" + provider;
        response.sendRedirect(redirectUrl);
    }


    private String determineAuthenticationProvider(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // Check for attributes specific to Google or GitHub OAuth2User
            if (oAuth2User.getAttribute("sub") != null) {
                // "sub" attribute is present, indicating Google
                return "Google";
            } else if (oAuth2User.getAttribute("login") != null) {
                // "login" attribute is present, indicating GitHub
                return "GitHub";
            }
        }
        // Return a default value or handle the case when the provider cannot be determined
        return "Unknown";
    }

}
