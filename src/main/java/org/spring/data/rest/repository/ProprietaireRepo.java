package org.spring.data.rest.repository;

import org.spring.data.rest.dto.ProprietaireProjection;
import org.spring.data.rest.modele.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = ProprietaireProjection.class)
public interface ProprietaireRepo extends JpaRepository<Proprietaire, Long> {
}
