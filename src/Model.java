import java.lang.reflect.Type;
import java.util.*;

public class Model extends Observable{

    final static int NOMBRE_CARTE_JEU = 78;

	private ArrayList<Carte> paquet;
    private ArrayList<Carte> paquetMelange;
	private ArrayList<Carte> chien;
	private ArrayList<Carte> joueur;
    private ArrayList<Carte> autreJoueur1;
    private ArrayList<Carte> autreJoueur2;
    private ArrayList<Carte> autreJoueur3;
    private ArrayList<Carte> ecart;

    private int joueurPetitSec; //Détermine quel joueur à le petit sec
    private boolean petitSec; //Détermine si le petit sec a été trouvé dans un des paquets des joueurs

    Model()
    {
		paquet = new ArrayList<>();
		//Insertion de toutes les cartes
		//Insertion des cartes de couleurs
        for (int j = 1; j <= 14; j++)
        {
            paquet.add(new Carte(TypeCarte.CARREAU, j));
            paquet.add(new Carte(TypeCarte.COEUR, j));
            paquet.add(new Carte(TypeCarte.PIQUE, j));
            paquet.add(new Carte(TypeCarte.TREFLE, j));
        }
		for(int i = 1; i <= 21; i++)    //Insertion des atouts
        {
            paquet.add(new Carte(TypeCarte.ATOUT, i));
        }
        paquet.add(new Carte(TypeCarte.EXCUSE, 1)); //Insertion de l'excuse

        paquetMelange = new ArrayList<>();
		chien = new ArrayList<>();
		joueur = new ArrayList<>();
        autreJoueur1 = new ArrayList<>();
        autreJoueur2 = new ArrayList<>();
        autreJoueur3 = new ArrayList<>();
        ecart = new ArrayList<>();

        joueurPetitSec = -1;
        petitSec = false;
	}

	/* Cette fonction réalise toute la distribution du modèle, en insérant simplement les cartes du paquet melangé tour à tour aux joueurs et au chien. */
	public void distribution()
    {
    	int indiceDistrib = 0; //Indice qui va permettre de distribuer 3 cartes aux joueurs à chaque tour et une carte au chien. 
		//Il délimite et gère un tour de distribution.
        for(int i = 0; i <= 77; i++) //A chaque tour de distribution
        {
            if (indiceDistrib >= 13) { 
                indiceDistrib = 0;//On met l'indice à 0 si on a distribuer 13 cartes, ça veut dire que l'on recommence un tour.
            }
            if (indiceDistrib < 3) { //Cela veut dire que l'on distribue au joueur 1 en premier
                autreJoueur1.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib >= 3 && indiceDistrib < 6) {
                autreJoueur2.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib >= 6 && indiceDistrib < 9) {
                autreJoueur3.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib == 9) { //On distribue une carte au chien
                chien.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib >= 10 && indiceDistrib < 13) {
                joueur.add(paquetMelange.get(i));
                indiceDistrib++;
            }
        }
    }

	/* Fonction qui melange le paquet initiale où se trouve toutes les cartes. Elle prend une carte aléatoire dans le paquet et l'insère dans paquetMélangé */
    public void melanger()
    {
        int iAlea;
        int iMax = 78;
        Random rand  = new Random();
        for(int i = 1; i <= 78; i++) {
            iAlea = rand.nextInt(iMax); //On prend un nombre aléatoire
            paquetMelange.add(paquet.get(iAlea)); //On ajoute la carte correspondante à l'id dans paquetMélangé.
            paquet.remove(iAlea); //Et on enlève la carte du paquet initiale.
            iMax--;
        }
    }

