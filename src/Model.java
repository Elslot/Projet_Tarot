import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Model {
	private ArrayList<Carte> paquet;
    private Stack<Carte> paquetMelange;
	private ArrayList<Carte> chien;
	private ArrayList<Carte> joueur;
    private ArrayList<Carte> autreJoueur1;
    private ArrayList<Carte> autreJoueur2;
    private ArrayList<Carte> autreJoueur3;

    Model()
	{
		paquet = new ArrayList<>();
		//Insertion de toutes les cartes
		//Insertion des cartes de couleurs
        for (int j = 1; j <= 14; j++)
        {
            paquet.add(new Carte(TypeCarte.CARREAU, j));
            paquet.add(new Carte(TypeCarte.COEUR, j);
            paquet.add(new Carte(TypeCarte.PIQUE, j));
            paquet.add(new Carte(TypeCarte.TREFLE, j));
        }
		for(int i = 1; i <= 21; i++)    //Insertion des atouts
        {
            paquet.add(new Carte(TypeCarte.ATOUT, i));
        }
        paquet.add(new Carte(TypeCarte.EXCUSE, 0)); //Insertion de l'excuse
		
		chien = new ArrayList<>();
		joueur = new ArrayList<>();
	}

	public void distribution()
    {
    	int indiceDistrib = 0;
        for(int i = 1; i <= 78; i++)
        {
			if(indiceDistrib < 3)
            {
                autreJoueur1.add(paquetMelange.peek());
                indiceDistrib++;
            }
            else if(indiceDistrib >= 3 && indiceDistrib < 6)
            {
                autreJoueur2.add(paquetMelange.peek());
                indiceDistrib++;
            }
            else if(indiceDistrib >= 6 && indiceDistrib < 9)
            {
                autreJoueur3.add(paquetMelange.peek());
                indiceDistrib++;
            }
            else if(indiceDistrib == 9)
            {
                chien.add(paquetMelange.peek());
                indiceDistrib++;
            }
            else if(indiceDistrib >= 10 && indiceDistrib < 13)
            {
                joueur.add(paquetMelange.peek());
                indiceDistrib++;
            }
            else
            {
                indiceDistrib = 0;
            }
            paquetMelange.pop();
        }
    }

    public void melange()
    {
        int iAlea;
        int iMax = 78;
        Random rand  = new Random();
        for(int i = 1; i <= 78; i++)
        {
            iAlea = rand.nextInt(iMax) + 1;
            paquetMelange.push(paquet.get(iAlea));
            paquet.remove(iAlea);
            iMax--;
        }
    }
}
