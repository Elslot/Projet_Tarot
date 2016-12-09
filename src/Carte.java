import java.awt.*;

public class Carte {
	private TypeCarte type;
	private int numero;
	private boolean turned; //définie si la carte est tournée côté face ou non
	private int ind_place_x; //place de la carte sur l'écran, en colonne
	private int ind_place_y; //place de la carte sur l'écran, en ligne
	private boolean ajouteEcart; //définie si la variable est ajouté à l'écart ou non

	//Permet de récupérer les dimensions de l'écran
    static Dimension DIMENSION_MODEL = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_H_MODEL = (int)DIMENSION_MODEL.getHeight();
    static int SCREEN_W_MODEL  = (int)DIMENSION_MODEL.getWidth();

	public Carte(TypeCarte type, int numero)
	{
		this.type = type;
		this.numero = numero;

        //Au début, les cartes sont mélangées et distribuées, elle sont donc tournées côté dos
		turned = false;
		ajouteEcart = false;
	}

	public TypeCarte getType()
	{
		return type;
	}

	public int getNumero()
    {
        return numero;
    }

    public int getRankType() //Permet de récupérer le rang du type, en fonction de l'ordre dans lequel les cartes doivent apparaîtres triées.
                            //Sert principalement pour déplacer le rectangle de lecture, à la création d'un carte view.
	{
		switch(this.getType()){
			case PIQUE :
				return 1;
			case COEUR:
				return 2;
			case ATOUT:
				if (numero<=14)
					return 3;
				else
					return 4;
			case CARREAU:
				return 5;
			case TREFLE:
				return 6;
			case EXCUSE:
				return 7;
			default:
				return -1;
		}
	}

	public double getPlaceX(){ return ind_place_x;}
	public double getPlaceY(){ return ind_place_y;}
	public void setPlaceX(int x){ ind_place_x=x;}
	public void setPlaceY(int y){ ind_place_y=y;}

	public boolean  getAjouteEcart() { return ajouteEcart; }
	public void  setAjouteEcart(boolean ajoutee) { ajouteEcart = ajoutee; }

}
