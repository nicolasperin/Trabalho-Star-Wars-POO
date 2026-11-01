package trabalhostarwars;
/**
 * @author PauloLagasse
 */
public class JediMestre extends Guerreiro{   
    //construtor inicializa dados por super
    public JediMestre(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        return(35);
    }    
    
    @Override
    public void recuperarEnergia(double qtdEnergiaRecuperar){
        double qtdQueFaltaParaMax = (100 - this.getEnergia());
        
        double qtdRecuperar = Math.min(qtdEnergiaRecuperar, qtdQueFaltaParaMax);
        super.recuperarEnergia(qtdRecuperar);
    }
    

    @Override
    public String getTipo() {
        return ("Jedi Mestre");
    }
    
}
