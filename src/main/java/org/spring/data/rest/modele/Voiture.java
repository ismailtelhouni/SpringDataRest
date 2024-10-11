package org.spring.data.rest.modele;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @RequiredArgsConstructor
@Entity
public class Voiture {
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) private Long id;
    @NonNull private String marque;
    @NonNull private String modele;
    @NonNull private String couleur;
    @NonNull private String immatricule;
    @NonNull private Integer annee;
    @NonNull private Integer prix;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "proprietaire") @NonNull private Proprietaire proprietaire;
}
