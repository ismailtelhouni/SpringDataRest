package org.spring.data.rest.web;

import org.spring.data.rest.modele.Voiture;
import org.spring.data.rest.repository.VoitureRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
}
