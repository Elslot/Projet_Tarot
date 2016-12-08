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
                    petitSec();
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

                //Enchere
            }
        });
        view.getBoutonGarde().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                //Enchere
            }
        });
        view.getBoutonGardeSansChien().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                //Enchere
            }
        });
        view.getBoutonGardeContreChien().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.cacherBoutonEnchere(true);
                //Enchere
            }
        });
    }

    public void tri()
    {
        modele.trierCartesAffichee();
        view.getBoutonTrier().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.AppelTri();
                view.cacherBoutonEnchere(false);
                view.cacherBouton(view.getBoutonTrier(), true);
            }
        });
    }

    public void petitSec()
    {
        view.getBoutonQuitter().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getStage().close();
            }
        });
    }

}
