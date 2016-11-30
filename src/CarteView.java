import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by karnaudeau on 29/11/16.
 */

public class CarteView {

    final static ImageView global = new ImageView("imagesrc/sprite");
    static int SPACE_X_CARDS= 20;
    static int SPACE_Y_CARDS= 20;
    static int CARD_W= 20;
    static int CARD_H= 20;
    private Carte modele;
    private ImageView sprite;


    public CarteView(){

        global.setViewport(new Rectangle2D(modele.getNumero()*(SPACE_X_CARDS+CARD_W), 100, 50, 200));
    }
}
