package com.example.devicetracker.config;

import com.example.devicetracker.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomLogoutSuccesHandler customLogoutSuccesHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/accounts/register").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        {
                            httpSecurityOAuth2LoginConfigurer
                                    .successHandler(customSuccessHandler)
                                    .failureHandler(customFailureHandler)
                                    .userInfoEndpoint(userInfoEndpointConfig ->
                                            userInfoEndpointConfig.userService(customOauth2UserService));
                        }
                )
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/api/accounts/logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .logoutSuccessHandler(customLogoutSuccesHandler);

                })
                .sessionManagement(customizer -> {
                    customizer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                });
        return http.build();
    }
}
