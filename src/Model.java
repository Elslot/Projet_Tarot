import java.util.ArrayList;

public class Model {
	ArrayList<Carte> paquet;
	ArrayList<Carte> chien;
	ArrayList<Carte> joueur;
	
	Model()
	{
		paquet = new ArrayList<>();
		//Insertion de toutes les cartes
		for(int i = 0; i < 4; i++)
		{
			for(int j = 1; j <= 14; j++)
				paquet.add(new Carte(TypeCarte.CARREAU, j));
			for(int j = 1; j <= 14; j++)
				paquet.add(new Carte(TypeCarte.COEUR, j));
			for(int j = 1; j <= 14; j++)
				paquet.add(new Carte(TypeCarte.PIQUE, j));
			for(int j = 1; j <= 14; j++)
				paquet.add(new Carte(TypeCarte.TREFLE, j));
		}
		
		chien = new ArrayList<>();
		joueur = new ArrayList<>();
	}
}
