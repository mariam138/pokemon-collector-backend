package com.example.pokemon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/", "/error",
                            "/login", "/login/**",
                            "/oauth2/**"
                    ).permitAll()
                            .requestMatchers("/api/**").authenticated()
                            .requestMatchers("/secured").authenticated()
                            .anyRequest().authenticated();
                })
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/secured", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }
}
