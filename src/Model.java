import java.util.ArrayList;
import java.util.Comparator;
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
            paquet.add(new Carte(TypeCarte.COEUR, j));
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
            if(indiceDistrib >= 13)
            {
                indiceDistrib = 0;
            }
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
            paquetMelange.pop();
        }
    }

    public void melanger()
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

    public void trier(ArrayList<Carte> cartes)
    {
        //Tri en premier les cartes par leur numéro
        cartes.sort(new Comparator<Carte>() {
            @Override
            public int compare(Carte carte1, Carte carte2) {    //Redéfinissions du comparateur pour pouvoir bien trier les cartes
                return Integer.compare(carte1.getNumero(), carte2.getNumero()); //en fonction de leur numéro
            }
        });

        int i = 0;
        //Indices pour compter les cartes de chaque type
        int iPique = 0;
        int iCoeur = 0;
        int iAtout = 0;
        int iCarreau = 0;
        int iTrefle = 0;

        while(i < cartes.size())
        {
            if(cartes.get(i).getType() == TypeCarte.PIQUE)
            {
                iPique++; //Incrémente l'indice correspondant au type que l'on a trouvé (ici pique)
                if(i > iPique) //Si l'indice général est supérieur à l'indice des piques, donc cela veut dire qu'il y a une (ou plusieurs)
                              //carte d'un autre type entre deux piques,
                {
                    cartes.add(cartes.set(iPique, cartes.get(i))); //on remplace donc en fonction de l'indice
                }
                else
                {
                    i++;
                }
            }
            else if(cartes.get(i).getType() == TypeCarte.COEUR)
            {
                iCoeur++; //Incrémente l'indice correspondant au type que l'on a trouvé (ici coeur)
                if(i > iCoeur + iPique) //Si l'indice général est supérieur à l'indice des piques + coeurs (on compte les piques car ils doivent être en premiers),
                                       //donc cela veut dire qu'il y a une carte (ou plusieurs) d'un autre type entre deux piques,
                {
                    cartes.add(cartes.set(iPique + iCoeur, cartes.get(i)));//on remplace donc en fonction de l'indice
                }
                else
                {
                    i++;
                }
            }
            else if(cartes.get(i).getType() == TypeCarte.ATOUT)
            {
                iAtout++; //Incrémente l'indice correspondant au type que l'on a trouvé (ici coeur)
                if(i > iAtout + iCoeur + iPique) //Si l'indice général est supérieur à l'indice des piques + coeurs + atout (on compte les piques et les coeurs car
                                                //ils doivent être en premiers), donc cela veut dire qu'il y a une carte (ou plusieurs) d'un autre type
                                                //entre deux atouts,
                {
                    cartes.add(cartes.set(iPique + iCoeur + iAtout, cartes.get(i)));//on remplace donc en fonction de l'indice
                }
                else
                {
                    i++;
                }
            }
            else if(cartes.get(i).getType() == TypeCarte.CARREAU)
            {
                iCarreau++; //Incrémente l'indice correspondant au type que l'on a trouvé (ici coeur)
                if(i > iCarreau + iAtout + iCoeur + iPique) //Si l'indice général est supérieur à l'indice des piques + coeurs + ...
                                                            //(on compte ces cartes car elles doivent être en premières), donc cela veut dire qu'il y a une carte
                                                            //(ou plusieurs) d'un autre type entre deux carreaux,
                {
                    cartes.add(cartes.set(iPique + iCoeur + iAtout + iCarreau, cartes.get(i)));//on remplace donc en fonction de l'indice
                }
                else
                {
                    i++;
                }
            }
            else if(cartes.get(i).getType() == TypeCarte.TREFLE)
            {
                iTrefle++; //Incrémente l'indice correspondant au type que l'on a trouvé (ici coeur)
                if(i > iTrefle + iCarreau + iAtout + iCoeur + iPique) //Si l'indice général est supérieur à l'indice des piques + coeurs + ...
                                                                     //(on compte ces cartes car elles doivent être devant), donc cela veut dire qu'il y a une carte
                                                                    //(ou plusieurs) d'un autre type entre deux trèfles,
                {
                    cartes.add(cartes.set(iPique + iCoeur + iAtout + iCarreau + iTrefle, cartes.get(i)));//on remplace donc en fonction de l'indice
                }
                else
                {
                    i++;
                }
            }
        }
    }
}
