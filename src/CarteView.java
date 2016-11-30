import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by karnaudeau on 29/11/16.
 */

public class CarteView {

    final static ImageView global = new ImageView("imagesrc/sprite");
    static int SPACE_X_CARDS= 20;
    static int SPACE_Y_CARDS= 20;
    private Carte modele;
    private ImageView sprite;


    public CarteView(int ){

        global.setViewport(new Rectangle2D((ESPACE_ENTRE_CARTES*modele.getNumero()), 100, 50, 200));
    }
}
