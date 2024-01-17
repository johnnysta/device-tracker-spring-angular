package com.example.devicetracker.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration("oauth2FailureHandler")
@Slf4j
public class CustomFailureHandler implements AuthenticationFailureHandler {

//    @Value("${failed-login-redirect-url}")
    private String failedRedirectUrl = "";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.error("User is unauthorized to login to application: {}", exception.getLocalizedMessage());
        response.sendRedirect(failedRedirectUrl);
    }

}
