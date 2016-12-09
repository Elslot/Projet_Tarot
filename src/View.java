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
    static int SCREEN_H_VIEW = (int)DIMENSION_VIEW.getHeight();
    static int SCREEN_W_VIEW = (int)DIMENSION_VIEW.getWidth();

    private double PositionXPaquet;	           // Une valeur pour positionner les cartes du joueur après la distribution et après avoir fait un tri
    private double PositionYPaquet;

    private Model modele;
    protected Stage fenetre;
    private Scene scene;
    private Group root;
    private ArrayList<CarteView> cardviews;    // Contiendra toute les cartes du jeu pour le positionnement du paquet au début, et la distribution 
    private ArrayList<CarteView> cartesJoueur; // Contiendra uniquement des les cartes du joueurs
    private ArrayList<CarteView> cartesChien;  // Contiendra uniquement des les cartes du chien
    private ArrayList<CarteView> cartesEcart;  // Contiendra les cartes qui seront mises dans l'écart avant d'être échangées.

    private Button bDistribution;              // Les différents boutons qui seront nécessaires
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

        fenetre = new Stage();
        fenetre.setTitle("Projet Tarot");
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

		// Le bouton OK sert à valider les cartes à écarter. Il apparait si il y a 6 cartes dans l'écart.
        bOK = new Button();
        createButton(bOK, "OK", 6*SCREEN_W_VIEW/7 - bOK.getPrefWidth()/2, 5*SCREEN_H_VIEW/6 - bOK.getPrefHeight()/2, 20, true);

		// Le bouton quitter qui sert à fermer la fenêtre d'application une fois que la distribution ou l'enchère arrive à sa fin
        bQuitter = new Button();
        createButton(bQuitter, "Quitter", 6*SCREEN_W_VIEW/7 - bQuitter.getPrefWidth()/2, 6*SCREEN_H_VIEW/7 - bQuitter.getPrefHeight()/2, 20, true);

        scene = new Scene(root, SCREEN_W_VIEW, SCREEN_H_VIEW, Color.DARKSEAGREEN);
        fenetre.setScene(scene);

		// Dans ce for on initialise l'ArrayList de cardviews en lui ajoutant les 78 CarteViews qui ont chaqu'une une Carte comme modèle
		// On décale leur affichage de 0.1 par carte 
        for (int i = 0; i < 78; i++) {
            CarteView cartePaquetView = new CarteView(modele.getPaquetMelange().get(i));

            cartePaquetView.setLayoutX(cartePaquetView.getX()-(0.1*i)-(CarteView.CARD_W+CarteView.SPACE_X_CARDS));
            cartePaquetView.setLayoutY(cartePaquetView.getY()-(0.1*i)-(CarteView.CARD_H/4));
            cartePaquetView.setTranslateX(cartePaquetView.getX()-(0.1*i));
            cartePaquetView.setTranslateY(cartePaquetView.getY()-(0.1*i));

            cardviews.add(cartePaquetView);
            root.getChildren().add(cartePaquetView);
        }

		// On initialise avec la position de la carte située en dessous du paquet ( qui n'a pas eu de décalage vers le haut et la gauche )
        PositionXPaquet = cardviews.get(1).getX();
        PositionYPaquet = cardviews.get(1).getY();

        fenetre.show();
    }

	/*
	*  Cette fonction distribue les cartes du paquet au 4 joueurs et au chien en commençant par le joueur.
	*  Les cartes sont envoyées une par une, trois fois à la suite pour chaque joueur à chaque tour de distribution.
	*  Le chien reçoit une carte à chaque tour et uniquement une ( cf : le sujet où il est précisé qu'elles étaient déposées 1 à 1)
	*/
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


        // Selon i, la transition qui est ajouté dans la sequence 'donner' se fera dans une direction particulière, et avec ou non une rotation (selon le booleen).
        for (int i = 77; i >= 0; i--) {

            if (i % 13 >= 0 && i % 13 <= 2)
                donner = cards.get(i).transitionJoueur(SCREEN_W_VIEW + CarteView.CARD_W,SCREEN_H_VIEW / 2 - cards.get(i).CARD_H,
                        true, donner);

            if (i % 13 >= 3 && i % 13 <= 5)
                donner = cards.get(i).transitionJoueur(SCREEN_W_VIEW / 2 - cards.get(i).CARD_H, -400,
                        false, donner);

            if (i % 13 >= 6 && i % 13 <= 8)
                donner = cards.get(i).transitionJoueur(-400,SCREEN_H_VIEW / 2 - cards.get(i).CARD_H,
                        true, donner);

            if (i % 13 == 9){
                donner = cards.get(i).transitionJoueur(PositionXPaquet + CarteView.CARD_W + 12 * (Model.NOMBRE_CARTE_JEU - i),
                        PositionYPaquet + (0.1 * i), false, donner);
                cartesChien.add(cards.get(i));
            }
            if (i%13 >=10)
            {
                donner = cards.get(i).transitionJoueur(PositionXPaquet-CarteView.CARD_W +
                                (CarteView.CARD_W + CarteView.SPACE_X_CARDS)*indx+(0.1*i),
                                PositionYPaquet+ SCREEN_H_VIEW/14+ (CarteView.CARD_H+CarteView.SPACE_X_CARDS)*indy+(0.1*i),
                                false, donner);

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

    // Cette fonction fera le retournement des cartes du chien une fois appelée. C'est à dire après un click sur le bouton 'Prise' ou 'Garde'
    public void affichageChien()
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

	/*
	*  Cette fonction stock dans 'tri' les séquences de transition sur chaque carte, qui doit être déplacer dans la main du joueur, puis lance cette séquence.
	*  On l'appelle après avoir distribué et retourné les cartes du joueurs une première fois. Puis encore une fois après que le joueur ai intégré
	*  les cartes du chiens et écarté des cartes. 
	*/
    public void appelTri(boolean triApresEcart) {

        SequentialTransition tri = new SequentialTransition();
        tri.setCycleCount(1);
        tri.setDelay(Duration.millis(10));

		// Cet int sert à défaire le décalage qui a été fait lors de la superposition des cartes dans le paquet lors de l'initialisation.
		// Sans lui, les cartes sont placées avec un petit décalage dans la main du joueur
        int alignementXY = 0;

		//Deux variables qui simplifient l'écriture de l'appel de la fonction triGraphique
        double finalx;
        double finaly;
        for (int i = 0; i < 18; i++) {
			// on stock à pour chaque carte le finalx correspondant à sa place ( la Xème colonne, et la Yème ligne) dans la main. 
			// on peut ainsi place toute les cartes à la place qui correspondant. Cette place est gérer dans le modele avec la fonction 'tri'
            finalx = cartesJoueur.get(i).getCarteModel().getPlaceX();
            finaly = cartesJoueur.get(i).getCarteModel().getPlaceY();

            tri = cartesJoueur.get(i).triGraphique(PositionXPaquet-150+169*finalx ,PositionYPaquet+100+220*finaly - (0.1*alignementXY), tri);
			// L'alignement correspondant au i qui s'incrémenter de 0 à 78 lors de l'initialisation de la position des cartes.
			// On doit donc l'incrémenter de 1 en 1, mais l'incrémenter de 10 lorsque le nombre correspond à une carte qui n'a pas été donné au joueur
            alignementXY++;
            if (alignementXY %13 <= 10) 
            {alignementXY += 10;}

        }
		// A la fin du tri, on veut afficher les boutons permettant les enchères SAUF si l'écart à déjà été fait. A l'inverse, on affiche le bouton Quitter
		// uniquement après avoir fait l'écart et pas après le premier tri.
        tri.setOnFinished(event -> {
            cacherBoutonEnchere(triApresEcart);
            cacherBouton(bQuitter,!triApresEcart);
        });
        tri.play();
    }

	/*
	*  Cette fonction implémente une transition légère vers le haut OU vers le bas. Elle est appelé pendant la phase d'écart. Si le joueur clique sur une carte
	*  test prend 'true' si la carte n'avait pas déjà été sélectionné. 'false' dans le cas contraire. Ensuite selon test, on monte la carte pour montrer qu'elle 
	*  est selectionnée, ou on la baisse pour montrer qu'elle a été déselectionné.
    */
	public void choixEcart(CarteView carte, boolean test)
    {
        SequentialTransition sequential = new SequentialTransition();
        sequential.setCycleCount(1);
        sequential.setDelay(Duration.millis(10));

        if (test){
            sequential = carte.transition(carte.getTranslateX(), carte.getTranslateY()-50, sequential, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION));
            if (!cartesEcart.contains(carte)) { // On ajoute dans cartesEcart à la CarteViews correspondante à la carte cliquée si elle n'est pas déjà ajoutée.
                cartesEcart.add(carte);
            }
			// On rajoute un léger grossissement des cartes lorsqu'elles sont sélectionnées
            carte.setScaleX(1.1);
            carte.setScaleY(1.1);
        }

        else {
            sequential = carte.transition(carte.getTranslateX(), carte.getTranslateY() + 50, sequential, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION));
            cartesEcart.remove(carte);
            carte.setScaleX(1);
            carte.setScaleY(1);
        }

        sequential.play();
    }

	// Cette fonction permet de créer l'animation montrant le chien prendre la place des cartes du joueurs qui sont dans l'écart et vice-versa.
    public void transitionEcartChien(){
        SequentialTransition swap = new SequentialTransition();
        swap.setCycleCount(1);
        swap.setDelay(Duration.millis(10));

		// Les deux listes ayant même taille, on peut parcourir les deux en même temps et donc faire bouger les cartes deux à deux, chaqu'une
		//aura une correspondance dans l'autre liste.
        for (int i=0; i<6; i++)
        {
			// On redonne une taille normale aux cartes qui étaient dans l'écart.
            cartesEcart.get(i).setScaleX(1);
            cartesEcart.get(i).setScaleY(1);
			// On dit aux cartes du chien, d'aller à la place de la carte correspondante dans l'écart et vice-versa.
            swap = cartesChien.get(i).transition(cartesEcart.get(i).getTranslateX(), cartesEcart.get(i).getTranslateY(), swap, Duration.millis(CarteView.TRANSITION_ECART_DURATION));
            swap = cartesEcart.get(i).transition(cartesChien.get(i).getTranslateX(), cartesChien.get(i).getTranslateY(),swap, Duration.millis(CarteView.TRANSITION_ECART_DURATION));
			// On retire de cartesJoueur les cartes de l'écart au fur et à mesure, en ajoutant en parrallèle les cartes du chien. Ainsi on peut relancer 
			// la fonction tri sur la main du joueur
            cartesJoueur.remove(cartesEcart.get(i));
            cartesJoueur.add(cartesChien.get(i));

        }
		// Une fois l'échange fait, on veut demander au carte venant d'arriver dans la main du joueur, de redescendre puisque la position au début
		// des cartes de l'écart avait été modifié.
        swap.setOnFinished(event -> {this.abaissementChien();});
        swap.play();

    }

	// Cette fonction gère le rabaissement des cartes du chien qui ont était ajouté dans la main après la prise. (cf: Au dessus)
    public void abaissementChien(){
        SequentialTransition abaissement = new SequentialTransition();
        abaissement.setCycleCount(1);
        abaissement.setDelay(Duration.millis(10));

        for (int i=0; i<6; i++)
        {
            abaissement = cartesChien.get(i).transition(cartesChien.get(i).getTranslateX(), cartesChien.get(i).getTranslateY()+CarteView.CARD_H/4,
                    abaissement, Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION));
        }

		// On ré-affiche le bouton trier après l'arrivée du chien dans la main pour permettre de relancer le tri en cliquant.
        abaissement.setOnFinished(event -> {
            cacherBouton(bTrier, false);

        });
        abaissement.play();
    }

	// Cette fonction intervient lorsque l'on clique sur les boutons 'Garde Contre' ou 'Garde Sans' pendant la phase d'enchère,
	// elle fait sortir les cartes du chien de l'écran puisque le joueur ne les veut pas.
    public void departChien(){
        SequentialTransition depart = new SequentialTransition();
        depart.setCycleCount(1);
        depart.setDelay(Duration.millis(10));

        for (CarteView cvChien : cartesChien)
        {
            depart = cvChien.transition(cvChien.getTranslateX(), cvChien.getTranslateY()-400, depart,
                    Duration.millis(CarteView.TRANSITION_JOUEUR_DURATION/2));
        }

        depart.play();
    }

	// La fonction qui change l'opacité du bouton et donc qui sera appelé pour cacher ou non un bouton.
    public void cacherBouton(Button bouton, boolean disable) {
        int opacity = 1;
        if (disable) {
            opacity = 0;
        }

        bouton.setDisable(disable);
        bouton.setOpacity(opacity);
    }

	// Affiche ou cache selon le booleen disable (vrai pour cacher, faux sinon) tout les boutons nécessaires au déroulement de l'enchère.
    public void cacherBoutonEnchere(boolean disable) {
        cacherBouton(bPrise, disable);
        cacherBouton(bGarde, disable);
        cacherBouton(bGardeSansChien, disable);
        cacherBouton(bGardeContreChien, disable);
    }

	// Cette fonction affiche un Label dans la fenêtre, qui indique qu'un joueur possède le Petit sec.
	// La parti s'arrête donc là, et le joueur qui a le Petit sec est annoncé.
    public void petitSec(int joueur) {
        Label affichage_petitSec = new Label("Le joueur " + joueur + " a le petit sec, donne annulée.\n Veuillez relancer l'application. Cliquez sur les cartes pour quitter");
        affichage_petitSec.setTranslateX(SCREEN_W_VIEW / 12);
        affichage_petitSec.setTranslateY(25);
        affichage_petitSec.setFont(Font.font(50));
        affichage_petitSec.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(affichage_petitSec);
    }

	// Une fonction standart pour créer les différents boutons nécessaires.
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
    public ArrayList<CarteView> getCartesJoueur() { 
		return cartesJoueur; 
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
    public Button getBoutonOK() { 
		return bOK; 
	}
    public Button getBoutonQuitter() {
		return bQuitter;
	}
    public Stage getStage() {
		return fenetre;
	}
    @Override
    public void update(Observable o, Object arg) {}


}




