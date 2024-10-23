package org.spring.data.rest;

import org.spring.data.rest.modele.ERole;
import org.spring.data.rest.modele.Proprietaire;
import org.spring.data.rest.modele.Role;
import org.spring.data.rest.modele.Voiture;
import org.spring.data.rest.repository.ProprietaireRepo;
import org.spring.data.rest.repository.RoleRepository;
import org.spring.data.rest.repository.VoitureRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class SpringDataRestApplication {
    private final VoitureRepo repository;
    private final ProprietaireRepo proprietaireRepo;
    private final RoleRepository roleRepository;
    public SpringDataRestApplication(VoitureRepo repository , ProprietaireRepo proprietaireRepo, RoleRepository roleRepository) {
        this.repository = repository;
        this.proprietaireRepo = proprietaireRepo;
        this.roleRepository  = roleRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestApplication.class, args);
    }
    @Bean
    @Profile("!test")
    CommandLineRunner runner() {
        return args -> {
            /*Proprietaire proprietaire1 = new Proprietaire("Ali" , "Hassan");
            Proprietaire proprietaire2 = new Proprietaire("Najat" , "Bani");
            proprietaireRepo.save(proprietaire1);
            proprietaireRepo.save(proprietaire2);
            repository.save(new Voiture("Toyota", "Corolla", "Grise", "A-1-9090", 2018, 95000 , proprietaire1));
            repository.save(new Voiture("Ford", "Fiesta", "Rouge", "A-2-8090", 2015, 90000, proprietaire1));
            repository.save(new Voiture("Honda", "CRV", "Bleu", "A-3-7090", 2016, 140000, proprietaire2));*/
            Role role = new Role(null,ERole.ROLE_USER);
            Role role2 = new Role(null,ERole.ROLE_ADMIN);
            if(roleRepository.findByName(ERole.ROLE_USER).isEmpty() && roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(role);
                roleRepository.save(role2);
            }
        };
    }

}
