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
  
  private static final String[] AUTH_SWAGGER_ENTRYPOINTS = {
                                                            "/swagger-resources", 
                                                            "/swagger-resources/**", 
                                                            "/configuration/ui",
                                                            "/configuration/security", 
                                                            "/swagger-ui.html", 
                                                            "/webjars/**", 
                                                            "/v3/api-docs/**", 
                                                            "v3/api-docs",
                                                            "/api/public/**", 
                                                            "/api/public/authenticate", 
                                                            "/actuator/*", 
                                                            "/swagger-ui/**", 
                                                            "/api-docs/**"
                                                            };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers("car-rest-service/api/v1/private/**").authenticated()
                        .requestMatchers("car-rest-service/api/v1/**")
                               .hasAnyAuthority(
                                       "SCOPE_update:resource",
                                       "SCOPE_create:resource",
                                       "SCOPE_delete:resource",
                                       "SCOPE_read:resource")
                        .requestMatchers(AUTH_SWAGGER_ENTRYPOINTS).permitAll())
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults()));

        return httpSecurity.build();
    }

}