	/* Cette fonction prend en paramètre un array de Cartes et le tri. 
	 * On tri d'abord le paquet par le numéro des cartes pour faciliter le tri par couleur */
    public void trier(ArrayList<Carte> cartes)
    {
        ArrayList<Carte> piques = new ArrayList<>();
        ArrayList<Carte> coeurs = new ArrayList<>();
        ArrayList<Carte> atouts = new ArrayList<>();
        ArrayList<Carte> carreaux = new ArrayList<>();
        ArrayList<Carte> trefles = new ArrayList<>();

		//Ces variables permetteront d'insérer l'excuse à la fin si on le détecte dans le paquet mis en paramètre
        boolean excuse = false;
        int iExcuse = 0;

        //Tri en premier les cartes par leur numéro
        cartes.sort(new Comparator<Carte>() {
            @Override
            public int compare(Carte carte1, Carte carte2) {    //Redéfinissions du comparateur pour pouvoir bien trier les cartes
                return Integer.compare(carte1.getNumero(), carte2.getNumero()); //en fonction de leur numéro
            }
        });

		//Tri par couleur
        for(int i = 0; i < cartes.size(); i++)
        {
			//Dès qu'on détecte une couleur, on l'ajoute dans son array corrspondant.
            if(cartes.get(i).getType() == TypeCarte.PIQUE)
            {
                piques.add(cartes.get(i));
            }
            else if(cartes.get(i).getType() == TypeCarte.COEUR)
            {
                coeurs.add(cartes.get(i));
            }
            else if(cartes.get(i).getType() == TypeCarte.ATOUT)
            {
                atouts.add(cartes.get(i));
            }
            else if(cartes.get(i).getType() == TypeCarte.CARREAU)
            {
                carreaux.add(cartes.get(i));
            }
            else if(cartes.get(i).getType() == TypeCarte.TREFLE)
            {
                trefles.add(cartes.get(i));
            }
			//Sauf pour l'excuse, où l'on indique juste qu'il se trouve dans le paquet à trier et on note son indice
            else
            {
                excuse = true;
                iExcuse = i;
            }
        }

		//Si on a trouvé l'excuse, on l'insère avec les trèfles à la fin.
		//Comme les trèfles sont insérer en dernier, l'excuse se trouvera à la fin, après les trèfles
        if(excuse)
        {
            trefles.add(cartes.get(iExcuse));
        }

		//On nettoie en entier le paquet passé en parmètre pour procéder au tri
        cartes.clear();

		//On ajoute dans l'ordre les array dans le paquet.
        cartes.addAll(piques);
        cartes.addAll(coeurs);
        cartes.addAll(atouts);
        cartes.addAll(carreaux);
        cartes.addAll(trefles);

		//Ces indices correspondent au placement des cartes graphiques dans l'écran
		//Ce sont les places (x = colonne; y = ligne) que devront prendre les cartes dans la main du joueur dans la view.
        int ind_x = 1;
        int ind_y = 1;
        for (int i=0; i<cartes.size();i++)
        {
            cartes.get(i).setPlaceX(ind_x);
            cartes.get(i).setPlaceY(ind_y);
			
			//On met 8 cartes sur la première ligne 
			//On change de ligne
			//On met le reste des cartes sur la deuxième, donc 10 cartes.
            if((ind_x >= 8) && (ind_y!=2))
            {
                ind_y++; //Changement de ligne
                ind_x = 0;
            }
            else
            {
                ind_x++;
            }
        }
    }
	
	/* Cette fonction recherche le petit sec dans le paquet passé en paramètre.
	 * Il parcours tout le paquet en repèrant si il y a des atouts, si il y a le petit, et si il y a l'excuse
	 * Après le parcours, il teste si le petit sec est présent, et retourne vrai si oui, faux sinon */
    public boolean aPetitSec(ArrayList<Carte> cartes)
    {
        int i = 0;
        boolean fini = false;
        boolean aExcuse = false; //Détermine si l'excuse se trouve dans le paquet
        boolean aPetit = false; //Détermine si le petit (le 1) est présent
        boolean aAtout = false; //Détermine si il y a un atout autre que le petit 

        while(!fini && i < cartes.size()) {
            if (cartes.get(i).getType() == TypeCarte.EXCUSE)
            {
                aExcuse = true; //Il y a une excuse
				fini = true; //On termine la boucle car la condition pour qu'on ai le petit sec est qu'il ne doit pas y avoir d'excuse.
            }
            else if(cartes.get(i).getType() == TypeCarte.ATOUT && cartes.get(i).getNumero() == 1)
            {
                aPetit = true; //Il y a le petit, il est nécessaire pour le petit sec.
            }
            else if(cartes.get(i).getType() == TypeCarte.ATOUT)
            {
                aAtout = true; //Il y a un atout
                fini = true; //On termine la boucle car la condition pour qu'on ai le petit sec est qu'il ne doit pas y avoir d'atout excepté le petit.
            }
            i++;
        }

        if(!aAtout && !aExcuse && aPetit)
        {
            return true;
        }
        return false;
    }

