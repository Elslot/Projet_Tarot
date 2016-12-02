import java.awt.*;

public class Carte {
	private TypeCarte type;
	private int numero;
	private boolean turned;
    private double x;
    private double y;

    static Dimension DIMENSION_MODEL = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_H_MODEL = (int)DIMENSION_MODEL.getHeight();
    static int SCREEN_W_MODEL  = (int)DIMENSION_MODEL.getWidth();

	public Carte(TypeCarte type, int numero)
	{
		this.type = type;
		this.numero = numero;
		turned = false;
        x = SCREEN_W_MODEL/10;
        y = SCREEN_H_MODEL/10;
	}
	public boolean getTurned() {return turned; }

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

	public double getPosX()
	{
		return x;
	}

	public double getPosY()
	{
		return y;
	}


	public void setPosX(double new_x)
	{
		this.x = new_x;
	}

	public void setPosY(double new_y)
	{
		this.y = new_y;
	}




}
