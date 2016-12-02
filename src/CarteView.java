import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Created by karnaudeau on 29/11/16.
 */

public class CarteView extends ImageView{

    final static Image global = new Image("file:./testcartes.png");
    final static Image imagedos = new Image("file:./imagedos.png");
    static int SPACE_X_CARDS= 20;
    static int SPACE_Y_CARDS= 20;
    static int CARD_W= 150;
    static int CARD_H= 200;


    private Carte CardModel;



    public CarteView(Carte card) {

        CardModel = card;

        this.setX(CardModel.getPosX());
        this.setY(CardModel.getPosY());

        if (CardModel.getTurned()==true) {
            this.setImage(global);
            if (CardModel.getNumero() <= 14) {
                this.setViewport(new Rectangle2D(((CardModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), 150, 200));
            } else {
                this.setViewport(new Rectangle2D(((CardModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), CardModel.getRankType() * (SPACE_Y_CARDS + CARD_H), 150, 200));
            }
        }

        else
        {
            this.setImage(imagedos);
        }
    }

    public Carte getModel(){ return CardModel; }



}
