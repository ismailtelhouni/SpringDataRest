package org.spring.data.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.data.rest.modele.Proprietaire;
import org.spring.data.rest.modele.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VoitureRepoTest {
    @Autowired
    private VoitureRepo voitureRepo;
    @Autowired
    private ProprietaireRepo proprietaireRepo;
    private Proprietaire proprietaire1;
    @BeforeEach
    void setUp() {
        proprietaire1 = new Proprietaire("Ali" , "Hassan");
        Proprietaire proprietaire2 = new Proprietaire("Najat" , "Bani");
        proprietaireRepo.save(proprietaire1);
        proprietaireRepo.save(proprietaire2);
        voitureRepo.save(new Voiture("Renault"  , "Clio", "Rouge"   , "1234AB", 2021, 15000     , proprietaire1));
        voitureRepo.save(new Voiture("MiolaCar" , "Clio", "Blanche" , "1234AB", 2022, 180000    , proprietaire2));
        voitureRepo.save(new Voiture("Renault"  , "Uber", "Blanche" , "1234AB", 2022, 15000     , proprietaire2));
    }
    @Test
    void shouldAjouterVoiture() {
        Voiture voiture = new Voiture("MiolaCar", "Uber", "Blanche", "M-2020", 2021, 180000 , proprietaire1);
        voitureRepo.save(voiture);
        assertThat(voiture.getId()).isNotNull();
        assertEquals("MiolaCar", voiture.getMarque());
        assertEquals("Uber", voiture.getModele());
        assertEquals("Blanche", voiture.getCouleur());
        assertEquals("M-2020", voiture.getImmatricule());
        assertEquals(2021, voiture.getAnnee());
        assertEquals(180000, voiture.getPrix());
    }
    @Test
    void testNotFindByMarque() {
        List<Voiture> voitures = voitureRepo.findByMarque("MiolaCar2");
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByMarque() {
        List<Voiture> voitures = voitureRepo.findByMarque("Renault");
        assertFalse(voitures.isEmpty());
        assertEquals("Renault", voitures.get(0).getMarque());
        assertThat(voitures).hasSize(2);
    }
    @Test
    void testFindByModele() {
        List<Voiture> voitures = voitureRepo.findByModele("Clio");
        assertFalse(voitures.isEmpty());
        assertEquals("Clio", voitures.get(0).getModele());
        assertThat(voitures).hasSize(2);
    }
    @Test
    void testNotFindByModele() {
        List<Voiture> voitures = voitureRepo.findByModele("Uber2");
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByCouleur() {
        List<Voiture> voitures = voitureRepo.findByCouleur("Rouge");
        assertFalse(voitures.isEmpty());
        assertEquals("Rouge", voitures.get(0).getCouleur());
        assertThat(voitures).hasSize(1);
    }
    @Test
    void testNotFindByCouleur() {
        List<Voiture> voitures = voitureRepo.findByCouleur("Red");
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByAnnee() {
        List<Voiture> voitures = voitureRepo.findByAnnee(2022);
        assertFalse(voitures.isEmpty());
        assertEquals(2022, voitures.get(0).getAnnee());
        assertThat(voitures).hasSize(2);
    }
    @Test
    void testNotFindByAnnee() {
        List<Voiture> voitures = voitureRepo.findByAnnee(2019);
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByMarqueAndModele() {
        List<Voiture> voitures = voitureRepo.findByMarqueAndModele("Renault", "Clio");
        assertFalse(voitures.isEmpty());
        assertEquals("Renault", voitures.get(0).getMarque());
        assertEquals("Clio", voitures.get(0).getModele());
        assertThat(voitures).hasSize(1);
    }
    @Test
    void testNotFindByMarqueAndModele() {
        List<Voiture> voitures = voitureRepo.findByMarqueAndModele("MiolaCar", "Uber");
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByMarqueOrCouleur() {
        List<Voiture> voitures = voitureRepo.findByMarqueOrCouleur("Renault", "Rouge");
        assertFalse(voitures.isEmpty());
        assertEquals("Renault", voitures.get(0).getMarque());
        assertEquals("Rouge", voitures.get(0).getCouleur());
        assertThat(voitures).hasSize(2);
    }
    @Test
    void testNotFindByMarqueOrCouleur() {
        List<Voiture> voitures = voitureRepo.findByMarqueOrCouleur("Honda", "red");
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByMarqueOrderByAnneeAsc() {
        List<Voiture> voitures = voitureRepo.findByMarqueOrderByAnneeAsc("Renault");
        assertFalse(voitures.isEmpty());
        assertEquals("Renault", voitures.get(0).getMarque());
        assertEquals(2021, voitures.get(0).getAnnee());
        assertThat(voitures).hasSize(2);
    }
    @Test
    void testNotFindByMarqueOrderByAnneeAsc() {
        List<Voiture> voitures = voitureRepo.findByMarqueOrderByAnneeAsc("Renault2");
        assertTrue(voitures.isEmpty());
    }
    @Test
    void testFindByMarque2() {
        List<Voiture> voitures = voitureRepo.findByMarque2("Renault");
        assertFalse(voitures.isEmpty());
        assertEquals("Renault", voitures.get(0).getMarque());
        assertThat(voitures).hasSize(2);
    }
    @Test
    void testNotFindByMarque2() {
        List<Voiture> voitures = voitureRepo.findByMarque2("Renault2");
        assertTrue(voitures.isEmpty());
    }
    @Test
     void testFindByMarqueEndsWith() {
        List<Voiture> voitures = voitureRepo.findByMarqueEndsWith("ult");
        assertFalse(voitures.isEmpty());
        assertEquals("Renault", voitures.get(0).getMarque());
        assertThat(voitures).hasSize(2);
    }
    @Test
     void testNotFindByMarqueEndsWith() {
        List<Voiture> voitures = voitureRepo.findByMarque2("2");
        assertTrue(voitures.isEmpty());
    }
}