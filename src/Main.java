import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by karnaudeau on 23/11/16.
 */
public class Main extends Application{

    static public void main(String[] args) {

        Model modeltest = new Model();
        modeltest.distribution();
        for (Carte c : modeltest.getCarteJoueur())
        {
            System.out.println(c.getNumero() + " / " + c.getType());
        }


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
