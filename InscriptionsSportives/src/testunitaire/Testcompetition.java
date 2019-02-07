package testunitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import inscriptions.Competition;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;

class Testcompetition {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test
    public void testSetNom() {
    	Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        assertEquals("nomComp", competition.getNom());
    }

    @Test
    public void testGetNom() {
    	Inscriptions inscriptions = Inscriptions.getInscriptions();
    	Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
    	competition.setNom("nomComp");
        assertEquals("nomComp", competition.getNom());
    }

    @Test
    public void testGetCandidats() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
    	Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
		Personne perso = inscriptions.createPersonne("nom", "prenom", "mail");
		competition.add(perso);
        assertTrue(competition.getCandidats().contains(perso));
    }

    @Test
    public void testRemove() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        Personne perso = inscriptions.createPersonne("nom", "prenom", "mail");
        competition.add(perso);
        int sizeBefore = competition.getCandidats().size();
        competition.remove(perso);
        int sizeAfter = competition.getCandidats().size();
        assertEquals(sizeBefore - 1, sizeAfter);
    }
    
    @Test
    public void testDelete() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        int size = inscriptions.getCompetitions().size();
        competition.delete();
        assertEquals(size - 1, inscriptions.getCompetitions().size());
    }

    @Test
    public void testCompareTo() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition compe = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        Competition compe2 = inscriptions.createCompetition("nomComp2",LocalDate.now().plusDays(3), false);
        assertEquals(0, compe.compareTo(compe2));
    }
    
    @Test
    public void testToString() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), false);
        assertNotNull(competition.toString());
    }

    @Test
    public void testAddEquipe() {
        Inscriptions inscriptions = Inscriptions.getInscriptions();
        Competition competition = inscriptions.createCompetition("nomComp",LocalDate.now().plusDays(3), true);
        Equipe equipe = inscriptions.createEquipe("nomEquipe");  
        competition.add(equipe);
        assertTrue(competition.getCandidats().contains(equipe));
    }
    
}
