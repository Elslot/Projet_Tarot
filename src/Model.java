import java.lang.reflect.Type;
import java.util.*;

public class Model extends Observable{
	private ArrayList<Carte> paquet;
    private ArrayList<Carte> paquetMelange;
	private ArrayList<Carte> chien;
	private ArrayList<Carte> joueur;
    private ArrayList<Carte> autreJoueur1;
    private ArrayList<Carte> autreJoueur2;
    private ArrayList<Carte> autreJoueur3;
    private ArrayList<Carte> ecart;

    private int joueurPetitSec;
    private boolean petitSec;

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

	public void distribution()
    {
    	int indiceDistrib = 0;
        for(int i = 0; i <= 77; i++)
        {
            if (indiceDistrib >= 13) {
                indiceDistrib = 0;
            }
            if (indiceDistrib < 3) {
                autreJoueur1.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib >= 3 && indiceDistrib < 6) {
                autreJoueur2.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib >= 6 && indiceDistrib < 9) {
                autreJoueur3.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib == 9) {
                chien.add(paquetMelange.get(i));
                indiceDistrib++;
            } else if (indiceDistrib >= 10 && indiceDistrib < 13) {
                joueur.add(paquetMelange.get(i));
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
            paquetMelange.add(paquet.get(iAlea));
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


        int ind_x = 1;
        int ind_y = 1;
        for (int i=0; i<cartes.size();i++)
        {
            cartes.get(i).setPlaceX(ind_x);
            cartes.get(i).setPlaceY(ind_y);
            if((ind_x >= 8) && (ind_y!=2))
            {
                ind_y++;
                ind_x = 0;
            }
            else
            {
                ind_x++;
            }
        }
    }

    public boolean aPetitSec(ArrayList<Carte> cartes)
    {
        int i = 0;
        boolean fini = false;
        boolean aExcuse = false;
        boolean aPetit = false;
        boolean aAtout = false;

        while(!fini && i < cartes.size()) {
            if (cartes.get(i).getType() == TypeCarte.EXCUSE)
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

    public boolean licite(Carte depot)
    {
        boolean atoutAutorise = true;
        for(int i = 0; i < joueur.size(); i++)
        {
            if(joueur.get(i).getType() != TypeCarte.ATOUT && joueur.get(i).getAjouteEcart() == false)
            {
                atoutAutorise = false;
            }
        }
        if((depot.getType() != TypeCarte.ATOUT && depot.getType() != TypeCarte.EXCUSE && depot.getNumero() == 14) ||
                (depot.getType() == TypeCarte.ATOUT && (depot.getNumero() == 21 || depot.getNumero() == 1)) ||
                (depot.getType() == TypeCarte.ATOUT && atoutAutorise == false))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void Ecart()
    {
        for(int i = 0; i < ecart.size(); i++)
        {
            joueur.remove(ecart.get(i));
        }
        joueur.addAll(chien);
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
