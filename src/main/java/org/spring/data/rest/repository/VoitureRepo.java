package org.spring.data.rest.repository;

import org.spring.data.rest.modele.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepo extends JpaRepository<Voiture, Long> {
}
