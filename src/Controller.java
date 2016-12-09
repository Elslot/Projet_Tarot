import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

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
            view.AffichageChien(); //C'est une prise, on affiche le chien.
            ecart(); //On réalise l'écart ensuite, avec les évènements de clics et les changements des arrays.
        });
        view.getBoutonGarde().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true);
            view.AffichageChien();
            ecart();
        });

        //La garde sans chien et contre le chien sont des enchères qui ne nécessitent pas d'écart, et terminent la distribution, donc on quitte l'application après le clic sur les
        //boutons respectifs.
        view.getBoutonGardeSansChien().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true);
            view.cacherBouton(view.getBoutonQuitter(), false); //On affiche le bouton quitter
            view.DepartChien(); //On fait partir graphiquement le chien vu qu'ion ne l'intègre pas à son jeu.
            quitter(); //On amorce la lecture de l'évènement du clic sur le bouton quitter.
        });
        view.getBoutonGardeContreChien().setOnMouseClicked(event -> {
            view.cacherBoutonEnchere(true);
            view.cacherBouton(view.getBoutonQuitter(), false);
            view.DepartChien();
            quitter();
        });
    }

    /* Cette fonction fait toutes les fonctions et les lectures d'évènements nécessairent au déroulement du tri
     * L'entrée booléenne "triApresEcart" permet de savoir si le tri est effectué avant ou après l'écart, afin de savoir si on doit ou non afficher les boutons des enchères
     * juste après le tri */
    public void tri(boolean triApresEcart) {
        modele.trier(modele.getCarteJoueur());
        view.getBoutonTrier().setOnMouseClicked(event -> {
            view.AppelTri(triApresEcart);
            view.cacherBouton(view.getBoutonTrier(), true);
        });
    }

    public void ecart() {
        final int[] taille_ecart = {0};
        int i =0;
        while(i < view.getCartesJoueur().size()) {
            int finalI = i;
            if (taille_ecart[0] != -1) {
                view.getCartesJoueur().get(i).setOnMouseClicked(event1 -> {
                    if (modele.licite(view.getCartesJoueur().get(finalI).getCarteModel())) {
                        if (!view.getCartesJoueur().get(finalI).getCarteModel().getAjouteEcart()) {
                            if (taille_ecart[0] < 6) {
                                view.getCartesJoueur().get(finalI).getCarteModel().setAjouteEcart(true);
                                modele.getEcart().add(view.getCartesJoueur().get(finalI).getCarteModel());
                                view.ChoixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getCarteModel().getAjouteEcart());
                                taille_ecart[0]++;
                            }
                        } else {
                            modele.getEcart().remove(view.getCartesJoueur().get(finalI).getCarteModel());
                            view.getCartesJoueur().get(finalI).getCarteModel().setAjouteEcart(false);
                            view.ChoixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getCarteModel().getAjouteEcart());
                            taille_ecart[0]--;
                        }
                    }
                    if (taille_ecart[0] == 6) {
                        view.cacherBouton(view.getBoutonOK(), false);
                    } else {
                        view.cacherBouton(view.getBoutonOK(), true);
                    }
                });
                i++;
            }
        }
        view.getBoutonOK().setOnMouseClicked(event -> {
            view.cacherBouton(view.getBoutonOK(), true);
            modele.Ecart();
            view.TransitionEcartChien();
            tri(true);
            taille_ecart[0] = -1;
        });

    }

    public void quitter()
    {
        view.getBoutonQuitter().setOnMouseClicked(event -> view.getStage().close());
    }

}
