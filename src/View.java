import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class View extends Stage implements Observer {

    protected static final int windowSizeW = 400;
    protected static final int windowSizeH = 100;

    protected Stage Fenetre;

    private Controller c;



    public View(Controller control){


        c = control;
        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");
  //      Fenetre.setFullScreen(true);
  //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);
        Fenetre.show();

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
