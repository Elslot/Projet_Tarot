import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Collections;

public class Controller {

    private Model modele;
    private View view;

    public Controller(Model mod, View view){
        modele=mod;
        this.view = view;
    }

    public void lancerDistribution()
    {
        view.getBoutonDistribuer().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getBoutonDistribuer().setDisable(true);
                view.getBoutonDistribuer().setOpacity(0);

                modele.distribution();
                view.distribution(view.getCardsViews());
                modele.trouverPetitSec();

                if(modele.getPetitSec())
                {
                    quitter();
                }
                else
                {
                    tri();
                    for (Carte c : modele.getCarteJoueur()) {
                        // System.out.println(c.getNumero() + " / " + c.getType());
                    }
                }
            }
        });


    }

    public void enchere()
    {
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

    public void tri()
    {
        modele.trier(modele.getCarteJoueur());
        view.getBoutonTrier().setOnMouseClicked(event -> {
            view.AppelTri();
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
        tri();
    }

    public void quitter()
    {
        view.getBoutonQuitter().setOnMouseClicked(event -> view.getStage().close());
    }

}
