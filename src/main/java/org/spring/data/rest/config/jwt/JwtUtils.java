package org.spring.data.rest.config.jwt;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private final JwtEncoder jwtEncoder;
    public JwtUtils(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    public Map<String, String> generateJwtToken(String subject , boolean withRefreshToken , String scope ) {
        Map<String, String> idToken = new HashMap<>();
        Instant instant = Instant.now();
        // Generate the access token
        String jwtAccessToken = generateToken(subject, instant, withRefreshToken?5:30, scope, false);
        idToken.put("access_token", jwtAccessToken);
        // If requested, generate the refresh token
        if (withRefreshToken) {
            String jwtRefreshToken = generateToken(subject, instant, 5, null, true);
            idToken.put("refresh_token", jwtRefreshToken);
        }
        return idToken;
    }
    private String generateToken(String subject, Instant issuedAt, long expirationMinutes, String scope, boolean isRefreshToken) {
        JwtClaimsSet.Builder jwtBuilder = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plus(expirationMinutes, ChronoUnit.MINUTES))
                .issuer("security-service");
        // Only add scope if it's not a refresh token
        if (!isRefreshToken && scope != null) {
            jwtBuilder.claim("scope", scope);
        }
        JwtClaimsSet jwtClaimsSet = jwtBuilder.build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}
