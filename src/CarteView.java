/*
* Nom de classe : CarteView
*
* Description   : Classe implementant la parti graphique des cartes. Ce sont des groupes regroupant à chaque fois
*                 deux ImageView (dos et face). On y définit les constantes graphiques associées aux cartes
*                 ainsi que la durée des transitions qui leur sont affectées.
*/

import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CarteView extends Group {

    final static Image global = new Image("file:./SpriteCarteFace.png"); //Ensemble des sprites pour les faces des cartes
    final static Image imagedos = new Image("file:./SpriteCarteDos.png");// Sprite du dos des cartes
    final static int SPACE_X_CARDS= 20; // Ecart à droite/gauche entre les cartes sur l'écran une fois qu'elles seront dans la main du joueur 
    final static int SPACE_Y_CARDS= 20; // Ecart en haut/bas entre les cartes -------------------------------------------------------------
    final static int CARD_W= 149; // Largeur des cartes
    final static int CARD_H= 200; // Hauteur des cartes
    final static int HALF_DURATION_FLIP = 100;        // La durée des animations qui retourne des cartes
    final static int TRANSITION_JOUEUR_DURATION = 200;// La durée des animations lors des transitions de la distribution
    final static int TRANSITION_ECART_DURATION = 400; // La durée des animations lors des transitions quand échange la position des cartes du chiens et celles de l'écart
    final static int ROTATE_DURATION = 100;           // La durée d'animation d'une rotation

    private double x, y; // Deux variables pour indiquer la position. Elles servent surtout au placement initial du paquet de carte.
    private ImageView face;
    private ImageView dos;
    private Carte carteModel; // Une carte modele qui permettra de savoir quel sprite pour quelle carte.

    public CarteView(Carte card) {

        carteModel = card;
        face = new ImageView();
        dos = new ImageView();

        x=View.SCREEN_W_VIEW/10;
        y=View.SCREEN_H_VIEW/10;

        dos.setImage(imagedos);

        // La face n'est pas affiché au début, son opacité est donc à 0 initialement
        face.setImage(global);
        face.setOpacity(0);

		// Creation d'un rectangle de lecture pour parcourir l'image où se trouve les sprites des faces. Selon le numero et le type de la carte qui sert de modele, le rectangle est déplacé.
        // La condition sert dans le cas des atouts, qui sont alignés sur 2 lignes sur l'image source.
        if (carteModel.getNumero() <= 14) {
            face.setViewport(new Rectangle2D(((carteModel.getNumero()) % 15) * (SPACE_X_CARDS + CARD_W), carteModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        } else {
            face.setViewport(new Rectangle2D(((carteModel.getNumero()) % 14) * (SPACE_X_CARDS + CARD_W), carteModel.getRankType() * (SPACE_Y_CARDS + CARD_H), CARD_W, 200));
        }
        // On ajoute les deux ImageView de notre classe en lui avec une méthode venant de Group.
        this.getChildren().addAll(dos, face);
    }


    // Fonction reprise de l'exemple de rotation fourni.
    SequentialTransition flip(SequentialTransition sequential) {

        final RotateTransition rotateOutFront = new RotateTransition(Duration.millis(HALF_DURATION_FLIP), face);
        rotateOutFront.setInterpolator(Interpolator.LINEAR);
        rotateOutFront.setAxis(Rotate.Y_AXIS);
        rotateOutFront.setFromAngle(90);
        rotateOutFront.setToAngle(0);

        final RotateTransition rotateInBack = new RotateTransition(Duration.millis(HALF_DURATION_FLIP), dos);
        rotateInBack.setInterpolator(Interpolator.LINEAR);
        rotateInBack.setAxis(Rotate.Y_AXIS);
        rotateInBack.setFromAngle(0);
        rotateInBack.setToAngle(90);

        // Une fois la rotation du dos finis, on remet l'opacité de la face à 1 pour l'afficher. A l'inverse
        // on rend le dos transparent.
        rotateInBack.setOnFinished(event ->  {for (int i=0; i<18; i++) {
            face.setOpacity(1);
            dos.setOpacity(0);
        }});

        sequential.getChildren().addAll(rotateInBack, rotateOutFront);
        return sequential;
    }


    // Fonction de transition qui sert pour la distribution essentiellement car elle appellera les transitions à faire
    // selon si la carte doit subir la rotation ou non ( pour les joueurs sur le côté ). finalx et finaly,
    // servent à définir où la carte doit se situer à la fin de sa transition.
    public SequentialTransition transitionJoueur( double finalx, double finaly, boolean rotate, SequentialTransition sequential){

        if (rotate)
            rotate(sequential, 90f, Duration.millis(ROTATE_DURATION));

        sequential = transition( finalx, finaly, sequential, Duration.millis(TRANSITION_JOUEUR_DURATION));
        return sequential;
    }
    // Fonction qui appelera les fonctions de rotation et de transition selon les besoins lors du tri des cartes dans
    // la main du joueur.
    public SequentialTransition triGraphique( double finalx, double finaly, SequentialTransition sequential){

        sequential = rotate(sequential, 360f, Duration.millis(1));
        sequential = transition( finalx, finaly, sequential,Duration.millis(TRANSITION_JOUEUR_DURATION));

        return sequential;
    }

    // Fonction de rotation standard. On peut définir l'angle de rotation voulu et la durée de celle-ci.
    public SequentialTransition rotate( SequentialTransition sequential, double angle, Duration duration){

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        RotateTransition rotateTransition =
                new RotateTransition(duration, this);
        rotateTransition.setByAngle(angle);
        rotateTransition.setCycleCount(1);

        parallelTransition.getChildren().addAll(rotateTransition);
        sequential.getChildren().add(parallelTransition);
        return sequential;
    }

    // Fonction de transition standard. On peut définir la position final voulue et la durée de la transition.
    public SequentialTransition transition( double finalx, double finaly, SequentialTransition sequential, Duration duration){

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.setCycleCount(1);

        TranslateTransition translateTransition=
                new TranslateTransition(duration, this);
        translateTransition.setToX(finalx);
        translateTransition.setToY(finaly);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        parallelTransition.getChildren().addAll(translateTransition);
        sequential.getChildren().addAll(parallelTransition);
        return sequential;
    }

    //Ensemble des get qui seront nécessaire dans la View.
    public Carte getCarteModel(){ return carteModel; }
    public double getX(){return x;}
    public double getY(){return y;}

}
