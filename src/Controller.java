import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.Collections;

public class Controller {

    private Model modele;
    private View view;
    private boolean distributionFini;

    public Controller(Model mod, View view){
        modele=mod;
        this.view = view;
        distributionFini = false;
    }

    public void lancerDistribution()
    {
        view.getBoutonDistribuer().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                modele.distribution();
                distributionFini = view.distribution(view.getCardsViews());
                view.distribution(view.getCardsViews());

                for (Carte c : modele.getCarteJoueur()) {
                    System.out.println(c.getNumero() + " / " + c.getType());
                }
                modele.trierCartesAffichee();
                System.out.println("---------------------------------------");
                for (Carte c : modele.getCarteJoueur()) {
                    System.out.println(c.getNumero() + " / " + c.getType());
                }
                view.getBoutonDistribuer().setDisable(true);
                view.getBoutonDistribuer().setOpacity(0);
            }
        });


    }

    public void enchere()
    {
        if(view.getEnd()) {
            view.getBoutonPrise().setDisable(false);
            view.getBoutonGarde().setDisable(false);
            view.getBoutonGardeSansChien().setDisable(false);
            view.getBoutonGardeContreChien().setDisable(false);
            view.getBoutonPrise().setOpacity(1);
            view.getBoutonGarde().setOpacity(1);
            view.getBoutonGardeSansChien().setOpacity(1);
            view.getBoutonGardeContreChien().setOpacity(1);
        }

        view.getBoutonPrise().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Enchere

            }
        });
    }



}
