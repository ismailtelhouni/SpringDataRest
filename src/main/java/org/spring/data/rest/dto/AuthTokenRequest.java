package org.spring.data.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthTokenRequest {
    private String grantType ;
    private String username;
    private String password;
    private boolean withRefreshToken ;
    private String refreshToken;
}
