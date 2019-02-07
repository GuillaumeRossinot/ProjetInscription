package inscriptions;

import java.time.LocalDate;
import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.rendering.examples.util.InOut;

public class MenuClient {

    /*
     * Menu de base
     */

    public Menu menuHome() {
        Menu menu = new Menu("Menu | Inscriptions Sportives");
        /* ajout du menu compétition */
        menu.add( CompetitionMenu() );
        /* ajout du menu équipe*/
        menu.add(equipeMenu());
        /* ajout du menu personne */
        menu.add(personneMenu());
        
        menu.add( QuitMenu() );
        
        
        
        return menu;
    }
    /* Choix exit */
    private Menu QuitMenu() {
        Menu menu = new Menu("Quitter", "q");
        menu.add(saveAndQuit());
        menu.add(quitWhitoutSaving());
        menu.addBack("b");
        return menu;
    }
    /* Sauvegarder et quitter le menu */
    private Option saveAndQuit() {
        return new Option("Sauvegarder et Quitter", "s", new Action() {
            @Override
            public void optionSelected() {
                try {
                    Inscriptions.getInscriptions().sauvegarder();
                    Action.QUIT.optionSelected();
                }
                catch (Exception e) {
                    System.out.println("Impossible de sauvegarder les données ! : " + e);
                }
            }
        });
    }
    /* Quitter sans sauvegarder */
    private Option quitWhitoutSaving() { return new Option("Quitter sans sauvegarder", "q", Action.QUIT); }





    /* Menu pour les Competitions */

    private Menu CompetitionMenu() {
        Menu menu = new Menu("Menu | Compétitions", "Compétition", "c");
        menu.add( addCompetitionOption() );
        menu.add( displayCompetitionOption() );
        menu.add( manageCompetitionMenu() );

        menu.addBack("b");
        return menu;
    }

