package testunitaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;

class Testequipe {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testAddPersonne()  {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
		Equipe equipe = inscriptions.createEquipe("nom");
		equipe.add(personne);
		assertTrue(equipe.getMembres().contains(personne));
	}
	
	@Test
	public void testRemovePersonne()  {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
		Equipe equipe = inscriptions.createEquipe("nom");
		equipe.add(personne);
		assertTrue(equipe.getMembres().contains(personne));
		personne.delete();
		assertTrue(!equipe.getMembres().contains(personne));
	}

	@Test
	public void testGetMembres()  {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
		Equipe equipe = inscriptions.createEquipe("nom");
		equipe.add(personne);
		int size = equipe.getMembres().size();
		assertTrue(equipe.getMembres().contains(personne));
		assertEquals(size,equipe.getMembres().size());
}
	
	@Test
	public void testDelete()  {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
		Equipe equipe = inscriptions.createEquipe("nom");
		equipe.add(personne);
		assertTrue(inscriptions.getPersonnes().contains(personne));
		personne.delete();
		assertTrue(!equipe.getMembres().contains(personne));
		assertTrue(!inscriptions.getPersonnes().contains(personne));
		assertTrue(!inscriptions.getCandidats().contains(personne));
	}
	
	@Test
	public void testToString()  {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne personne = inscriptions.createPersonne("nom", "prenom", "mail");
		Equipe equipe = inscriptions.createEquipe("nom");
		equipe.add(personne);
		assertNotNull(personne.toString());
	}
}