	/* Cette fonction appelle simplement la fonction aPetitSec pour chaque joueur, et détermine lequel le possède
	 * Elle retourne vrai si un des joueurs à le petit sec, non sinon.*/
    public boolean trouverPetitSec()
    {
        if(aPetitSec(joueur))
        {
            joueurPetitSec = 0;
            petitSec = true;
        }
        else if(aPetitSec(autreJoueur1))
        {
            joueurPetitSec = 1;
            petitSec = true;
        }
        else if(aPetitSec(autreJoueur2))
        {
            joueurPetitSec = 2;
            petitSec = true;
        }
        else if(aPetitSec(autreJoueur3))
        {
            joueurPetitSec = 3;
            petitSec = true;
        }

        return petitSec;
    }

	/* Fonction qui teste si la carte passée en paramètre (qui doit être forcément une carte du joueur) est licite pour un écart
	 * Retourne vrai si c'est le cas, faux sinon*/
    public boolean licite(Carte depot)
    {
        boolean atoutAutorise = true; //Par défaut les atouts sont autorisés, mais la variable est vérifiée lors du parcours du paquet du joueur
        for(int i = 0; i < joueur.size(); i++) //On parcours le paquet pour voir si déposer un atout dans un écart est autorisé ou non
        {
            if(joueur.get(i).getType() != TypeCarte.ATOUT && joueur.get(i).getAjouteEcart() == false) //Si il y a dans le paquet une carte autre qu'un atout et qu'elle n'est pas encore dans l'écart,
			//cela veut dire que l'on peut la déposer dans l'écart, et comme les atouts ne doivent être écarté seulement si c'est indispensable.
            {
                atoutAutorise = false; //On n'autorise pas l'écart de l'atout
            }
        }
        if((depot.getType() != TypeCarte.ATOUT && depot.getType() != TypeCarte.EXCUSE && depot.getNumero() == 14) || //si le dépôt que l'on veut faire est un Roi
                (depot.getType() == TypeCarte.ATOUT && (depot.getNumero() == 21 || depot.getNumero() == 1)) || //Ou un bout
                (depot.getType() == TypeCarte.ATOUT && atoutAutorise == false)) //Ou si c'est un atout, et qu'ils ne sont pas autorisés...
        {
            return false; //Le dépôt n'est pas licite car ces trois conditions sont interdites, on retourne faux.
        }
        else
        {
            return true; //Sinon vrai.
        }
    }

	/* Cette fonction réalise l'écart dans le modèle, lorsqu'il est confimé */
    public void Ecart()
    {
        for(int i = 0; i < ecart.size(); i++)
        {
            joueur.remove(ecart.get(i)); //On enlève toute les cartes écartées du paquet du joueur
        }
        joueur.addAll(chien); //Et on rajoute tout le chien (on retri plus tard le paquet du joueur.
    }

    public ArrayList<Carte> getPaquetMelange() {
        return paquetMelange;
    }

    public ArrayList<Carte> getCarteJoueur ()
	{
		return joueur;
	}

	public int getJoueurPetitSec () { return joueurPetitSec; }

	public boolean getPetitSec () { return petitSec; }

	public ArrayList<Carte> getEcart () { return ecart; }
}
