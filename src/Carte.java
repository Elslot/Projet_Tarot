

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

    public int getIDType()
	{
		switch(this.getType()){
			case PIQUE :
				return 1;
			case CARREAU:
				return 2;
			case ATOUT:
				return 3;
			default:
				return -1;
		}
	}
}