    /* Affichage competitions */
    private Option displayCompetitionOption() { return new Option(
            "Afficher les compétitions", "2",
            new Action() {
                @Override
                public void optionSelected() {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Inscriptions.getInscriptions().getCompetitions());
                }
            });
    }
    /* Ajout competitions */
    private Option addCompetitionOption() {
        return new Option(
                "Ajouter une compétition", "1",
                new Action() {
                    @Override
                    public void optionSelected()
                    {
                        String nomCompetition = InOut.getString("Nom de la compétition: ");
                        System.out.println("Fin de la compétition :");
                        int jour = InOut.getInt("  Jour (jj) : ");
                        int mois = InOut.getInt("  Mois (mm) : ");
                        int annee = InOut.getInt("  Année (yyyy) : ");
                        int choice = InOut.getInt("La compétition se déroule t'elle en équipe ? (0 pour Non, 1 pour oui) ");
                        boolean isTeam = false;
                        if(choice == 1){
                            isTeam = true;
                        } else if(choice != 0 && choice != 1 ){
                            System.out.println("Erreur de saisie, veuillez entrer 1 ou 0 ! ");
                        }
                        LocalDate dateCloture = LocalDate.of(annee, mois, jour);
                        Inscriptions.getInscriptions().createCompetition(nomCompetition, dateCloture, isTeam);
                    }
                }
        );
    }
    /* Supprimer competitions */
    private Option deleteCompetitionOption() {
        return new List<>(
                "Supprimer une compétition", "1",
                new ListData<Competition>()
                {
                    @Override
                    public java.util.List<Competition> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getCompetitions());
                    }
                },
                new ListAction<Competition>()
                {
                    @Override
                    public void itemSelected(int i, Competition competition) {
                        competition.delete();
                    }

                }
        );
    }
    /* Modifier competitions */
    private Menu manageCompetitionMenu() {
        Menu menu = new Menu("Menu | Gestion des compétitions", "Gestion des compétitions", "3");
        menu.add( deleteCompetitionOption() );
        menu.add( modifyCompetitionOption() );
        menu.addBack("b");
        return menu;
    }

    private Option modifyCompetitionOption() {
        return new List<>(
                "Modifier une compétition", "2",
                new ListData<Competition>()
                {
                    @Override
                    public java.util.List<Competition> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getCompetitions());
                    }
                },
                new ListOption<Competition>()
                {
                    @Override
                    public Option getOption(Competition competition) {
                        return modifyCompetition(competition);
                    }
                }
        );
    }
    /* Menu modification equipe */
    private Option modifyCompetition(Competition competition) {
        Menu menu = new Menu("Menu | Modification competition : " + competition.getNom());
        menu.add(changerNomCompetition(competition));
        menu.add(changerDateCompetition(competition));

        if(competition.estEnEquipe()){
            menu.add(addEquipeToCompetition(competition));
        }else{
            menu.add(addPersonToCompetition(competition));
        }
        menu.addBack("b");

        return menu;
    }
    /* changer nom competitions */
    private Option changerNomCompetition(Competition competition) {
        return new Option(
                "Changer le nom de la compétition", "1",
                new Action() {
                    @Override
                    public void optionSelected() {
                        competition.setNom(InOut.getString("Nouveau nom de la compétition : "));
                    }
                }
        );
    }
    /* changer date de fin competitions */
    private Option changerDateCompetition(Competition competition) {
        return new Option(
                "Changer la date de fin de la compétition", "2",
                new Action() {
                    @Override
                    public void optionSelected() {
                        System.out.println("Nouvelle fin de la compétition : ");
                        int jour = InOut.getInt("  Jour (jj) : ");
                        int mois = InOut.getInt("  Mois (mm) : ");
                        int annee = InOut.getInt("  Année (yyyy) : ");
                        LocalDate dateCloture = LocalDate.of(annee, mois, jour);
                        competition.setDateCloture(dateCloture);
                    }
                }
        );
    }
    /* Ajout personne competitions */
    private Option addPersonToCompetition(Competition competition) {
        return new List<>(
                "Ajouter une personne à la competition", "3",
                new ListData<Personne>()
                {
                    @Override
                    public java.util.List<Personne> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getPersonnes());
                    }
                },
                new ListAction<Personne>()
                {
                    @Override
                    public void itemSelected(int i, Personne personne) {
                        competition.add(personne);
                    }

                }
        );
    }
    /* Ajout equipe competitions */
    private Option addEquipeToCompetition(Competition competition) {
        return new List<>(
                "Ajouter une équipe à la competition", "4",
                new ListData<Equipe>()
                {
                    @Override
                    public java.util.List<Equipe> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getEquipes());
                    }
                },
                new ListAction<Equipe>()
                {
                    @Override
                    public void itemSelected(int i, Equipe equipe) {
                        competition.add(equipe);
                    }
                }
        );
    }

    /*
     * Menu pour les equipes
     */

    private Menu equipeMenu() {
        Menu menu = new Menu("Menu | Équipes", "Équipes", "e");
        menu.add( addEquipeOption() );
        menu.add( displayEquipeOption() );
        menu.add( manageEquipeMenu() );
        menu.addBack("b");
        return menu;
    }
    /* Afficher equipe */
    private Option displayEquipeOption() { return new Option(
            "Afficher les équipes", "2",
            new Action() {
                @Override
                public void optionSelected() {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Inscriptions.getInscriptions().getEquipes());
                }
            });
    }
    /* Ajout equipe */
    private Option addEquipeOption() { return new Option(
            "Ajouter une équipe", "1",
            new Action()
            {
                @Override
                public void optionSelected()
                {
                    String a = InOut.getString("Nom de l'équipe: ");
                    Inscriptions.getInscriptions().createEquipe(a);
                }
            }
    ); }
    /* Supprimer equipe */
    private Option deleteEquipeOption() {
        return new List<>(
                "Supprimer une équipe", "1",
                new ListData<Equipe>()
                {
                    @Override
                    public java.util.List<Equipe> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getEquipes());
                    }
                },
                new ListAction<Equipe>()
                {
                    @Override
                    public void itemSelected(int i, Equipe equipe) {
                        equipe.delete();
                    }

                }
        );
    }
    /* Gestion equipe */
    private Menu manageEquipeMenu() {
        Menu menu = new Menu("Menu | Gestion des équipes", "Gestion des équipes","3");
        menu.add( deleteEquipeOption() );
        menu.add( modifyEquipeOption() );
        menu.addBack("b");
        return menu;
    }
    /* Modifier equipe */
    private Option modifyEquipeOption() {
        return new List<>(
                "Modifier une équipe", "2",
                new ListData<Equipe>(){

                    @Override
                    public java.util.List<Equipe> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getEquipes());
                    }
                },
                new ListOption<Equipe>()
                {
                    @Override
                    public Option getOption(Equipe equipe) {
                        return modifyEquipe(equipe);
                    }
                }
        );
    }
    /* Menu modification equipe */
    private Option modifyEquipe(Equipe equipe) {
        Menu menu = new Menu("Menu | Modification equipe : " + equipe.getNom());
        menu.add(changerNomEquipe(equipe));
        menu.add(addPersonToEquipeOption(equipe));
        menu.add(removePersonToEquipeOption(equipe));
        menu.addBack("b");

        return menu;
    }
    /* Changer nom equipe */
    private Option changerNomEquipe(Equipe equipe) {
        return new Option(
                "Changer le nom de l'équipe", "1",
                new Action() {
                    @Override
                    public void optionSelected() {
                        equipe.setNom(InOut.getString("Nouveau nom de l'équipe : "));
                    }
                }
        );
    }
    /* Ajout personne equipe */
    private Option addPersonToEquipeOption(Equipe equipe) {
        return new List<>(
                "Ajouter une personne à l'équipe", "2",
                new ListData<Personne>()
                {
                    @Override
                    public java.util.List<Personne> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getPersonnes());
                    }
                },
                new ListAction<Personne>()
                {
                    @Override
                    public void itemSelected(int i, Personne personne) {
                        equipe.add(personne);
                    }

                }
        );
    }
    /* Supprimer personne equipe */
    private Option removePersonToEquipeOption(Equipe equipe) {
        return new List<>(
                "Supprimer une personne de l'équipe", "3",
                new ListData<Personne>()
                {
                    @Override
                    public java.util.List<Personne> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getPersonnes());
                    }
                },
                new ListAction<Personne>()
                {
                    @Override
                    public void itemSelected(int i, Personne personne) {
                        equipe.remove(personne);
                    }

                }
        );
    }
    
    /* Menu pour les personnes */

    private Menu personneMenu() {
        Menu menu = new Menu("Menu | Menu des personnes", "Personnes", "p");
        menu.add( addPersonneOption() );
        menu.add( displayPersonneOption() );
        menu.add( managePersMenu() );
        menu.addBack("b");
        return menu;
    }
    /* Afficher personne */
    private Option displayPersonneOption() { return new Option(
            "Afficher les personnes", "2",
            new Action() {
                @Override
                public void optionSelected() {
                    System.out.println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Inscriptions.getInscriptions().getPersonnes());
                }
            }
    );
    }
    /* Ajout personne */
    private Option addPersonneOption() {
        return new Option("Ajouter une personne", "1",
                new Action() {
                    @Override
                    public void optionSelected()
                    {
                        String prenom = InOut.getString("Prénom de la personne: ");
                        String nom = InOut.getString("Nom de la personne: ");
                        String email = InOut.getString("Adresse e-mail de la personne: ");
                        Inscriptions.getInscriptions().createPersonne(nom, prenom, email);
                    }
                }
        );
    }
    /* Supprimer personne */
    private Option deletePersonneOption() {
        return new List<>(
                "Supprimer une personne", "1",
                new ListData<Personne>(){

                    @Override
                    public java.util.List<Personne> getList() {
                        return new ArrayList<>(Inscriptions.getInscriptions().getPersonnes());
                    }
                },
                new ListAction<Personne>()
                {
                    @Override
                    public void itemSelected(int i, Personne personne) {
                        personne.delete();
                    }

                }
        );
    }
    /* Gestion personne */
    private Menu managePersMenu() {
        Menu menu = new Menu("Menu | Gestion des personnes", "Gestion des personnes","3");
        menu.add( deletePersonneOption() );
        menu.add( modifyPersonneOption() );
        menu.addBack("b");
        return menu;
    }
    /* Modifier personne */
    private Option modifyPersonneOption() {
        return new List<>(
            "Modifier une personne", "2",
            new ListData<Personne>(){

                @Override
                public java.util.List<Personne> getList() {
                    return new ArrayList<>(Inscriptions.getInscriptions().getPersonnes());
                }
            },
            new ListOption<Personne>()
            {
                @Override
                public Option getOption(Personne personne) {
                    return modifyPersonne(personne);
                }
            }
        );
    }
    /* menu modification personne */
    private Option modifyPersonne(Personne personne) {
        Menu menu = new Menu("Menu | Modifier une personne :  " + personne.getNom());
        menu.add(changerNom(personne));
        menu.add(changerPrenom(personne));
        menu.add(changerMail(personne));
        menu.addBack("b");

        return menu;
    }
    /* Changer nom personne */
    private Option changerNom(Personne personne) {
        return new Option(
                "Changer le nom", "1",
                new Action() {
                    @Override
                    public void optionSelected() {
                        personne.setNom(InOut.getString("Nouveau nom : "));
                    }
                }
        );
    }
    /* Changer prenom personne */
    private Option changerPrenom(Personne personne) {
        return new Option(
                "Changer le prénom", "2",
                new Action() {
                    @Override
                    public void optionSelected() {
                        personne.setPrenom(InOut.getString("Nouveau prénom : "));
                    }
                }
        );
    }
    /* Changer email personne */
    private Option changerMail(Personne personne) {
        return new Option(
                "Changer l'email", "3",
                new Action() {
                    @Override
                    public void optionSelected() {
                        personne.setMail(InOut.getString("Nouvelle adresse email : "));
                    }
                }
        );
    }
    
}
