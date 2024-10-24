package org.spring.data.rest.web;

import org.spring.data.rest.modele.Voiture;
import org.spring.data.rest.repository.VoitureRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    private final VoitureRepo voitureRepo;
    public VoitureController(VoitureRepo voitureRepo) {
        this.voitureRepo = voitureRepo;
    }

    @GetMapping()
    public Iterable<Voiture> getVoitures(){
        return voitureRepo.findAll();
    }
    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public Map<String,String> customer(Authentication authentication) {
        return Map.of(
                "name","ismail",
                "username",authentication.getName(),
                "scope",authentication.getAuthorities().toString()
        );
    }
}
