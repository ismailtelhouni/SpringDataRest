package org.spring.data.rest;

import org.spring.data.rest.modele.Voiture;
import org.spring.data.rest.repository.VoitureRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataRestApplication {
    private VoitureRepo repository;
    public SpringDataRestApplication(VoitureRepo repository) {this.repository = repository;}
    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestApplication.class, args);
    }
    @Bean
    CommandLineRunner runner() {
        return args -> {
            repository.save(new Voiture(null , "Toyota", "Corolla", "Grise", "A-1-9090", 2018, 95000));
            repository.save(new Voiture(null , "Ford", "Fiesta", "Rouge", "A-2-8090", 2015, 90000));
            repository.save(new Voiture(null , "Honda", "CRV", "Bleu", "A-3-7090", 2016, 140000));
        };
    }

}
