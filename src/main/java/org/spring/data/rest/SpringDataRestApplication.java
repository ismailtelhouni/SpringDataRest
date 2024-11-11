package org.spring.data.rest;

import org.spring.data.rest.config.RsaKeysConfig;
import org.spring.data.rest.modele.ERole;
import org.spring.data.rest.modele.Proprietaire;
import org.spring.data.rest.modele.Role;
import org.spring.data.rest.repository.ProprietaireRepo;
import org.spring.data.rest.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
public class SpringDataRestApplication {
    private final RoleRepository roleRepository;
    private final ProprietaireRepo proprietaireRepo;
    public SpringDataRestApplication( RoleRepository roleRepository , ProprietaireRepo proprietaireRepo ) {
        this.roleRepository  = roleRepository;
        this.proprietaireRepo = proprietaireRepo;
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestApplication.class, args);
    }
    @Bean
    @Profile("!test")
    CommandLineRunner runner() {
        return args -> {
            Role role = new Role(null,ERole.ROLE_USER);
            Role role2 = new Role(null,ERole.ROLE_ADMIN);
            if(roleRepository.findByName(ERole.ROLE_USER).isEmpty() && roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(role);
                roleRepository.save(role2);
            }
            Proprietaire proprietaire1 = new Proprietaire("Ali" , "Hassan");
            Proprietaire proprietaire2 = new Proprietaire("Najat" , "Bani");
            if(proprietaireRepo.findAll().isEmpty()){
                proprietaireRepo.save(proprietaire1);
                proprietaireRepo.save(proprietaire2);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
