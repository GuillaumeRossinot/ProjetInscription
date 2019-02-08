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
        Menu menu = new Menu("Menu Inscriptions Sportives");
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
        menu.addBack("x");
        return menu;
    }
    /* Sauvegarder et quitter le menu */
    private Option saveAndQuit() {
        return new Option("Save et Quitter", "s", new Action() {
            @Override
            public void optionSelected() {
                try {
                    Inscriptions.getInscriptions().sauvegarder();
                    Action.QUIT.optionSelected();
                }
                catch (Exception e) {
                    System.out.println("Impossible de sauvegarder : " + e);
                }
            }
        });
    }
    /* Quitter sans sauvegarder */
    private Option quitWhitoutSaving() { return new Option("Quitter sans sauvegarder", "q", Action.QUIT); }





    /* Menu pour les Competitions */

    private Menu CompetitionMenu() {
        Menu menu = new Menu("Menu Competitions", "Competition", "a");
        menu.add( addCompetition() );
        menu.add( displayCompetition() );
        menu.add( manageCompetition() );

        menu.addBack("x");
        return menu;
    }

    /* Affichage competitions */
    private Option displayCompetition() { return new Option(
            "Afficher competitions", "2",
            new Action() {
                @Override
                public void optionSelected() {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Inscriptions.getInscriptions().getCompetitions());
                }
            });
    }
    /* Ajout competitions */
    private Option addCompetition() {
        return new Option(
                "Ajouter competition", "1",
                new Action() {
                    @Override
                    public void optionSelected()
                    {
                        String nomCompetition = InOut.getString("Nom competition: ");
                        System.out.println("Fin competition :");
                        int day = InOut.getInt("  Jour (jj) : ");
                        int month = InOut.getInt("  Mois (mm) : ");
                        int year = InOut.getInt("  Année (yyyy) : ");
                        int choice = InOut.getInt("competition en equipe ? (2 pour Non, 1 pour oui) ");
                        boolean isTeam = false;
                        if(choice == 1){
                            isTeam = true;
                        } else if(choice != 2 && choice != 1 ){
                            System.out.println("Erreur de saisie, veuillez entrer 1 ou 2 ! ");
                        }
                        LocalDate dateClose = LocalDate.of(year, month, day);
                        Inscriptions.getInscriptions().createCompetition(nomCompetition, dateClose, isTeam);
                    }
                }
        );
    }
    /* Supprimer competitions */
    private Option deleteCompetition() {
        return new List<>(
                "Supprimer competition", "1",
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
    private Menu manageCompetition() {
        Menu menu = new Menu("Menu Gestion competitions", "Gestion competitions", "3");
        menu.add( deleteCompetition() );
        menu.add( modifyCompetition() );
        menu.addBack("x");
        return menu;
    }

    private Option modifyCompetition() {
        return new List<>(
                "Modifier competition", "2",
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
        Menu menu = new Menu("Menu Modification competition : " + competition.getNom());
        menu.add(changerNomCompetition(competition));
        menu.add(changerDateCompetition(competition));

        if(competition.estEnEquipe()){
            menu.add(addEquipeToCompetition(competition));
        }else{
            menu.add(addPersonToCompetition(competition));
        }
        menu.addBack("x");

        return menu;
    }
    /* changer nom competitions */
    private Option changerNomCompetition(Competition competition) {
        return new Option(
                "Changer nom competition", "1",
                new Action() {
                    @Override
                    public void optionSelected() {
                        competition.setNom(InOut.getString("Nouveau nom competition : "));
                    }
                }
        );
    }
    /* changer date de fin competitions */
    private Option changerDateCompetition(Competition competition) {
        return new Option(
                "Changer date de fin competition", "2",
                new Action() {
                    @Override
                    public void optionSelected() {
                        System.out.println("Nouvelle fin competition : ");
                        int day = InOut.getInt("  Jour (jj) : ");
                        int month = InOut.getInt("  Mois (mm) : ");
                        int year = InOut.getInt("  Année (yyyy) : ");
                        LocalDate dateCloture = LocalDate.of(year, month, day);
                        competition.setDateCloture(dateCloture);
                    }
                }
        );
    }
    /* Ajout personne competitions */
    private Option addPersonToCompetition(Competition competition) {
        return new List<>(
                "Ajouter personne à la competition", "3",
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
                "Ajouter équipe à la competition", "4",
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
        Menu menu = new Menu("Menu equipes", "equipes", "b");
        menu.add( addEquipe() );
        menu.add( displayEquipe() );
        menu.add( manageEquipe() );
        menu.addBack("x");
        return menu;
    }
    /* Afficher equipe */
    private Option displayEquipe() { return new Option(
            "Afficher equipes", "2",
            new Action() {
                @Override
                public void optionSelected() {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Inscriptions.getInscriptions().getEquipes());
                }
            });
    }
    /* Ajout equipe */
    private Option addEquipe() { return new Option(
            "Ajouter equipe", "1",
            new Action()
            {
                @Override
                public void optionSelected()
                {
                    String a = InOut.getString("Nom equipe: ");
                    Inscriptions.getInscriptions().createEquipe(a);
                }
            }
    ); }
    /* Supprimer equipe */
    private Option deleteEquipe() {
        return new List<>(
                "Supprimer equipe", "1",
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
    private Menu manageEquipe() {
        Menu menu = new Menu("Menu Gestion equipes", "Gestion equipes","3");
        menu.add( deleteEquipe() );
        menu.add( modifyEquipe() );
        menu.addBack("x");
        return menu;
    }
    /* Modifier equipe */
    private Option modifyEquipe() {
        return new List<>(
                "Modifier equipe", "2",
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
        Menu menu = new Menu("Menu Modification equipe : " + equipe.getNom());
        menu.add(changerNomEquipe(equipe));
        menu.add(addPersonToEquipe(equipe));
        menu.add(removePersonToEquipe(equipe));
        menu.addBack("x");

        return menu;
    }
    /* Changer nom equipe */
    private Option changerNomEquipe(Equipe equipe) {
        return new Option(
                "Changer nom equipe", "1",
                new Action() {
                    @Override
                    public void optionSelected() {
                        equipe.setNom(InOut.getString("Nouveau nom equipe : "));
                    }
                }
        );
    }
    /* Ajout personne equipe */
    private Option addPersonToEquipe(Equipe equipe) {
        return new List<>(
                "Ajouter personne  a l'equipe", "2",
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
    private Option removePersonToEquipe(Equipe equipe) {
        return new List<>(
                "Supprimer personne de l'equipe", "3",
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
        Menu menu = new Menu("Menu Menu personnes", "Personnes", "c");
        menu.add( addPersonne() );
        menu.add( displayPersonne() );
        menu.add( managePers() );
        menu.addBack("x");
        return menu;
    }
    /* Afficher personne */
    private Option displayPersonne() { return new Option(
            "Afficher personnes", "2",
            new Action() {
                @Override
                public void optionSelected() {
                    System.out.println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + Inscriptions.getInscriptions().getPersonnes());
                }
            }
    );
    }
    /* Ajout personne */
    private Option addPersonne() {
        return new Option("Ajouter personne", "1",
                new Action() {
                    @Override
                    public void optionSelected()
                    {
                        String firstname = InOut.getString("Prénom : ");
                        String name = InOut.getString("Nom : ");
                        String email = InOut.getString("Adresse e-mail : ");
                        Inscriptions.getInscriptions().createPersonne(name, firstname, email);
                    }
                }
        );
    }
    /* Supprimer personne */
    private Option deletePersonne() {
        return new List<>(
                "Supprimer personne", "1",
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
    private Menu managePers() {
        Menu menu = new Menu("Menu Gestion personnes", "Gestion personnes","3");
        menu.add( deletePersonne() );
        menu.add( modifyPersonne() );
        menu.addBack("x");
        return menu;
    }
    /* Modifier personne */
    private Option modifyPersonne() {
        return new List<>(
            "Modifier personne", "2",
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
        Menu menu = new Menu("Menu Modif personne :  " + personne.getNom());
        menu.add(changerName(personne));
        menu.add(changerFirstname(personne));
        menu.add(changerMail(personne));
        menu.addBack("x");

        return menu;
    }
    /* Changer nom personne */
    private Option changerName(Personne personne) {
        return new Option(
                "Changer nom", "1",
                new Action() {
                    @Override
                    public void optionSelected() {
                        personne.setNom(InOut.getString("Nouveau nom : "));
                    }
                }
        );
    }
    /* Changer prenom personne */
    private Option changerFirstname(Personne personne) {
        return new Option(
                "Changer prénom", "2",
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
                        personne.setMail(InOut.getString("Nouveau email : "));
                    }
                }
        );
    }
    
}
