import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Dimension;
import java.util.*;

public class View implements Observer {

    static Dimension DIMENSION_VIEW = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static double SCREEN_H_VIEW =  DIMENSION_VIEW.getHeight();
    static double SCREEN_W_VIEW =  DIMENSION_VIEW.getWidth();

    protected Stage Fenetre;
    private Scene scene;
    private ArrayList<CarteView> cardviews;
    private ArrayList<CarteView> cartesJoueur;
    private ArrayList<CarteView> cartesChien;

    private ArrayList<CarteView> cartesEcart;

    private Group root;

    private double PositionXPaquet;
    private double PositionYPaquet;

    private Model modele;

    private Button bDistribution;
    private Button bGarde;
    private Button bPrise;
    private Button bGardeSansChien;
    private Button bGardeContreChien;
    private Button bTrier;
    private Button bOK;
    private Button bQuitter;

    public View(Model modele) {

        this.modele = modele;

        cardviews= new ArrayList<>();
        cartesJoueur = new ArrayList<>();
        cartesChien = new ArrayList<>();
        cartesEcart = new ArrayList<>();

        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");

        //      Fenetre.setFullScreen(true);
        //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        root = new Group();


        //Inistialisation de chaque bouton
        //"Distribuer" qui sera affiché dés le début
        bDistribution = new Button();
        createButton(bDistribution, "Distribuer", SCREEN_W_VIEW/2 - bDistribution.getPrefWidth()/2, 3*SCREEN_H_VIEW/4 - bDistribution.getPrefHeight()/2, 20, false);

        //"Trier" qui sera invisibles et désactivé avant la distribution
        bTrier = new Button();
        createButton(bTrier, "Trier", SCREEN_W_VIEW/2 - bDistribution.getPrefWidth()/2, 6*SCREEN_H_VIEW/7 - bTrier.getPrefHeight()/2, 20, true);

        //Les boutons de choix des enchères qui seront invisbles et désactivés au début
        bPrise = new Button();
        createButton(bPrise, "Prise", 2*SCREEN_W_VIEW/7 - bPrise.getPrefWidth()/2, SCREEN_H_VIEW/12 - bPrise.getPrefHeight()/2, 12, true);

        bGarde = new Button();
        createButton(bGarde, "Garde", 2.75*SCREEN_W_VIEW/7 - bGarde.getPrefWidth()/2, SCREEN_H_VIEW/12 - bGarde.getPrefHeight()/2, 12, true);

        bGardeSansChien = new Button();
        createButton(bGardeSansChien, "Garde sans chien", 3.5*SCREEN_W_VIEW/7 - bGardeSansChien.getPrefWidth()/2, SCREEN_H_VIEW/12 - bGardeSansChien.getPrefHeight()/2, 12, true);

        bGardeContreChien = new Button();
        createButton(bGardeContreChien, "Garde contre chien", 4.25*SCREEN_W_VIEW/7 - bGardeContreChien.getPrefWidth()/2, SCREEN_H_VIEW/12 - bGardeContreChien.getPrefHeight()/2, 12, true);

        bOK = new Button();
        createButton(bOK, "OK", 6*SCREEN_W_VIEW/7 - bOK.getPrefWidth()/2, 5*SCREEN_H_VIEW/6 - bOK.getPrefHeight()/2, 20, true);

        bQuitter = new Button();
        createButton(bQuitter, "Quitter", 6*SCREEN_W_VIEW/7 - bQuitter.getPrefWidth()/2, 6*SCREEN_H_VIEW/7 - bQuitter.getPrefHeight()/2, 20, true);

        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);

        for (int i = 0; i < 78; i++) {

            CarteView cartePaquetView = new CarteView(modele.getPaquetMelange().get(i));


            cartePaquetView.setLayoutX(cartePaquetView.getX()-(0.1*i)-(CarteView.CARD_W+CarteView.SPACE_X_CARDS));
            cartePaquetView.setLayoutY(cartePaquetView.getY()-(0.1*i)-(CarteView.CARD_H/4)/*50*/);
            cartePaquetView.setTranslateX(cartePaquetView.getX()-(0.1*i));
            cartePaquetView.setTranslateY(cartePaquetView.getY()-(0.1*i));

            cardviews.add(cartePaquetView);
            root.getChildren().add(cartePaquetView);

        }

