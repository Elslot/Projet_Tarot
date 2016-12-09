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

        /* Nous avons choisi le modèle MVC, mais où le controleur contient modèle et vue, c'est lui qui gère la maj
         * de ces deux élèments en fonction des évènements et de l'ordre pour que tout se synchronise, et la vue contient modèle, pour
         * mettre à jour le modèle en fonction de l'affichage. Tout cela pour faciliter notre programmation */

        Model modele = new Model();
        modele.melanger(); //On mélange au tout début du jeu.

        View vue = new View(modele);
        Controller control = new Controller(modele, vue);

        control.lancerDistribution(); //On lance en premier toute la distribution
        control.enchere(); //Puis les enchère

    }
}
