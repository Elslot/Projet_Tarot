import java.lang.reflect.Type;
import java.util.*;

public class Model {
	private ArrayList<Carte> paquet;
    private Stack<Carte> paquetMelange;
	private ArrayList<Carte> chien;
	private ArrayList<Carte> joueur;
    private ArrayList<Carte> autreJoueur1;
    private ArrayList<Carte> autreJoueur2;
    private ArrayList<Carte> autreJoueur3;

    boolean distributionFini;

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
        paquet.add(new Carte(TypeCarte.EXCUSE, 0)); //Insertion de l'excuse

        paquetMelange = new Stack<>();
		chien = new ArrayList<>();
		joueur = new ArrayList<>();
        autreJoueur1 = new ArrayList<>();
        autreJoueur2 = new ArrayList<>();
        autreJoueur3 = new ArrayList<>();

        distributionFini = false;
	}

	public void distribution()
    {
    	int indiceDistrib = 0;
        for(int i = 1; i <= 78; i++)
        {
            if(indiceDistrib >= 13)
            {
                indiceDistrib = 0;
            }
			if(indiceDistrib < 3)
            {
                autreJoueur1.add(paquetMelange.pop());
                indiceDistrib++;
            }
            else if(indiceDistrib >= 3 && indiceDistrib < 6)
            {
                autreJoueur2.add(paquetMelange.pop());
                indiceDistrib++;
            }
            else if(indiceDistrib >= 6 && indiceDistrib < 9)
            {
                autreJoueur3.add(paquetMelange.pop());
                indiceDistrib++;
            }
            else if(indiceDistrib == 9)
            {
                chien.add(paquetMelange.pop());
                indiceDistrib++;
            }
            else if(indiceDistrib >= 10 && indiceDistrib < 13)
            {
                joueur.add(paquetMelange.pop());
                indiceDistrib++;
            }
        }
    }

    public void melanger()
    {
        int iAlea;
        int iMax = 78;
        Random rand  = new Random();
        for(int i = 1; i <= 78; i++) {
            iAlea = rand.nextInt(iMax);
            paquetMelange.push(paquet.get(iAlea));
            paquet.remove(iAlea);
            iMax--;
        }
    }

    public void trier(ArrayList<Carte> cartes)
    {
        ArrayList<Carte> piques = new ArrayList<>();
        ArrayList<Carte> coeurs = new ArrayList<>();
        ArrayList<Carte> atouts = new ArrayList<>();
        ArrayList<Carte> carreaux = new ArrayList<>();
        ArrayList<Carte> trefles = new ArrayList<>();

        boolean excuse = false;
        int iExcuse = 0;

        //Tri en premier les cartes par leur numéro
        cartes.sort(new Comparator<Carte>() {
            @Override
            public int compare(Carte carte1, Carte carte2) {    //Redéfinissions du comparateur pour pouvoir bien trier les cartes
                return Integer.compare(carte1.getNumero(), carte2.getNumero()); //en fonction de leur numéro
            }
        });

        for(int i = 0; i < cartes.size(); i++)
        {
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
            else
            {
                excuse = true;
                iExcuse = i;
            }
        }

        if(excuse)
        {
            trefles.add(cartes.get(iExcuse));
        }

        cartes.clear();

        cartes.addAll(piques);
        cartes.addAll(coeurs);
        cartes.addAll(atouts);
        cartes.addAll(carreaux);
        cartes.addAll(trefles);
    }

    public boolean aPetitSec(ArrayList<Carte> cartes)
    {
        int i = 0;
        boolean fini = false;
        boolean aExcuse = false;
        boolean aPetit = false;
        boolean aAtout = false;

        while(!fini && i < cartes.size()) {
            if(cartes.get(i).getType() == TypeCarte.CARREAU || cartes.get(i).getType() == TypeCarte.TREFLE)
            {
                fini = true;
            }
            else if (cartes.get(i).getType() == TypeCarte.EXCUSE)
            {
                aExcuse = true;
            }
            else if(cartes.get(i).getType() == TypeCarte.ATOUT && cartes.get(i).getNumero() == 1)
            {
                aPetit = true;
            }
            else if(cartes.get(i).getType() == TypeCarte.ATOUT)
            {
                aAtout = true;
                fini = true;
            }
            i++;
        }

        if(!aAtout && !aExcuse && aPetit)
        {
            return true;
        }
        return false;
    }

    public void trouverPetitSec()
    {
        if(aPetitSec(joueur))
        {

        }
        else if(aPetitSec(autreJoueur1))
        {

        }
        else if(aPetitSec(autreJoueur2))
        {

        }
        else if(aPetitSec(autreJoueur3))
        {

        }
    }



    public ArrayList<Carte> getCarteJoueur ()
	{
		return joueur;
	}
}
