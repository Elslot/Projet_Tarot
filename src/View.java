
import javafx.animation.*;
import javafx.beans.binding.MapBinding;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Dimension;


import java.lang.reflect.Array;
import java.util.*;

public class View extends Stage implements Observer {

    protected static final int windowSizeW = 400;
    protected static final int windowSizeH = 100;

    static Dimension DIMENSION_VIEW = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_H_VIEW = (int) DIMENSION_VIEW.getHeight();
    static int SCREEN_W_VIEW = (int) DIMENSION_VIEW.getWidth();

    protected Stage Fenetre;
    private Scene scene;
    private ArrayList<CarteView> cardviews;
    private Group root;
    private Group Gcards;
    private boolean end;


    private Model modele;

    private Button bDistribution;
    private Button bGarde;
    private Button bPrise;
    private Button bGardeSansChien;
    private Button bGardeContreChien;

    public View(Model modele) {

        this.modele = modele;
        end = false;

        cardviews= new ArrayList<CarteView>();

        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");

        //      Fenetre.setFullScreen(true);
        //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        root = new Group();

        //Inistialisation de chaque bouton
        //"Distribuer" qui sera affiché dés le début
        bDistribution = new Button("Distribuer");
        bDistribution.setPrefSize(150, 50);
        bDistribution.setLayoutX(SCREEN_W_VIEW/2 - bDistribution.getPrefWidth()/2);
        bDistribution.setLayoutY(3*SCREEN_H_VIEW/4 - bDistribution.getPrefHeight()/2);
        bDistribution.setFont((Font.font(20)));

        //Les boutons de choix des enchères qui seront invisbles et désactivés au début
        bPrise = new Button("Prise");
        bPrise.setPrefSize(150, 50);
        bPrise.setLayoutX(2*SCREEN_W_VIEW/7 - bPrise.getPrefWidth()/2);
        bPrise.setLayoutY(SCREEN_H_VIEW/12 - bPrise.getPrefHeight()/2);
        bPrise.setFont((Font.font(12)));
        bPrise.setDisable(true);
        bPrise.setOpacity(0);

        bGarde = new Button("Garde");
        bGarde.setPrefSize(150, 50);
        bGarde.setLayoutX(2.75*SCREEN_W_VIEW/7 - bGarde.getPrefWidth()/2);
        bGarde.setLayoutY(SCREEN_H_VIEW/12 - bGarde.getPrefHeight()/2);
        bGarde.setFont((Font.font(12)));
        bGarde.setDisable(true);
        bGarde.setOpacity(0);

        bGardeSansChien = new Button("Garde sans chien");
        bGardeSansChien.setPrefSize(150, 50);
        bGardeSansChien.setLayoutX(3.5*SCREEN_W_VIEW/7 - bGardeSansChien.getPrefWidth()/2);
        bGardeSansChien.setLayoutY(SCREEN_H_VIEW/12 - bGardeSansChien.getPrefHeight()/2);
        bGardeSansChien.setFont((Font.font(12)));
        bGardeSansChien.setDisable(true);
        bGardeSansChien.setOpacity(0);

        bGardeContreChien = new Button("Garde contre chien");
        bGardeContreChien.setPrefSize(150, 50);
        bGardeContreChien.setLayoutX(4.25*SCREEN_W_VIEW/7 - bGardeContreChien.getPrefWidth()/2);
        bGardeContreChien.setLayoutY(SCREEN_H_VIEW/12 - bGardeContreChien.getPrefHeight()/2);
        bGardeContreChien.setFont((Font.font(12)));
        bGardeContreChien.setDisable(true);
        bGardeContreChien.setOpacity(0);

        root.getChildren().add(bDistribution);
        root.getChildren().add(bPrise);
        root.getChildren().add(bGarde);
        root.getChildren().add(bGardeSansChien);
        root.getChildren().add(bGardeContreChien);

        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);
        Group cards = new Group();
        Gcards = new Group();
        root.getChildren().add(Gcards);

