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

                //Enchere
                final int[] idtest = {0};
                final int[] finalIdtest = {idtest[0]};
                    for(int i = 0; i < view.getCartesJoueur().size(); i++) {
                            int finalI = i;

                        /*boolean test = modele.depotEcart(view.getCartesJoueur().get(finalI).getModel());
                        System.out.println(test);
                        if(test) {
                            System.out.println(modele.getEcart().get(idtest).getNumero() + "/" + modele.getEcart().get(idtest).getType());
                            System.out.println("------------");
                            idtest++;
                        }*/


                        view.getCartesJoueur().get(i).setOnMouseClicked(event1 -> {
                                if(modele.depotEcart(view.getCartesJoueur().get(finalI).getModel()))
                                {
                                    view.ChoixEcart(view.getCartesJoueur().get(finalI), view.getCartesJoueur().get(finalI).getModel().getAtoujeEcart());

                                    /*System.out.println(modele.getEcart().get(finalIdtest[0]).getNumero() + "/" + modele.getEcart().get(finalIdtest[0]).getType());
                                    System.out.println("------------");
                                    finalIdtest[0]++;*/
                                }
                                /*for(int j = 0; j < modele.getEcart().size(); j++)
                                {
                                    System.out.println(modele.getEcart().get(j).getNumero() + "/" + modele.getEcart().get(j).getType());
                                    System.out.println("++++++++++++");
                                }*/
                            });
                    }
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

    public void quitter()
    {
        view.getBoutonQuitter().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.getStage().close();
            }
        });
    }

}
