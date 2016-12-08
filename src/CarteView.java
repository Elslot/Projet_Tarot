import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by karnaudeau on 29/11/16.
 */

public class CarteView extends Group {

    final static Image global = new Image("file:./cartesfinales2.png");
    final static Image imagedos = new Image("file:./imagedos.png");
    static int SPACE_X_CARDS= 20;
    static int SPACE_Y_CARDS= 20;
    static int CARD_W= 149;
    static int CARD_H= 200;

    private double x, y;
    private ImageView face;
    private ImageView dos;
    private Carte CardModel;



    public CarteView(Carte card) {

        CardModel = card;

        face = new ImageView();
        dos = new ImageView();

        x=CardModel.SCREEN_W_MODEL/10;
        y=CardModel.SCREEN_H_MODEL/10;


  /*      dos.setTranslateX(x);
        dos.setTranslateY(y);

        face.setTranslateX(x);
        face.setTranslateY(y); */
    //    face.setTranslateZ(z);

        dos.setImage(imagedos);

    //    dos.setTranslateZ(z);



        face.setImage(global);
 //       face.setVisible(false);
        face.setOpacity(0);

        if (CardModel.getNumero() <= 14) {
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        } else {
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        }

        this.getChildren().addAll(dos, face);


    }

    Collection<Node> getNodes(){
        ArrayList<Node> al = new ArrayList<>();
        al.add(face);
        al.add(dos);
        return al;
    }


    SequentialTransition flip(SequentialTransition sequential) {


        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(100), face);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(Rotate.Y_AXIS);
        rotateOutFront.setFromAngle(90);
        rotateOutFront.setToAngle(0);
        //


        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(100), dos);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(Rotate.Y_AXIS);
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(90);
        //
   //     return rotateInBack;

        rotateInBack.setOnFinished(event ->  {for (int i=0; i<18; i++) {
            face.setOpacity(1);
            dos.setOpacity(0);
        }});

        SequentialTransition sequential2 = new SequentialTransition();
        sequential2.getChildren().addAll(rotateInBack, rotateOutFront);
        sequential.getChildren().add(sequential2);
        return sequential;
    }


    public Carte getModel(){ return CardModel; }


    public SequentialTransition TransitionAutreJoueur( double x, double y, double finalx, double finaly, boolean rotate, SequentialTransition sequential){

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        TranslateTransition translateTransition=
                new TranslateTransition(Duration.millis(1), this);
        translateTransition.setFromX(x);
        translateTransition.setToX(finalx);
        translateTransition.setFromY(y);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);


        parallelTransition.getChildren().add(translateTransition);


        if (rotate) {

            rotate( sequential);
        }
        sequential.getChildren().add(parallelTransition);



        return sequential;
    }

    public SequentialTransition rotate( SequentialTransition sequential){

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(50), this);
        rotateTransition.setByAngle(90f);
        rotateTransition.setCycleCount(1);

        parallelTransition.getChildren().addAll(rotateTransition);
        sequential.getChildren().add(parallelTransition);

        return sequential;
    }

    public SequentialTransition triGraphique( double x, double y, double finalx, double finaly, SequentialTransition sequential){


        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        TranslateTransition translateTransition=
                new TranslateTransition(Duration.millis(100), dos);
        translateTransition.setFromX(x);
        translateTransition.setToX(finalx);
        translateTransition.setFromY(y);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        TranslateTransition translateTransition2=
                new TranslateTransition(Duration.millis(100), face);
        translateTransition.setFromX(x);
        translateTransition.setToX(finalx);
        translateTransition.setFromY(y);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        parallelTransition.getChildren().addAll(translateTransition, translateTransition2);
        parallelTransition.play();

        sequential.getChildren().addAll(parallelTransition);

        return sequential;
    }


    public void positionFace()
    {
        face.setTranslateX(dos.getTranslateX());
        face.setTranslateY(dos.getTranslateY());

    }

    public ImageView getDos()
    {
        return dos;
    }

    public ImageView getFace()
    {
        return face;
    }

    public double getX(){return x;}
    public void setXY(double new_x, double new_y){
        dos.setTranslateX(new_x);
        dos.setTranslateY(new_y);
        face.setTranslateX(new_x);
        face.setTranslateY(new_y);
    }
    public double getY(){return y;}
    public void setY(double new_y){y= new_y;}

}
