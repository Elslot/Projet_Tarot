import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Created by karnaudeau on 29/11/16.
 */

public class CarteView extends ImageView{

    final static Image global = new Image("file:./cartesfinales.png");
    final static Image imagedos = new Image("file:./imagedos.png");
    static int SPACE_X_CARDS= 20;
    static int SPACE_Y_CARDS= 20;
    static int CARD_W= 149;
    static int CARD_H= 200;

    private int z;
    private ImageView face;
    private Carte CardModel;



    public CarteView(Carte card) {

        CardModel = card;

        face = new ImageView();

        face.setTranslateX(this.getX());
        face.setTranslateY(this.getY());
        face.setTranslateZ(z);

        this.setImage(imagedos);
        this.setTranslateX(this.getX());
        this.setTranslateY(this.getY());
        this.setTranslateZ(z);

        this.setX(CardModel.getPosX());
        this.setY(CardModel.getPosY());


        face.setImage(global);
        if (CardModel.getNumero() <= 14) {
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        } else {
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
            face.setViewport(new Rectangle2D(((CardModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        }



    }

    Transition flip() {
        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(3000), face);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(Rotate.Y_AXIS);
        rotateOutFront.setFromAngle(0);
        rotateOutFront.setToAngle(90);
        //
        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(3000), this);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(Rotate.Y_AXIS);
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(90);
        //
   //     return rotateInBack;
        return new SequentialTransition( rotateInBack, rotateOutFront);
    }


    public Carte getModel(){ return CardModel; }


    public SequentialTransition TransitionAutreJoueur(CarteView card, double x, double y, double finalx, double finaly, boolean rotate, SequentialTransition sequential){
        TranslateTransition translateTransition=
                new TranslateTransition(Duration.millis(100), card);
        translateTransition.setFromX(x);
        translateTransition.setToX(finalx);
        translateTransition.setFromY(y);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);


        if (rotate) {

            rotate(card, sequential);
        }
        sequential.getChildren().add(translateTransition);
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

}
