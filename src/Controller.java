public class Controller {

    //Le contrôleur possède un modèle et une vue car il prend en charge les évènements de la fenêtre, et en fonction des évènements effectue les fonctions de model en interne,
    // et les fonctions de view qui permet d'afficher les cartes et d'effectuer des animations. Nous avons choisis ce modèle de MVC car il est plus facile de réagir en fonction
    //des évènements lorsque controlleur gère les deux traitement de données : affichage et calcul.
    private Model modele;
    private View view;

    public Controller(Model mod, View view){
        modele=mod;
        this.view = view;
    }

    /* Foncton permettant d'effectuer divers contrôle d'évènement, et de gèrer toute la distribution de l'application derrière en fonction d'eux.
    *  Elle lance séquentitellement ces éléments*/
    public void lancerDistribution()
    {
        view.getBoutonDistribuer().setOnMouseClicked(event -> { //Fait une série d'évènement lorsqu'on clique sur distribuer
            view.cacherBouton(view.getBoutonDistribuer(), true); //Efface le bouton distribuer de la fenêtre

            modele.distribution(); //Distribue les cartes du modèle
            view.distribution(view.getCardsViews()); //Distribue les cartes graphiquement
            modele.trouverPetitSec(); //Recherche si le petit sec est présent dans les cartes du modèle ou non (pas nécessaire d'attendre la fin de la distribution graphique)

            if(modele.getPetitSec()) //Si on a trouvé le petit sec
            {
                quitter(); //On initialise la lecture d'évènement qui permet de quitter
            }
            else
            {
                tri(false); //Sinon on permet de trier
            }
        });


    }

    /* Fonction qui effectue toutes les fonctions nécessaires à l'enchère, donc l'affichage du chien et l'écart, si il y en a un */
    public void enchere()
    {
        //Comme les enchères prise et garde sont les mêmes pour la constitution de l'écart, on réalise les mêmes fontions pour les deux lorsque l'on clique sur leur bouton respectif
        view.getBoutonPrise().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true); //On cache les boutons d'enchères à chaque fois que l'on clique sur l'un deux, car on ne peut faire qu'une seule enchère dans une partie.
            view.affichageChien(); //C'est une prise, on affiche le chien.
            ecart(); //On réalise l'écart ensuite, avec les évènements de clics et les changements des arrays.
        });
        view.getBoutonGarde().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true);
            view.affichageChien();
            ecart();
        });

        //La garde sans chien et contre le chien sont des enchères qui ne nécessitent pas d'écart, et terminent la distribution, donc on quitte l'application après le clic sur les
        //boutons respectifs.
        view.getBoutonGardeSansChien().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true);
            view.cacherBouton(view.getBoutonQuitter(), false); //On affiche le bouton quitter
            view.departChien(); //On fait partir graphiquement le chien vu qu'ion ne l'intègre pas à son jeu.
            quitter(); //On amorce la lecture de l'évènement du clic sur le bouton quitter.
        });
        view.getBoutonGardeContreChien().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true);
            view.cacherBouton(view.getBoutonQuitter(), false);
            view.departChien();
            quitter();
        });
    }

    /* Cette fonction fait toutes les fonctions et les lectures d'évènements nécessairent au déroulement du tri
     * L'entrée booléenne "triApresEcart" permet de savoir si le tri est effectué avant ou après l'écart, afin de savoir si on doit ou non afficher les boutons des enchères
     * juste après le tri */
    public void tri(boolean triApresEcart) {
        modele.trier(modele.getCarteJoueur()); //On trie les cartes du joueur du modèle.
        view.getBoutonTrier().setOnMouseClicked(event -> { //On attend un clic sur le bouton trier
            view.appelTri(triApresEcart); //On trie graphiquement (le booléen est transferé, l'affichage ou non des boutons enchères ce fait dans cette fonction après le tri)
            view.cacherBouton(view.getBoutonTrier(), true); //On cache le bouton trier.
        });
    }

    /* La fonction qui gère tout l'écart. Elle appelle les fontions adéquates de modèle et view dans l'ordre. */
    public void ecart() {
        final int[] tailleEcart = {0}; //La taille de l'array de l'écart du modèle. On ne peut pas prendre directment "size()" car le pointeur est null lorsque l'array est vide,
        //et on veut tester la taille avant d'insérer quoique ce soit. Il est final car on est obligé pour pouvoir le manipuler dans l'évèment de clic sur une carte "setOnMouseClicked
        //(event -> {... . C'est donc un tableau pour permettre la modification de cette variable.

        for(int i = 0;i < view.getCartesJoueur().size(); i++) {
            int finalI = i; //Même raison que pour tailleEcart, sauf qu'on a pas besoin de le modifier sauf à chaque tour de boucle, donc on ne met qu'un int.
            if (tailleEcart[0] != -1) { //La taille de l'écart == -1 lorsque que l'écart est finie et valider, pour ne puis gérer l'évènement du clic sur une carte.
                view.getCartesJoueur().get(i).setOnMouseClicked(event1 -> { //Si on clique sur une carte
                    if (modele.licite(view.getCartesJoueur().get(finalI).getCarteModel())) { //On teste si la carte est licite
                        if (!view.getCartesJoueur().get(finalI).getCarteModel().getAjouteEcart()) { //On teste si elle n'est pas déjà dans l'écart
                            //Si non
                            if (tailleEcart[0] < 6) { //On teste si on peut encore ajouter une carte à l'écart
                                modele.getEcart().add(view.getCartesJoueur().get(finalI).getCarteModel()); //On l'ajoute dans le modèle
                                view.getCartesJoueur().get(finalI).getCarteModel().setAjouteEcart(true); //On dit que la carte est ajouté
                                view.choixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getCarteModel().getAjouteEcart()); //On montre gaphiquement
                                //qu'elle est présélectionnée
                                tailleEcart[0]++;
                            }
                        } else {
                            //Si oui
                            modele.getEcart().remove(view.getCartesJoueur().get(finalI).getCarteModel());  //On la supprime du model
                            view.getCartesJoueur().get(finalI).getCarteModel().setAjouteEcart(false);  //On dit qu'elle n'est plus dans l'écart
                            view.choixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getCarteModel().getAjouteEcart()); //On montre graphiquement qu'elle
                            //n'est plus présélectionnée.
                            tailleEcart[0]--;
                        }
                    }
                    //On ne montre pas le bouton OK tant qu'on a pas selectionné 6 cartes.
                    if (tailleEcart[0] == 6) {
                        view.cacherBouton(view.getBoutonOK(), false);
                    } else {
                        view.cacherBouton(view.getBoutonOK(), true);
                    }
                });
            }
        }

        view.getBoutonOK().setOnMouseClicked(event -> { //Lorsqu'on appuie sur le bouton OK (possible que si il est affiché)
            view.cacherBouton(view.getBoutonOK(), true); //On le cache
            //On effectue l'écart dans le modèle et dans le view
            modele.Ecart();
            view.transitionEcartChien();
            tri(true); //On trie
            tailleEcart[0] = -1; //Et on met a -1 tailleEcart pour ne plus gérer le clic des cartes.
            quitter(); //On permet de quitter après le tri
        });

    }

    /* Fonction qui gère simplement le clic sur le bouton Quitter, en fermant la fenêtre lorsque l'évènement survient. */
    public void quitter()
    {
        view.getBoutonQuitter().setOnMouseClicked(event -> view.getStage().close());
    }

}
