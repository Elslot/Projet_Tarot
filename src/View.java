
import javafx.animation.*;
import javafx.beans.binding.MapBinding;
import javafx.collections.ObservableMap;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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

    private Model modele;


    public View(Model modele) {

        this.modele = modele;

        cardviews= new ArrayList<CarteView>();

        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");

        //      Fenetre.setFullScreen(true);
        //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        Group root = new Group();
        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);
        Group cards = new Group();
        root.getChildren().add(cards);

        for (int i = 0; i < 78; i++) {
            modele.getPaquet().get(i).setPosX(modele.getPaquet().get(i).getPosX()-(0.3*i)); //Afin de donner de la perspective aux cartes déjà placées
            modele.getPaquet().get(i).setPosY(modele.getPaquet().get(i).getPosY()-(0.3*i)); //
            CarteView cartePaquetView = new CarteView(modele.getPaquet().get(i));
            cardviews.add(cartePaquetView);
            root.getChildren().add(cartePaquetView);
        }
        Fenetre.show();
    }

    public void distributionRotation(ArrayList<CarteView> cards) {
        for (int i = 0; i < 78; i++) {
            int startx = (int) cards.get(i).getModel().getPosX() - 150;
            int starty = (int) cards.get(i).getModel().getPosY();

            if (i % 13 >= 0 && i % 13 <= 2) {
                TransitionAutreJoueur(cards.get(i), startx, starty,-500,cards.get(i).getModel().SCREEN_H_MODEL/2-cards.get(i).CARD_H, true);
            }
            if (i % 13 >= 3 && i % 13 <= 5) {
                TransitionAutreJoueur(cards.get(i), startx, starty,cards.get(i).getModel().SCREEN_W_MODEL/2-cards.get(i).CARD_H,-400, false);
            }
            if (i % 13 >= 6 && i % 13 <= 8) {
                TransitionAutreJoueur(cards.get(i), startx, starty,cards.get(i).getModel().SCREEN_W_MODEL+cards.get(i).CARD_W,cards.get(i).getModel().SCREEN_H_MODEL/2-cards.get(i).CARD_H, true);
            }

            // Coder la distrib chien/Joueur trouver un truc qui attendent entre chaque carte !

 /*           if (i % 13 == 9) {
                distribChien();
            }
            if (i % 13 >= 10 && i % 13 <= 12) {
                distribJoueur();
            } */


 /*           TranslateTransition translateTransition =
                    new TranslateTransition(Duration.millis(2000), card);
            translateTransition.setFromX(card.getX());
            translateTransition.setToX(card.getX() + 200);
            translateTransition.setFromY(card.getY());
            translateTransition.setToY(card.getY());
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(true); */





 /*       ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(2000), card);
        scaleTransition.setToX(0.5f);
        scaleTransition.setToY(0.5f);
        scaleTransition.setCycleCount(1); */

/*            SequentialTransition sequentialTransition = new SequentialTransition();
            sequentialTransition.getChildren().addAll(
                    translateTransition
            );

            sequentialTransition.setCycleCount(1);
            sequentialTransition.play();

            card.setX(card.getX() + 200);


            secondTransition(card);*/
/*
        SequentialTransition sequentialTransition2 = new SequentialTransition();
        sequentialTransition2.getChildren().addAll(
                translateTransitionTop,
                rotateTransition
        );

        sequentialTransition2.setCycleCount(1);
        sequentialTransition2.play();*/


        }
    }

    public void TransitionAutreJoueur(CarteView card, int x, int y, int finalx, int finaly, boolean rotate){
        TranslateTransition translateTransition=
                new TranslateTransition(Duration.millis(2000), card);
        translateTransition.setFromX(x);
        translateTransition.setToX(finalx);
        translateTransition.setFromY(y);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();



        parallelTransition.getChildren().addAll(
                translateTransition);

        if (rotate) {

            RotateTransition rotateTransition =
                    new RotateTransition(Duration.millis(3000), card);
            rotateTransition.setByAngle(90f);
            rotateTransition.setCycleCount(1);


            parallelTransition.getChildren().addAll(
                    rotateTransition
            );

        }

        parallelTransition.setCycleCount(1);
        parallelTransition.play();
    }

    public void secondTransition(CarteView card, int x, int y){

        TranslateTransition translateTransitionTop =
                new TranslateTransition(Duration.millis(2000), card);
        translateTransitionTop.setFromX(card.getX());
        translateTransitionTop.setToX(Carte.SCREEN_W_MODEL/2 - 200);
        translateTransitionTop.setFromY(card.getY());
        translateTransitionTop.setToY(card.getY()-400);
        translateTransitionTop.setCycleCount(1);
        translateTransitionTop.setAutoReverse(true);

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(3000), card);
        rotateTransition.setByAngle(90f);
        rotateTransition.setCycleCount(1);

        SequentialTransition sequentialTransition2 = new SequentialTransition();
        sequentialTransition2.getChildren().addAll(
                translateTransitionTop,
                rotateTransition
        );

        sequentialTransition2.setCycleCount(1);
        sequentialTransition2.play();
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
                cartetest.setX((j - 1) * (150 + 20) + (150 + 20));
                cartetest.setY((i - 1) * (200 + 20) + (200 + 20));

                // root.getChildren().add(cartetest);
                k++;

            }
        }
    }

    public ArrayList<CarteView> getCardViews ()
    {
        return cardviews;
    }

    @Override
    public void update (Observable o, Object arg){

    }
}


