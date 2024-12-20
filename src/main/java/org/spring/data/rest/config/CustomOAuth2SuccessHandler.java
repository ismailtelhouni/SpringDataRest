package org.spring.data.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.spring.data.rest.config.jwt.JwtUtils;
import org.spring.data.rest.modele.ERole;
import org.spring.data.rest.modele.Role;
import org.spring.data.rest.modele.User;
import org.spring.data.rest.repository.RoleRepository;
import org.spring.data.rest.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    public CustomOAuth2SuccessHandler(UserRepository userRepository , RoleRepository roleRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email"); // Adjust this based on your OAuth2 provider
        String username = oauth2User.getAttribute("name"); // Adjust if necessary
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        // Check if user exists
        if (userRepository.findByEmail(email).isEmpty()) {
            // If not, create a new user without a password
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);
            // Do not set password, as it is not needed for OAuth2 users
            userRepository.save(user);
        }
        Map<String , String> jwtToken = jwtUtils.generateJwtToken(authentication.getName(), true , scope);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(jwtToken);
        response.getWriter().write(jsonResponse);
    }
}
