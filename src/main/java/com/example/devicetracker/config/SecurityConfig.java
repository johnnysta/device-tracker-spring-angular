package com.example.devicetracker.config;

import com.example.devicetracker.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/accounts/register").permitAll()
                        .requestMatchers("/api/accounts/userInfo").permitAll()
                        .requestMatchers("/api/accounts/logout").permitAll()
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
                            .logoutSuccessHandler(customLogoutSuccessHandler);
                })
                .sessionManagement(customizer -> {
                    customizer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                });
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // @formatter:off
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200",
                "http://localhost:4200/*", "http://127.0.0.1:4200/*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Cache-Control", "Content-Language", "Content-Type", "Expires", "Last-Modified", "Pragma", "Location"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        // @formatter:on
        return source;
    }
}
