
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
    private Group GcardsFace;
    private boolean end;


    private Model modele;

    private Button bDistribution;
    private Button bGarde;
    private Button bPrise;
    private Button bGardeSansChien;
    private Button bGardeContreChien;
    private Button bTrier;

    public View(Model modele) {

        this.modele = modele;
        end = false;

        cardviews= new ArrayList<CarteView>();
        cartesJoueur = new ArrayList<CarteView>();

        Fenetre = new Stage();
        Fenetre.setTitle("Projet Tarot");

        //      Fenetre.setFullScreen(true);
        //      Fenetre.setFullScreenExitHint("Press ESC to exit FullScreen Mode");
        root = new Group();

        //Inistialisation de chaque bouton
        //"Distribuer" qui sera affiché dés le début
        bDistribution = new Button("Distribuer");
        bDistribution.setPrefSize(150, 50);
        bDistribution.setLayoutX(SCREEN_W_VIEW/2 - bDistribution.getPrefWidth()/2);
        bDistribution.setLayoutY(3*SCREEN_H_VIEW/4 - bDistribution.getPrefHeight()/2);
        bDistribution.setFont((Font.font(20)));

        //"Trier" qui sera invisibles et désactivé avant la distribution
        bTrier = new Button("Trier");
        bTrier.setPrefSize(150, 50);
        bTrier.setLayoutX(SCREEN_W_VIEW/2 - bTrier.getPrefWidth()/2);
        bTrier.setLayoutY(6*SCREEN_H_VIEW/7 - bTrier.getPrefHeight()/2);
        bTrier.setFont((Font.font(20)));
        bTrier.setDisable(true);
        bTrier.setOpacity(0);

        //Les boutons de choix des enchères qui seront invisbles et désactivés au début
        bPrise = new Button("Prise");
        bPrise.setPrefSize(150, 50);
        bPrise.setLayoutX(2*SCREEN_W_VIEW/7 - bPrise.getPrefWidth()/2);
        bPrise.setLayoutY(SCREEN_H_VIEW/12 - bPrise.getPrefHeight()/2);
        bPrise.setFont((Font.font(12)));
        bPrise.setDisable(true);
        bPrise.setOpacity(0);

        bGarde = new Button("Garde");
        bGarde.setPrefSize(150, 50);
        bGarde.setLayoutX(2.75*SCREEN_W_VIEW/7 - bGarde.getPrefWidth()/2);
        bGarde.setLayoutY(SCREEN_H_VIEW/12 - bGarde.getPrefHeight()/2);
        bGarde.setFont((Font.font(12)));
        bGarde.setDisable(true);
        bGarde.setOpacity(0);

        bGardeSansChien = new Button("Garde sans chien");
        bGardeSansChien.setPrefSize(150, 50);
        bGardeSansChien.setLayoutX(3.5*SCREEN_W_VIEW/7 - bGardeSansChien.getPrefWidth()/2);
        bGardeSansChien.setLayoutY(SCREEN_H_VIEW/12 - bGardeSansChien.getPrefHeight()/2);
        bGardeSansChien.setFont((Font.font(12)));
        bGardeSansChien.setDisable(true);
        bGardeSansChien.setOpacity(0);

        bGardeContreChien = new Button("Garde contre chien");
        bGardeContreChien.setPrefSize(150, 50);
        bGardeContreChien.setLayoutX(4.25*SCREEN_W_VIEW/7 - bGardeContreChien.getPrefWidth()/2);
        bGardeContreChien.setLayoutY(SCREEN_H_VIEW/12 - bGardeContreChien.getPrefHeight()/2);
        bGardeContreChien.setFont((Font.font(12)));
        bGardeContreChien.setDisable(true);
        bGardeContreChien.setOpacity(0);

        root.getChildren().add(bDistribution);
        root.getChildren().add(bTrier);
        root.getChildren().add(bPrise);
        root.getChildren().add(bGarde);
        root.getChildren().add(bGardeSansChien);
        root.getChildren().add(bGardeContreChien);

        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        Fenetre.setScene(scene);
        GcardsFace = new Group();
        root.getChildren().add(GcardsFace);

        for (int i = 0; i < 78; i++) {




            CarteView cartePaquetView = new CarteView(modele.getPaquetMelange().get(i));
            cardviews.add(cartePaquetView);
            cardviews.get(i).setXY(cardviews.get(i).getX()-(0.1*i), cardviews.get(i).getY()-(0.1*i));
            cardviews.get(i).setY(cardviews.get(i).getY()-(0.1*i));

            root.getChildren().add(cartePaquetView.getDos());
            GcardsFace.getChildren().add(cartePaquetView.getFace());
            GcardsFace.setOpacity(0.f);



        }
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

                 root.getChildren().remove(cards.get(i));
            }
            if (i % 13 >= 3 && i % 13 <= 5) {
                sequential = cards.get(i).TransitionAutreJoueur(  startx, starty, cards.get(i).getModel().SCREEN_W_MODEL / 2 - cards.get(i).CARD_H, -400, false, sequential);
                root.getChildren().remove(cards.get(i));
            }
            if (i % 13 >= 6 && i % 13 <= 8) {

                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, -400, cards.get(i).getModel().SCREEN_H_MODEL / 2 - cards.get(i).CARD_H, true, sequential);// root.getChildren().remove(cards.get(i));
            }

            if (i%13 == 9)
            {
                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, startx+200+12*(78-i), 200, false, sequential);

            }

            if (i%13 >=10)
            {
                sequential = cards.get(i).TransitionAutreJoueur( startx, starty, startx-100+160*indx, 250+220*indy, false, sequential);
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
            sequential2= cartesJoueur.get(i).flip(sequential2);
        }

        sequential.setOnFinished(event ->  {

            cacherBouton(bTrier, false);
            end = true;
            GcardsFace.setOpacity(1);

        });

        total.getChildren().addAll(sequential, sequential2);
        total.play();


        end = true;


    }



            public void AppelTri() {
                SequentialTransition sequential = new SequentialTransition();
                double startx;
                double starty;
                for (int i = 0; i < 18; i++) {
                    startx = cartesJoueur.get(i).getDos().getTranslateX();
                    starty = cartesJoueur.get(i).getDos().getTranslateY();
                    System.out.println(startx);
                    System.out.println(starty+"\n --------------\n");
                    sequential = cartesJoueur.get(i).triGraphique(startx, starty, startx - 100 + 160*cartesJoueur.get(i).getModel().getPlaceX(), 250 + 220 * cartesJoueur.get(i).getModel().getPlaceY(), sequential);
            }
                sequential.play();
                for (int i= 0; i<18; i++){
                    startx = cartesJoueur.get(i).getDos().getTranslateX();
                    starty = cartesJoueur.get(i).getDos().getTranslateY();
                    System.out.println(startx);
                    System.out.println(starty+"\n --------------\n");
                }
            }

            public void Poubelle() {


        /*
        Path path = new Path();
        path.getElements().add(new MoveTo(startx, starty));

        path.getElements().add(new CubicCurveTo(0, 0, 0, 0, 0, 0));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(card);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
        pathTransition.play();
       // pathTransition.stop(); */

                //A mettre dans une autre fonction
                int k = 0;


                for (int i = 1; i <= 2; i++) {
                    for (int j = 1; j <= 9; j++) {

                        CarteView cartetest = new CarteView(modele.getCarteJoueur().get(k));
                        //   cartetest.setX((j - 1) * (150 + 20) + (150 + 20));
                        //   cartetest.setY((i - 1) * (200 + 20) + (200 + 20));

                        // root.getChildren().add(cartetest);
                        k++;

                    }
                }
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
                Label affichage_petitSec = new Label("Le joueur " + joueur + " a le petit sec, donne annulée.\n Veuillez relancer l'application. Appuyer sur espace pour quitter");
                affichage_petitSec.setTranslateX(SCREEN_W_VIEW / 10);
                affichage_petitSec.setTranslateY(0);
                affichage_petitSec.setFont(Font.font(50));
                affichage_petitSec.setTextAlignment(TextAlignment.CENTER);
                root.getChildren().add(affichage_petitSec);
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

            @Override
            public void update(Observable o, Object arg) {

            }

            public Stage getStage() {
                return Fenetre;
            }
        }