        for (int i = 0; i < 78; i++) {
            modele.getPaquet().get(i).setPosX(modele.getPaquet().get(i).getPosX()-(0.1*i)); //Afin de donner de la perspective aux cartes déjà placées
            modele.getPaquet().get(i).setPosY(modele.getPaquet().get(i).getPosY()-(0.1*i)); //
            CarteView cartePaquetView = new CarteView(modele.getPaquet().get(i));
          //  Scale Sprite
       //     cartePaquetView.setScaleX(0.5);
       //     cartePaquetView.setScaleY(0.5);
            cardviews.add(cartePaquetView);

            root.getChildren().add(cartePaquetView.getDos());
            Gcards.getChildren().add(cartePaquetView.getFace());
            Gcards.setOpacity(0.f);


        }
        Fenetre.show();
    }

    public boolean distribution(ArrayList<CarteView> cards) {

        int indx = 1;
        int indy = 1;
        SequentialTransition total = new SequentialTransition();
        total.setCycleCount(1);

        SequentialTransition sequential2 = new SequentialTransition();
        sequential2.setCycleCount(1);

        SequentialTransition sequential = new SequentialTransition();
        sequential.setCycleCount(1);
        sequential.setDelay(Duration.millis(10));

        for (int i = 0; i <= 77; i++) {
            double startx = cards.get(i).getX();
            double starty = cards.get(i).getY();

            if (i % 13 >= 0 && i % 13 <= 2) {
                sequential = cards.get(i).TransitionAutreJoueur(cards.get(i), startx, starty, -400, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);

                // root.getChildren().remove(cards.get(i));
            }
            if (i % 13 >= 3 && i % 13 <= 5) {
                    sequential = cards.get(i).TransitionAutreJoueur(cards.get(i), startx, starty, cards.get(i).getModel().SCREEN_W_MODEL / 2 - cards.get(i).CARD_H, -400, false, sequential);
                   // root.getChildren().remove(cards.get(i));
            }
            if (i % 13 >= 6 && i % 13 <= 8) {
                    sequential = cards.get(i).TransitionAutreJoueur(cards.get(i), startx, starty, cards.get(i).getModel().SCREEN_W_MODEL + cards.get(i).CARD_W, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);
                   // root.getChildren().remove(cards.get(i));
            }

            if (i%13 == 9)
            {
                sequential = cards.get(i).TransitionAutreJoueur(cards.get(i), startx, starty, startx+200+12*i, 100, false, sequential);
                cards.get(i).getModel().setTurned();
            }

            if (i%13 >=10)
            {
                sequential = cards.get(i).TransitionAutreJoueur(cards.get(i), startx, starty, startx-100+160*indx, 250+220*indy, false, sequential);
                cards.get(i).getModel().setTurned();
                indx ++;
                if ((indx==9) &&(indy!=2))
                {
                    indy++;
                    indx=0;
                }
            }
            //cards.get(i).positionFace();

        }


        sequential2 = cards.get(11).flip(sequential2);
        sequential2 = cards.get(12).flip(sequential2);
        sequential2 = cards.get(10).flip(sequential2);
        sequential.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                end = true;

                Gcards.setOpacity(1);
            }
        });
        total.getChildren().addAll(sequential, sequential2);
        total.play();



        end = true;
        return end;

    }

    public void Poubelle() {


        /*
        Path path = new Path();
        path.getElements().add(new MoveTo(startx, starty));

        path.getElements().add(new CubicCurveTo(0, 0, 0, 0, 0, 0));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(card);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
        pathTransition.play();
       // pathTransition.stop(); */

        //A mettre dans une autre fonction
        int k = 0;


        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 9; j++) {

                CarteView cartetest = new CarteView(modele.getCarteJoueur().get(k));
             //   cartetest.setX((j - 1) * (150 + 20) + (150 + 20));
             //   cartetest.setY((i - 1) * (200 + 20) + (200 + 20));

                // root.getChildren().add(cartetest);
                k++;

            }
        }
    }

    public ArrayList<CarteView> getCardsViews ()
    {
        return cardviews;
    }
    public Button getBoutonDistribuer()
    {
        return bDistribution;
    }

    public Button getBoutonGarde() {
        return bGarde;
    }

    public void setBoutonGarde(Button bGarde) {
        this.bGarde = bGarde;
    }

    public Button getBoutonPrise() {
        return bPrise;
    }

    public void setBoutonPrise(Button bPrise) {
        this.bPrise = bPrise;
    }

    public Button getBoutonGardeSansChien() {
        return bGardeSansChien;
    }

    public void setBoutonGardeSansChien(Button bGardeSansChien) {
        this.bGardeSansChien = bGardeSansChien;
    }

    public Button getBoutonGardeContreChien() {
        return bGardeContreChien;
    }

    public void setBoutonGardeContreChien(Button bGardeContreChien) {
        this.bGardeContreChien = bGardeContreChien;
    }

    @Override
    public void update (Observable o, Object arg){

    }

    public boolean getEnd(){return end;}
}


