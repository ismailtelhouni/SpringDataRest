package org.spring.data.rest.modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Data @NoArgsConstructor @RequiredArgsConstructor
public class Proprietaire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NonNull private String nom;
    @NonNull private String prenom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="proprietaire") @JsonIgnore private List<Voiture> voitures;
}
