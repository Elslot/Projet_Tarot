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

    /* Fonction qui effectue toutes les fonctions nécessaires à l'enchère */
    public void enchere()
    {
        //Comme prise
        view.getBoutonPrise().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                view.AffichageChien();
                ecart();
            }
        });
        view.getBoutonGarde().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                view.AffichageChien();
                view.cacherBouton(view.getBoutonOK(), false);
                ecart();
            }
        });
        view.getBoutonGardeSansChien().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                view.cacherBouton(view.getBoutonQuitter(), false);
                quitter();
            }
        });
        view.getBoutonGardeContreChien().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                view.cacherBouton(view.getBoutonQuitter(), false);
                quitter();
            }
        });
    }

    public void tri(boolean triApresEcart)
    {
        modele.trier(modele.getCarteJoueur());
        view.getBoutonTrier().setOnMouseClicked(event -> {
            view.AppelTri(triApresEcart);
            view.cacherBouton(view.getBoutonTrier(), true);
        });
    }

    public void ecart()
    {
        final int[] taille_ecart = {0};
        int i =0;
        while(i < view.getCartesJoueur().size()) {
            int finalI = i;
            view.getCartesJoueur().get(i).setOnMouseClicked(event1 -> {
                if (modele.licite(view.getCartesJoueur().get(finalI).getModel())) {
                    if (!view.getCartesJoueur().get(finalI).getModel().getAjouteEcart()) {
                        if(taille_ecart[0] < 6) {
                            view.getCartesJoueur().get(finalI).getModel().setAjouteEcart(true);
                            modele.getEcart().add(view.getCartesJoueur().get(finalI).getModel());
                            view.ChoixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getModel().getAjouteEcart());
                            taille_ecart[0]++;
                        }
                    }
                    else {
                        modele.getEcart().remove(view.getCartesJoueur().get(finalI).getModel());
                        view.getCartesJoueur().get(finalI).getModel().setAjouteEcart(false);
                        view.ChoixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getModel().getAjouteEcart());
                        taille_ecart[0]--;
                    }
                }

                if(taille_ecart[0] == 6) {
                    view.cacherBouton(view.getBoutonOK(), false);
                }
                else
                {
                    view.cacherBouton(view.getBoutonOK(), true);
                }
            });
            i++;
        }
        view.getBoutonOK().setOnMouseClicked(event -> {
            modele.Ecart();
            view.TransitionEcartChien();
            view.cacherBouton(view.getBoutonOK(), true);
        });
        tri(true);
    }

    public void quitter()
    {
        view.getBoutonQuitter().setOnMouseClicked(event -> view.getStage().close());
    }

}
