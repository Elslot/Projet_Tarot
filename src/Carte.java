
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
}
