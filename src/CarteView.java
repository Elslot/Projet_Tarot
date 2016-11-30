import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * Created by karnaudeau on 29/11/16.
 */

public class CarteView extends Rectangle {

    final static ImageView global = new ImageView("file:./testcartes.png");
    static int SPACE_X_CARDS= 20;
    static int SPACE_Y_CARDS= 20;
    static int CARD_W= 150;
    static int CARD_H= 200;
    private Carte modele;
    private ImageView sprite;


    public CarteView(int pos_x, int pos_y, Carte mod){

        modele = mod;
        modele.setX(pos_x);
        modele.setY(pos_y);

        global.setViewport(new Rectangle2D(modele.getNumero()*(SPACE_X_CARDS+CARD_W),modele.getIDType()*(SPACE_Y_CARDS+CARD_H), 50, 200));
    }
}
