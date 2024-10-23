package org.spring.data.rest.dto;

import org.spring.data.rest.modele.Proprietaire;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "proprietaireProjection", types = { Proprietaire.class })
public interface ProprietaireProjection {
    Long getId();
    String getNom();
    String getPrenom();
}
