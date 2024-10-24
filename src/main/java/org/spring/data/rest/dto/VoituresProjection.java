package org.spring.data.rest.dto;

import org.spring.data.rest.modele.Proprietaire;
import org.spring.data.rest.modele.Voiture;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "voitureProjection", types = { Voiture.class })
public interface VoituresProjection {
    Long getId();
    String getMarque();
    String getModele();
    String getCouleur();
    String getImmatricule();
    Integer getAnnee();
    Integer getPrix();
    Proprietaire getProprietaire();
}
