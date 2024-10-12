package org.spring.data.rest.repository;

import org.spring.data.rest.modele.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietaireRepo extends JpaRepository<Proprietaire, Long> {
}
