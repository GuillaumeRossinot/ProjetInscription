package testunitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;

class Testpersonne {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    public void testSetPrenom() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        personne.setPrenom("prenom2");
        String test = personne.getPrenom();
        assertEquals("prenom2", test);
    }

    @Test
    public void tesGetPrenom() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        String test = personne.getPrenom();
        assertEquals("prenom", test);
    }

    @Test
    public void testSetMail() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        personne.setPrenom("mail2");
        String test = personne.getMail();
        assertEquals("mail2", test);
    }
    
    @Test
    public void testGetMail() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        String test = personne.getMail();
        assertEquals("mail", test);
    }
    
    @Test
    public void testGetEquipes() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        Equipe equipe = inscriptions.createEquipe("nomEquipe");
        equipe.add(personne);
        assertTrue(personne.getEquipes().contains(equipe));
    }
    
    @Test
    public void testToString() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
        assertNotNull(personne.toString());
    }
    
}
