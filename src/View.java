
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.Dimension;


import java.util.Observable;
import java.util.Observer;

public class View extends Stage implements Observer {

    protected static final int windowSizeW = 400;
    protected static final int windowSizeH = 100;

    static Dimension DIMENSION_VIEW = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_H_VIEW = (int) DIMENSION_VIEW.getHeight();
    static int SCREEN_W_VIEW = (int) DIMENSION_VIEW.getWidth();

    protected Stage Fenetre;
    private Scene scene;

    private Model modele;


    public View(Model modele) {

        this.modele = modele;
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
            CarteView cartePaquet = new CarteView(modele.getPaquet().get(i));
            root.getChildren().add(cartePaquet);
        }
        Fenetre.show();
    }

    public void Poubelle() {
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



    @Override
    public void update (Observable o, Object arg){

    }
}


