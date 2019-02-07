package testunitaire;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inscriptions.Competition;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;

class Testinscription {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    public void testCreatePersonne() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        assertTrue(inscriptions.getCandidats().contains(personne));
    }
	
    @Test
    public void testGetPersonnes() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        assertTrue(inscriptions.getPersonnes().contains(personne));
    }
    
    @Test
    public void testCreateCompetition() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        assertTrue(inscriptions.getCompetitions().contains(competition));
    }
    
    @Test
    public void testGetCompetitions() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        assertTrue(inscriptions.getCompetitions().contains(competition));
    }
    
    @Test
    public void testCreateEquipe() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Equipe equipe = inscriptions.createEquipe("nom");
        assertTrue(inscriptions.getCandidats().contains(equipe));
    }
    
    @Test
    public void testGetEquipes() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Equipe equipe = inscriptions.createEquipe("nom");
        assertTrue(inscriptions.getCandidats().contains(equipe));
    }
    
    @Test
    public void testGetInscriptions() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        assertNotNull(inscriptions);
    }

    @Test
    public void testGetCandidats() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
		Equipe equipe = inscriptions.createEquipe("nom");
        equipe.add(personne);
        assertTrue(inscriptions.getCandidats().contains(personne));
        assertTrue(inscriptions.getCandidats().contains(equipe));
    }
    
    @Test
    public void testReinitialiser() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        assertNotNull(inscriptions, "L'objet n'est pas null, il a donc été reinitialisé !");
    }

    @Test
    public void testRecharger() {
        Inscriptions inscriptions = null;
        inscriptions = Inscriptions.getInscriptions();
        assertNotNull(inscriptions, "L'objet n'est pas null, il a donc été rechargé !");
    }

    @Test
    public void testToString() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        assertNotNull(inscriptions.toString());
    }
    
}
