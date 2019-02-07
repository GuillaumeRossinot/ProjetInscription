package testunitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inscriptions.Competition;
import inscriptions.Inscriptions;
import inscriptions.Personne;

import java.time.LocalDate;

class Testcandidat {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    public void testGetNom() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        String inscription = personne.getPrenom();
        assertEquals("prenom", inscription);
    }

    @Test
    public void testGetCompetitions() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        competition.add(personne);
        assertTrue(personne.getCompetitions().contains(competition));
    }

    @Test
    public void testToString() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne perso = inscriptions.createPersonne("Nom", "Prenom", "mail");
        assertNotNull(perso.toString());
    }    
    
    @Test
    public void testCompareTo() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne perso = inscriptions.createPersonne("Nom", "Prenom", "mail");
        Personne perso2 = inscriptions.createPersonne("Nom2", "Prenom2", "mail2");
        assertEquals(0, perso.compareTo(perso2));
    }
    
    @Test
    public void testDelete() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne perso = inscriptions.createPersonne("Nom", "Prenom", "mail");
        perso.delete();
        assertTrue(!inscriptions.getCandidats().contains(perso));
    }
    
}
