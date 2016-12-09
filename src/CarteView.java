/*
* Nom de classe : CarteView
*
* Description   : Classe implementant la parti graphique des cartes. Ce sont des groupes regroupant à chaque fois
*                 deux ImageView (dos et face). On y définit les constantes graphiques associées aux cartes
*                 ainsi que la durée des transitions qui leur sont affectées.
*/

import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class CarteView extends Group {

    final static Image global = new Image("file:./SpriteCarteFace.png");
    final static Image imagedos = new Image("file:./SpriteCarteDos.png");
    final static int SPACE_X_CARDS= 20;
    final static int SPACE_Y_CARDS= 20;
    final static int CARD_W= 149;
    final static int CARD_H= 200;
    final static int HALF_DURATION_FLIP = 100;
    final static int TRANSITION_JOUEUR_DURATION = 200;
    final static int TRANSITION_ECART_DURATION = 400;
    final static int ROTATE_DURATION = 100;

    private double x, y;
    private ImageView face;
    private ImageView dos;
    private Carte carteModel;

    public CarteView(Carte card) {

        carteModel = card;
        face = new ImageView();
        dos = new ImageView();

        x=carteModel.SCREEN_W_MODEL/10;
        y=carteModel.SCREEN_H_MODEL/10;

        dos.setImage(imagedos);
        face.setImage(global);
        face.setOpacity(0);

        if (carteModel.getNumero() <= 14) {
            face.setViewport(new Rectangle2D(((carteModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), carteModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
            face.setViewport(new Rectangle2D(((carteModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), carteModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        } else {
            face.setViewport(new Rectangle2D(((carteModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), carteModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
            face.setViewport(new Rectangle2D(((carteModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), carteModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        }
        this.getChildren().addAll(dos, face);
    }


    SequentialTransition flip(SequentialTransition sequential) {

        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(HALF_DURATION_FLIP), face);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(Rotate.Y_AXIS);
        rotateOutFront.setFromAngle(90);
        rotateOutFront.setToAngle(0);

        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(HALF_DURATION_FLIP), dos);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(Rotate.Y_AXIS);
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(90);

        rotateInBack.setOnFinished(event ->  {for (int i=0; i<18; i++) {
            face.setOpacity(1);
            dos.setOpacity(0);
        }});

        sequential.getChildren().addAll(rotateInBack, rotateOutFront);
        return sequential;
    }


    public Carte getCarteModel(){ return carteModel; }


    public SequentialTransition TransitionJoueur( double finalx, double finaly, boolean rotate, SequentialTransition sequential){

        if (rotate)
            rotate(sequential, 90f, Duration.millis(ROTATE_DURATION));

        sequential = Transition( finalx, finaly, sequential, Duration.millis(TRANSITION_JOUEUR_DURATION));
        return sequential;
    }

    public SequentialTransition rotate( SequentialTransition sequential, double angle, Duration duration){

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        RotateTransition rotateTransition =
                new RotateTransition(duration, this);
        rotateTransition.setByAngle(angle);
        rotateTransition.setCycleCount(1);

        parallelTransition.getChildren().addAll(rotateTransition);
        sequential.getChildren().add(parallelTransition);
        return sequential;
    }

    public SequentialTransition triGraphique( double finalx, double finaly, SequentialTransition sequential){

        sequential = rotate(sequential, 360f, Duration.millis(1));
        sequential = Transition( finalx, finaly, sequential,Duration.millis(TRANSITION_JOUEUR_DURATION));

        return sequential;
    }

    public SequentialTransition Transition( double finalx, double finaly, SequentialTransition sequential, Duration duration){

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        TranslateTransition translateTransition=
                new TranslateTransition(duration, this);
        translateTransition.setToX(finalx);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        parallelTransition.getChildren().addAll(translateTransition);
        sequential.getChildren().addAll(parallelTransition);
        return sequential;
    }

    public double getX(){return x;}
    public double getY(){return y;}

}
