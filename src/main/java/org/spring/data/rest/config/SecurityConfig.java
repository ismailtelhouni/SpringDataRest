package org.spring.data.rest.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final RsaKeysConfig rsaKeysConfig;
    private final PasswordEncoder passwordEncoder;
    public SecurityConfig(
            @Lazy CustomOAuth2SuccessHandler customOAuth2SuccessHandler ,
            RsaKeysConfig rsaKeysConfig ,
            PasswordEncoder passwordEncoder
    ) {
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
        this.rsaKeysConfig = rsaKeysConfig;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(registry -> {
            registry.requestMatchers("/").permitAll();
            registry.requestMatchers("/register","/token","/api","/api/voitures/**","/api/proprietaires/**","/actuator/**" , "/voitures").permitAll();
            registry.anyRequest().authenticated();
        })
        .oauth2Login(oauth2Login-> oauth2Login.successHandler(customOAuth2SuccessHandler))
        .formLogin(Customizer.withDefaults())
        .oauth2ResourceServer(auth2 -> auth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder())))
        .build();
    }
    @Bean
    public JwtDecoder customJwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeysConfig.publicKey()).build() ;
    }
    @Bean
    public JwtEncoder customJwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeysConfig.publicKey()).privateKey(rsaKeysConfig.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
