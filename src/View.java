import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
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

    Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int SCREEN_H = (int)dimension.getHeight();
    int SCREEN_W  = (int)dimension.getWidth();

    protected Stage Fenetre;
    private Scene scene;

    private Controller c;


    public View(Controller control){


        c = control;
        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");

  //      Fenetre.setFullScreen(true);
  //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        Group root = new Group();
        scene = new Scene(root, SCREEN_W, SCREEN_H, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);
        Group cards = new Group();
        root.getChildren().add(cards);
        int k=0;

        for (int j = 1; j<=9; j++)
        {
            for (int i=1; i<=2; i++)
            {
                CarteView cartetest = new CarteView(c.getModel().getCarteJoueur().get(k));
                cartetest.setX((j-1)*(150+20)+(150+20));
                cartetest.setY((i-1)*(200+20)+(200+20));

                root.getChildren().add(cartetest);
                k++;

            }
        }


        Fenetre.show();


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
