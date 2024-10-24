package org.spring.data.rest.web;

import org.spring.data.rest.config.jwt.JwtUtils;
import org.spring.data.rest.dto.AuthTokenRequest;
import org.spring.data.rest.dto.SignupRequest;
import org.spring.data.rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class AuthController {
    private final UserService userService;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public AuthController(
            UserService userService ,
            JwtDecoder jwtDecoder,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        this.userService = userService;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            userService.registerUser(
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    signUpRequest.getPassword()
            );
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/token")
    public ResponseEntity<Map<String , String>> jwtToken(@RequestBody AuthTokenRequest authTokenRequest) {
        String subject=null;
        String scope=null;
        String errorMessage = "errorMessage";
        if(authTokenRequest.getGrantType().equals("password")){
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authTokenRequest.getUsername(), authTokenRequest.getPassword())
                );
            }catch (Exception e){
                return new ResponseEntity<>(Map.of(errorMessage,"Invalid username or password"),HttpStatus.UNAUTHORIZED);
            }
            subject = authentication.getName();
            scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" " ));
        } else if(authTokenRequest.getGrantType().equals("refresh_token")){
            if(authTokenRequest.getRefreshToken() == null){
                return new ResponseEntity<>(Map.of(errorMessage,"Refresh Token is required"), HttpStatus.UNAUTHORIZED);
            }
            Jwt decodeJwt;
            try {
                decodeJwt = jwtDecoder.decode(authTokenRequest.getRefreshToken());
            } catch (JwtException e) {
                return new ResponseEntity<>(Map.of(errorMessage, e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            subject = decodeJwt.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            if(userDetails == null){
                return new ResponseEntity<>(Map.of(errorMessage, "User does not exist"), HttpStatus.UNAUTHORIZED);
            }
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            scope = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        }
        Map<String , String> idToken = jwtUtils.generateJwtToken(subject , authTokenRequest.isWithRefreshToken(), scope);
        return new ResponseEntity<>(idToken,HttpStatus.OK);
    }
}
