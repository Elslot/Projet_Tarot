import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class View extends Stage implements Observer {

    protected static final int windowSizeW = 400;
    protected static final int windowSizeH = 100;

    protected Stage Fenetre;

    protected Controller c;

    public View(){

        Fenetre = new Stage();
        Fenetre.setFullScreen(true);
        Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
