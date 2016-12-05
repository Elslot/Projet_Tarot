import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by karnaudeau on 23/11/16.
 */
public class Main extends Application{

    static public void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage viewTest) throws Exception {

        Model modeltest = new Model();
        View viewtest = new View(modeltest);
        Controller control = new Controller(modeltest, viewtest);

       control.lancerDistribution();
    }
}
