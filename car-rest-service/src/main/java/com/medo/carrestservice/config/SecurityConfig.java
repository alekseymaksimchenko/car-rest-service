package com.medo.carrestservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers("car-rest-service/api/v1/private/**").authenticated()
                        .requestMatchers("car-rest-service/api/v1/private-scoped/**")
                               .hasAnyAuthority(
                                       "SCOPE_update:resource",
                                       "SCOPE_create:resource",
                                       "SCOPE_delete:resource",
                                       "SCOPE_read:resource"))
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults()));

        return httpSecurity.build();
    }

}
