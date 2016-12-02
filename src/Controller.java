
public class Controller {

    private Model modele;

    public Controller(Model mod){

        modele=mod;
    }

    public void lancerDistribution()
    {

        modele.distribution();
    }
}
