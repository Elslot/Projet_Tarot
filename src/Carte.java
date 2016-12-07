import java.awt.*;

public class Carte {
	private TypeCarte type;
	private int numero;
	private boolean turned;
	private int ind_place_x;
	private int ind_place_y;

    static Dimension DIMENSION_MODEL = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_H_MODEL = (int)DIMENSION_MODEL.getHeight();
    static int SCREEN_W_MODEL  = (int)DIMENSION_MODEL.getWidth();

	public Carte(TypeCarte type, int numero)
	{
		this.type = type;
		this.numero = numero;
		turned = false;
	}
	public boolean getTurned() {return turned; }
	public void setTurned() { turned=true; }

	public TypeCarte getType()
	{
		return type;
	}

	public int getNumero()
    {
        return numero;
    }

    public int getRankType()
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






}
