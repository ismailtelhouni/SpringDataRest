package org.spring.data.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(registry -> {
            registry.requestMatchers("/").permitAll();
            registry.requestMatchers("/auth/register").permitAll();
            registry.anyRequest().authenticated();
        })
        .oauth2Login(oauth2Login->{
            oauth2Login.successHandler((request, response, authentication) -> response.sendRedirect("/api"));
        })
        .formLogin(formLogin -> formLogin
            .loginPage("/login")  // URL de la page de login
            .defaultSuccessUrl("/api")  // URL de redirection après une authentification réussie
            .failureUrl("/login?error=true")  // URL de redirection en cas d'échec
            .permitAll()
        )
        .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
