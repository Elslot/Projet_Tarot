

public class Carte {
	private TypeCarte type;
	private int numero;

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

}
