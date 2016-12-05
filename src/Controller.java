import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.Collections;

public class Controller {

    private Model modele;
    private View view;

    public Controller(Model mod, View view){
        modele=mod;
        this.view = view;

        view.getBoutonDistribuer().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isControlDown()) {
                    modele.melanger();
                    modele.distribution();

                    for (Carte c : modele.getCarteJoueur()) {
                        System.out.println(c.getNumero() + " / " + c.getType());
                    }
                    modele.trier(modele.getCarteJoueur());
                    System.out.println("---------------------------------------");
                    for (Carte c : modele.getCarteJoueur()) {
                        System.out.println(c.getNumero() + " / " + c.getType());
                    }
                    event.consume();
                }
            }
        });
    }

    public void lancerDistribution()
    {

    }


}