        PositionXPaquet = cardviews.get(1).getX();
        PositionYPaquet = cardviews.get(1).getY();
        Fenetre.show();
    }

	// Cette fonction distribue les cartes du paquet au 4 joueurs et au chien en commençant par le joueur.
	// Les cartes sont envoyées une par une, trois fois à la suite pour chaque joueur à chaque tour de distribution.
	// Le chien reçoit une carte à chaque tour et uniquement une ( cf : le sujet où il est précisé qu'elles étaient déposées 1 à 1)
    public void distribution(ArrayList<CarteView> cards) {

        int indx = 1; // Deux indices de placements pour position les cartes du Joueur sur l'écran
        int indy = 1;

        SequentialTransition total = new SequentialTransition();
        total.setCycleCount(1);

        SequentialTransition flipSequence = new SequentialTransition();
        flipSequence.setCycleCount(1);
        flipSequence.setDelay(Duration.millis(10));

        SequentialTransition donner = new SequentialTransition();
        donner.setCycleCount(1);
        donner.setDelay(Duration.millis(10));


        // Selon i, la transition ajouter dans la sequence 'donner' se fera dans une direction particulière, et avec ou non une rotation.
        for (int i = 77; i >= 0; i--) {

            if (i % 13 >= 0 && i % 13 <= 2)
                donner = cards.get(i).TransitionJoueur(cards.get(i).getModel().SCREEN_W_MODEL + cards.get(i).CARD_W, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, donner);

            if (i % 13 >= 3 && i % 13 <= 5)
                donner = cards.get(i).TransitionJoueur(cards.get(i).getModel().SCREEN_W_MODEL / 2 - cards.get(i).CARD_H, -400, false, donner);

            if (i % 13 >= 6 && i % 13 <= 8)
                donner = cards.get(i).TransitionJoueur(-400, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, donner);

            if (i % 13 == 9){
                donner = cards.get(i).TransitionJoueur(PositionXPaquet + 149 + 12 * (78 - i), 100 + (0.1 * i), false, donner);
                cartesChien.add(cards.get(i));
            }
            if (i%13 >=10)
            {
                donner = cards.get(i).TransitionJoueur(PositionXPaquet-150+160*indx+(0.1*i), PositionYPaquet+100+220*indy+(0.1*i), false, donner);
                cartesJoueur.add(cards.get(i));
                indx ++;
                if ((indx==9) &&(indy!=2))
                {
                    indy++;
                    indx=0;
                }
            }
        }
        // On rempli notre sequence flip, avec une suite de sequence retournant chaque carte.
        for( int i = 0; i<18; i++)
            flipSequence = cartesJoueur.get(i).flip(flipSequence);


        // Quand les cartes ont finies de toutes se retourner, on veut afficher le bouton qui lancera le tri si l'on clique dessus.
        // On veut aussi afficher si le petitSec a été trouvé, et si c'est le cas, mettre fin grace au bouton Quitte qui apparaitra.
        flipSequence.setOnFinished(event ->
        {
            if (!modele.getPetitSec())
                cacherBouton(bTrier, false);
            else
            {
                cacherBouton(bQuitter, false);
                petitSec(modele.getJoueurPetitSec());
            }
        });

        // On réunit la distribution et le retournement des cartes dans une SequentialTransition pour les retourner à la suite.
        total.getChildren().addAll( donner, flipSequence);
        total.play();
    }

    // Cette fonction fera le retournement des cartes du chien
    public void AffichageChien()
    {
        SequentialTransition retournerChien = new SequentialTransition();
        retournerChien.setCycleCount(1);
        retournerChien.setDelay(Duration.millis(10));
        for (int i =0; i<6; i++)
        {
            retournerChien = cartesChien.get(i).flip(retournerChien);
        }
        retournerChien.play();
    }

    public void AppelTri(boolean triApresEcart) {

        SequentialTransition tri = new SequentialTransition();
        tri.setCycleCount(1);
        tri.setDelay(Duration.millis(10));

        int alignementXY = 0;

        double finalx;
        double finaly;
        for (int i = 0; i < 18; i++) {
            finalx = cartesJoueur.get(i).getModel().getPlaceX();
            finaly = cartesJoueur.get(i).getModel().getPlaceY();

            tri = cartesJoueur.get(i).triGraphique(PositionXPaquet-150+169*finalx ,PositionYPaquet+100+220*finaly - (0.1*alignementXY), tri);
            alignementXY++;
            if (alignementXY %13 <= 10)
            {alignementXY += 10;}

        }
        tri.setOnFinished(event -> {
            cacherBoutonEnchere(triApresEcart);
        });
        tri.play();
    }

    public void ChoixEcart(CarteView carte, boolean test)
    {
        SequentialTransition sequential = new SequentialTransition();
        sequential.setCycleCount(1);
        sequential.setDelay(Duration.millis(10));

        if (test){
            sequential = carte.Transition(carte.getTranslateX(), carte.getTranslateY()-50, sequential, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION));
            if (!cartesEcart.contains(carte)) {
                cartesEcart.add(carte);
            }
            carte.setScaleX(1.1);
            carte.setScaleY(1.1);
        }

        else {
            sequential = carte.Transition(carte.getTranslateX(), carte.getTranslateY() + 50, sequential, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION));
            cartesEcart.remove(carte);
            carte.setScaleX(1);
            carte.setScaleY(1);
        }

        sequential.play();
    }

    public void TransitionEcartChien(){
        SequentialTransition swap = new SequentialTransition();
        swap.setCycleCount(1);
        swap.setDelay(Duration.millis(10));

        for (int i=0; i<6; i++)
        {
            cartesEcart.get(i).setScaleX(1);
            cartesEcart.get(i).setScaleY(1);

            swap = cartesChien.get(i).Transition(cartesEcart.get(i).getTranslateX(), cartesEcart.get(i).getTranslateY(), swap, Duration.millis(CarteView.TRANSITION_ECART_DURATION));
            swap = cartesEcart.get(i).Transition(cartesChien.get(i).getTranslateX(), cartesChien.get(i).getTranslateY(),swap, Duration.millis(CarteView.TRANSITION_ECART_DURATION));
            cartesJoueur.remove(cartesEcart.get(i));
            cartesJoueur.add(cartesChien.get(i));

        }
        swap.setOnFinished(event -> {this.AbaissementChien();});
        swap.play();

    }

    public void AbaissementChien(){
        SequentialTransition abaissement = new SequentialTransition();
        abaissement.setCycleCount(1);
        abaissement.setDelay(Duration.millis(10));

        for (int i=0; i<6; i++)
        {
            abaissement = cartesChien.get(i).Transition(cartesChien.get(i).getTranslateX(), cartesChien.get(i).getTranslateY()+50, abaissement, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION));
        }

        abaissement.setOnFinished(event -> {
            cacherBouton(bTrier, false);
            cacherBouton(bOK, false);

        });
        abaissement.play();
    }

    public void DepartChien(){
        SequentialTransition depart = new SequentialTransition();
        depart.setCycleCount(1);
        depart.setDelay(Duration.millis(10));

        for (CarteView cvChien : cartesChien)
        {
            depart = cvChien.Transition(cvChien.getTranslateX(), cvChien.getTranslateY()-400, depart, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION/2));
        }

        depart.play();
    }

    public void cacherBouton(Button bouton, boolean disable) {
        int opacity = 1;
        if (disable) {
            opacity = 0;
        }

        bouton.setDisable(disable);
        bouton.setOpacity(opacity);
    }

    public void cacherBoutonEnchere(boolean disable) {
        cacherBouton(bPrise, disable);
        cacherBouton(bGarde, disable);
        cacherBouton(bGardeSansChien, disable);
        cacherBouton(bGardeContreChien, disable);
    }

    public void petitSec(int joueur) {
        Label affichage_petitSec = new Label("Le joueur " + joueur + " a le petit sec, donne annulée.\n Veuillez relancer l'application. Cliquez sur les cartes pour quitter");
        affichage_petitSec.setTranslateX(SCREEN_W_VIEW / 12);
        affichage_petitSec.setTranslateY(25);
        affichage_petitSec.setFont(Font.font(50));
        affichage_petitSec.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(affichage_petitSec);
    }

    public void createButton(Button buttonCreated, String title, double x, double y,int font, boolean disable)
    {
        buttonCreated.setText(title);
        buttonCreated.setPrefSize(150, 50);
        buttonCreated.setLayoutX(x);
        buttonCreated.setLayoutY(y);
        buttonCreated.setFont((Font.font(font)));

        if(disable) {
            buttonCreated.setDisable(disable);
            buttonCreated.setOpacity(0);
        }

        root.getChildren().add(buttonCreated);
    }

    public ArrayList<CarteView> getCardsViews() {
        return cardviews;
    }

    public ArrayList<CarteView> getCartesJoueur() { return cartesJoueur; }

    public Button getBoutonDistribuer() {
        return bDistribution;
    }

    public Button getBoutonTrier() {
        return bTrier;
    }

    public Button getBoutonGarde() {
        return bGarde;
    }

    public Button getBoutonPrise() {
        return bPrise;
    }

    public Button getBoutonGardeSansChien() {
        return bGardeSansChien;
    }

    public Button getBoutonGardeContreChien() {
        return bGardeContreChien;
    }

    public Button getBoutonOK() { return bOK; }

    public Button getBoutonQuitter() { return bQuitter; }

    @Override
    public void update(Observable o, Object arg) {}

    public Stage getStage()
    {
        return Fenetre;
    }
}




