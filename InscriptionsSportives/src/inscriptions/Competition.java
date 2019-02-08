package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import inscriptions.Equipe;

import static org.junit.Assert.assertTrue;

/**
 * Représente une compétition, c'est-à -dire un ensemble de candidats 
 * inscrits à  un événement, les inscriptions sont closes à  la date dateCloture.
 *
 */

public class Competition implements Comparable<Competition>, Serializable
{
	private static final long serialVersionUID = -2882150118573759729L;
	private Inscriptions inscriptions;
	private String nom;
	private Set<Candidat> candidats;
	private LocalDate dateCloture;
	private boolean enEquipe = false;

	Competition(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe)
	{
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
	}
	
	/**
	 * Retourne le nom de la compétition.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Modifie le nom de la compétition.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom ;
	}
	
	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, 
	 * faux si les inscriptions sont closes.
	 * @return
	 */
	
	public boolean inscriptionsOuvertes()
	{
		// TODO retourner vrai si et seulement si la date systÃ¨me est antÃ©rieure Ã  la date de clÃ´ture.
		return LocalDate.now().isBefore(getDateCloture());

	}
	
	/**
	 * Retourne la date de cloture des inscriptions.
	 * @return
	 */
	
	public LocalDate getDateCloture()
	{
		return dateCloture;
	}
	
	/**
	 * Est vrai si et seulement si les inscriptions sont réservées aux équipes.
	 * @return
	 */
	
	public boolean estEnEquipe()
	{
		return enEquipe;
	}
	
	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la reculer 
	 * mais pas de l'avancer.
	 * @param dateCloture
	 */
	
	public void setDateCloture(LocalDate dateCloture)
	{
		// TODO vÃ©rifier que l'on avance pas la date.
		
		if (this.dateCloture.isBefore(dateCloture) || dateCloture.equals(this.dateCloture)) 
			this.dateCloture = dateCloture;
	else
			throw new RuntimeException("Date de clôture invalide !");
	
	}
	
	/**
	 * Retourne l'ensemble des candidats inscrits.
	 * @return
	 */
	
	public Set<Candidat> getCandidats()
	{
		return Collections.unmodifiableSet(candidats);
	}
	
	/**
	 * Inscrit un candidat de type Personne Ã  la compétition. Provoque une
	 * exception si la compÃ©tition est réservée aux équipes ou que les 
	 * inscriptions sont closes.
	 * @param personne
	 * @return
	 */
	
	public boolean add(Personne personne)
	{
		// TODO vÃ©rifier que la date de clÃ´ture n'est pas passÃ©e
		if (enEquipe)
			throw new RuntimeException("Compétition réservée aux équipes !");
		
		else if (inscriptionsOuvertes())
		{
			personne.add(this);
			return candidats.add(personne);
		}
		else 
			throw new RuntimeException("Erreur lors de l'inscription");
	}

	/**
	 * Inscrit un candidat de type Equipe Ã  la compétition. Provoque une
	 * exception si la compétition est réservée aux personnes ou que 
	 * les inscriptions sont closes.
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe)
	{
		// TODO vÃ©rifier que la date de clÃ´ture n'est pas passÃ©e
		if (!enEquipe)
			throw new RuntimeException("Compétition individuelle !");
		if(inscriptionsOuvertes())
			equipe.add(this);
		return candidats.add(equipe);
	}
	
	/**
	 * Retourne les Candidats que l'on peut inscrire à  cette competition.
	 * @return les candidats que l'on peut inscrire à  cette compétition.
	 */
	
	public Set<Candidat> getCandidatsAInscrire()
	{	
		// TODO les candidats que l'on peut inscrire Ã  cette compÃ©tition.
		
		Set<Candidat> CandidatsAInscrire = new TreeSet<>();
		for (Candidat candidats : inscriptions.getCandidats())
			if (!(getCandidats()).contains(candidats))
				CandidatsAInscrire.add(candidats);
		return CandidatsAInscrire;
		
	}

	/**
	 * Désinscrit un candidat.
	 * @param candidat
	 * @return
	 */
	
	public boolean remove(Candidat candidat)
	{
		candidat.remove(this);
		return candidats.remove(candidat);
	}
	
	/**
	 * Supprime la compétition de l'application.
	 */
	
	public void delete()
	{
		for (Candidat candidat : candidats)
			remove(candidat);
		inscriptions.delete(this);
	}
	
	@Override
	public int compareTo(Competition o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return getNom();
	}
}
