import java.util.ArrayList;
import java.util.Stack;

public class Model {
	private Stack<Carte> paquet;
	private ArrayList<Carte> chien;
	private ArrayList<Carte> joueur;
    private ArrayList<Carte> autreJoueur1;
    private ArrayList<Carte> autreJoueur2;
    private ArrayList<Carte> autreJoueur3;

    Model()
	{
		paquet = new Stack<>();
		//Insertion de toutes les cartes
		for(int i = 0; i < 4; i++) {    //Insertion des cartes de couleurs
			for (int j = 1; j <= 14; j++)
			{
				paquet.add(new Carte(TypeCarte.CARREAU, j));
				paquet.add(new Carte(TypeCarte.COEUR, j);
                paquet.add(new Carte(TypeCarte.PIQUE, j));
				paquet.add(new Carte(TypeCarte.TREFLE, j));
			}
		}
		for(int i = 1; i <= 21; i++)    //insertion des atouts
        {
            paquet.add(new Carte(TypeCarte.ATOUT, i));
        }
        paquet.add(new Carte(TypeCarte.EXCUSE, 0)); //Insertion de l'excuse
		
		chien = new ArrayList<>();
		joueur = new ArrayList<>();
	}

	public void distribution()
    {
        for(int i = 1; i <= 78; i++)
        {

        }
    }
}
