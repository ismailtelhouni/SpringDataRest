package org.spring.data.rest.repository;

import org.spring.data.rest.modele.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
@Repository
public interface VoitureRepo extends JpaRepository<Voiture, Long> {
    List<Voiture> findByMarque(String marque);
    List<Voiture> findByCouleur(@Param("couleur") String couleur);
    List<Voiture> findByAnnee(Integer annee);
    List<Voiture> findByMarqueAndModele(String marque, String modele);
    List<Voiture> findByMarqueOrCouleur(String marque, String couleur);
    List<Voiture> findByMarqueOrderByAnneeAsc(String marque);
    @Query("select v from Voiture v where v.marque = ?1")
    List<Voiture> findByMarque2(String marque);
    @Query("select v from Voiture v where v.marque like %?1")
    List<Voiture> findByMarqueEndsWith(String marque);
    List<Voiture> findByModele(@Param("modele") String modele);
}
