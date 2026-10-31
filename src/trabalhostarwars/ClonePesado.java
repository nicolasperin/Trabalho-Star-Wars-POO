package trabalhostarwars;
/**
 * @author PauloLagasse
 */
public class ClonePesado extends Guerreiro{
    //contrutpr para inicializar os dados do guerreiro com energia 400
    public ClonePesado(String nome, int idade, double peso){
        super(nome, idade, peso, 400);
    }

    @Override
    public double getDanoDeAtaque(){
        return(30);
    }
    
    @Override
    public String toString(){
        return("Clone Pesado: " + super.toString());
    }
}
