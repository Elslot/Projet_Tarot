

public class Carte {
	private TypeCarte type;
	private int numero;
	private int pos_x;
	private int pos_y;

	public Carte(TypeCarte type, int numero)
	{
		this.type = type;
		this.numero = numero;

	}

	public TypeCarte getType()
	{
		return type;
	}

	public int getNumero()
    {
        return numero;
    }

    public int getIDType()
	{
		switch(this.getType()){
			case PIQUE :
				return 1;
			case COEUR:
				return 2;
			case ATOUT:
				return 3;
			case CARREAU:
				return 4;
			case TREFLE:
				return 5;
			case EXCUSE:
				return 6;
			default:
				return -1;
		}
	}

	public void setX(int x)
	{
		pos_x = x;
	}
	public void setY(int y)
	{
		pos_y = y;
	}
}
