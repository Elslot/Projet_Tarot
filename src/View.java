
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

    private Model modele;

    private Button bDistribution;

    public View(Model modele) {

        this.modele = modele;

        cardviews= new ArrayList<CarteView>();

        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");

        //      Fenetre.setFullScreen(true);
        //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        root = new Group();

        bDistribution = new Button("Distribuer");
        bDistribution.setPrefSize(150, 50);
        bDistribution.setLayoutX(SCREEN_W_VIEW/2 - bDistribution.getPrefWidth()/2);
        bDistribution.setLayoutY(3*SCREEN_H_VIEW/4 - bDistribution.getPrefHeight()/2);
        bDistribution.setFont((Font.font(20)));
        //bDistribution.setDisable(true);
        //bDistribution.setOpacity(0);

        root.getChildren().add(bDistribution);

        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);
        Group cards = new Group();
        root.getChildren().add(cards);

        for (int i = 0; i < 78; i++) {
            modele.getPaquet().get(i).setPosX(modele.getPaquet().get(i).getPosX()-(0.2*i)); //Afin de donner de la perspective aux cartes déjà placées
            modele.getPaquet().get(i).setPosY(modele.getPaquet().get(i).getPosY()-(0.2*i)); //
            CarteView cartePaquetView = new CarteView(modele.getPaquet().get(i));
            cardviews.add(cartePaquetView);
            root.getChildren().add(cartePaquetView);
        }
        Fenetre.show();
    }

    public boolean distribution(ArrayList<CarteView> cards) {

        int indx = 1;
        int indy = 1;
        boolean end = true;
        SequentialTransition sequential = new SequentialTransition();
        sequential.setCycleCount(1);
        sequential.setDelay(Duration.millis(10));

        for (int i = 0; i <= 77; i++) {
            double startx = cards.get(i).getX() - 190 + 0.2 * i;
            double starty = cards.get(i).getY() - 105 + 0.2 * i;

            if (i % 13 >= 0 && i % 13 <= 2) {
                sequential = TransitionAutreJoueur(cards.get(i), startx, starty, -400, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);

                // root.getChildren().remove(cards.get(i));
            }
            if (i % 13 >= 3 && i % 13 <= 5) {
                    sequential = TransitionAutreJoueur(cards.get(i), startx, starty, cards.get(i).getModel().SCREEN_W_MODEL / 2 - cards.get(i).CARD_H, -400, false, sequential);
                   // root.getChildren().remove(cards.get(i));
            }
            if (i % 13 >= 6 && i % 13 <= 8) {
                    sequential = TransitionAutreJoueur(cards.get(i), startx, starty, cards.get(i).getModel().SCREEN_W_MODEL + cards.get(i).CARD_W, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);
                   // root.getChildren().remove(cards.get(i));
            }

            if (i%13 == 9)
            {
                sequential = TransitionAutreJoueur(cards.get(i), startx, starty, startx+100+12*i, 100, false, sequential);
            }

            if (i%13 >=10)
            {
                sequential = TransitionAutreJoueur(cards.get(i), startx, starty, startx-150+160*indx, 250+220*indy, false, sequential);
                indx ++;
                if ((indx==9) &&(indy!=2))
                {
                    indy++;
                    indx=0;
                }
            }

        }
            sequential.play();
        end = true;
        return end;

    }



    public SequentialTransition TransitionAutreJoueur(CarteView card, double x, double y, double finalx, double finaly, boolean rotate, SequentialTransition sequential){
        TranslateTransition translateTransition=
                new TranslateTransition(Duration.millis(100), card);
        translateTransition.setFromX(x);
        translateTransition.setToX(finalx);
        translateTransition.setFromY(y);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);


  /*      ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().add(
                translateTransition); */

        if (rotate) {

            rotate(card, sequential);
        }
        sequential.getChildren().add(translateTransition);
       // parallelTransition.setCycleCount(1);
        return sequential;
    }

    public SequentialTransition rotate( CarteView card, SequentialTransition sequential){
        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(50), card);
        rotateTransition.setByAngle(90f);
        rotateTransition.setCycleCount(1);
        sequential.getChildren().add(rotateTransition);
        return sequential;
    }

    public void TransitionJoueur(CarteView card, double x, double y, double finalx, double finaly, boolean rotate, SequentialTransition sequential){


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

    public ArrayList<CarteView> getCardsViews ()
    {
        return cardviews;
    }
    public Button getBoutonDistribuer()
    {
        return bDistribution;
    }

    @Override
    public void update (Observable o, Object arg){

    }
}


