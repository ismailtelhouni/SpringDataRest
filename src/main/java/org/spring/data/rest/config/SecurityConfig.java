package org.spring.data.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/").permitAll();
            registry.anyRequest().authenticated();
        })
        .oauth2Login(oauth2Login->{
            oauth2Login.successHandler((request, response, authentication) -> response.sendRedirect("voitures"));
        })
        .formLogin(Customizer.withDefaults())
        .build();
    }
}
