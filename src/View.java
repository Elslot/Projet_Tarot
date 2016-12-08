
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


import java.lang.reflect.Array;
import java.util.*;

public class View implements Observer {

    protected static final int windowSizeW = 400;
    protected static final int windowSizeH = 100;

    static Dimension DIMENSION_VIEW = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_H_VIEW = (int) DIMENSION_VIEW.getHeight();
    static int SCREEN_W_VIEW = (int) DIMENSION_VIEW.getWidth();

    protected Stage Fenetre;
    private Scene scene;
    private ArrayList<CarteView> cardviews;
    private ArrayList<CarteView> cartesJoueur;

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
    private Button bQuitter;

    public View(Model modele) {

        this.modele = modele;

        cardviews= new ArrayList<>();
        cartesJoueur = new ArrayList<>();

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
        createButton(bGardeContreChien, "Garde sans chien", 4.25*SCREEN_W_VIEW/7 - bGardeContreChien.getPrefWidth()/2, SCREEN_H_VIEW/12 - bGardeContreChien.getPrefHeight()/2, 12, true);

        bQuitter = new Button();
        createButton(bQuitter, "Quitter", 6*SCREEN_W_VIEW/7 - bQuitter.getPrefWidth()/2, 6*SCREEN_H_VIEW/7 - bQuitter.getPrefHeight()/2, 20, true);

        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);

        for (int i = 0; i < 78; i++) {

            CarteView cartePaquetView = new CarteView(modele.getPaquetMelange().get(i));


            cartePaquetView.setLayoutX(cartePaquetView.getX()-(0.1*i)-169);
            cartePaquetView.setLayoutY(cartePaquetView.getY()-(0.1*i)-50);
            cartePaquetView.setTranslateX(cartePaquetView.getX()-(0.1*i));
            cartePaquetView.setTranslateY(cartePaquetView.getY()-(0.1*i));
       /*     cardviews.get(i).setXY(cardviews.get(i).getX()-(0.1*i), cardviews.get(i).getY()-(0.1*i));
            cardviews.get(i).setY(cardviews.get(i).getY()-(0.1*i));
*/
            cardviews.add(cartePaquetView);
            root.getChildren().add(cartePaquetView/*.getDos()*/);
           // GcardsFace.getChildren().add(cartePaquetView.getFace());
            //GcardsFace.setOpacity(0.f);



        }

        PositionXPaquet = cardviews.get(1).getX();
        PositionYPaquet = cardviews.get(1).getY();
        Fenetre.show();
    }

    public void distribution(ArrayList<CarteView> cards) {

        int indx = 1;
        int indy = 1;
        SequentialTransition total = new SequentialTransition();
        total.setCycleCount(1);

        SequentialTransition sequential2 = new SequentialTransition();
        sequential2.setCycleCount(1);
        sequential2.setDelay(Duration.millis(10));

        SequentialTransition sequential = new SequentialTransition();
        sequential.setCycleCount(1);
        sequential.setDelay(Duration.millis(10));


        for (int i = 77; i >= 0; i--) {

            double startx = cards.get(i).getX();
            double starty = cards.get(i).getY();

            if (i % 13 >= 0 && i % 13 <= 2) {

                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, cards.get(i).getModel().SCREEN_W_MODEL + cards.get(i).CARD_W, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);


            }
            if (i % 13 >= 3 && i % 13 <= 5) {
                sequential = cards.get(i).TransitionAutreJoueur(  startx, starty, cards.get(i).getModel().SCREEN_W_MODEL / 2 - cards.get(i).CARD_H, -400, false, sequential);

            }
            if (i % 13 >= 6 && i % 13 <= 8) {

                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, -400, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);// root.getChildren().remove(cards.get(i));
            }

            if (i%13 == 9)
            {
                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, startx+149+12*(78-i), 100+(0.1*i), false, sequential);

            }

            if (i%13 >=10)
            {
                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, PositionXPaquet-200+160*indx+(0.1*i), PositionYPaquet+100+220*indy+(0.1*i), false, sequential);
                cartesJoueur.add(cards.get(i));
                indx ++;
                if ((indx==9) &&(indy!=2))
                {
                    indy++;
                    indx=0;
                }
            }
        }


        for( int i = 0; i<18; i++)
        {
            sequential2 = cartesJoueur.get(i).flip(sequential2);
        }



        sequential2.setOnFinished(event ->  {

            if (!modele.getPetitSec()) {
                cacherBouton(bTrier, false);
            }
            else
            {
                cacherBouton(bQuitter, false);
                petitSec(modele.getJoueurPetitSec());
            }

        });



        total.getChildren().addAll(sequential, sequential2);
        total.play();


    }



            public void AppelTri() {

                SequentialTransition sequential = new SequentialTransition();
                sequential.setCycleCount(1);
                sequential.setDelay(Duration.millis(10));

                int alignementXY = 0;

                double startx;
                double starty;
                double finalx;
                double finaly;
                for (int i = 0; i < 18; i++) {
                    finalx = cartesJoueur.get(i).getModel().getPlaceX();
                    finaly = cartesJoueur.get(i).getModel().getPlaceY();

                    System.out.println(i);
                    System.out.println( cartesJoueur.get(i).getTranslateX() + " / " +  cartesJoueur.get(i).getTranslateY());
                    System.out.println( cartesJoueur.get(i).getModel().getPlaceX());
                    System.out.println(cartesJoueur.get(i).getModel().getPlaceY()+ "---------\n" );
                    sequential = cartesJoueur.get(i).triGraphique(PositionXPaquet-200+169*finalx -( 0.1*alignementXY),PositionYPaquet+100+220*finaly - (0.1*alignementXY), sequential);
                    alignementXY++;
                    if (alignementXY %13 <= 10)
                    {alignementXY += 10;}

            }
            sequential.play();
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

            public Button getBoutonQuitter() { return bQuitter; }

            @Override
            public void update(Observable o, Object arg) {

            }

            public Group getRoot() {
                return root;
            }

            public Stage getStage()
            {
                return Fenetre;
            }
        }




