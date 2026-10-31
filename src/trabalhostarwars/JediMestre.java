package trabalhostarwars;
import java.util.ArrayList;
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
    
    //private pois sera suado dentro por Jadi Mestre
    //Passar o Alvo e a fila adversaria
    private void empurrarAlvo(Guerreiro alvo, ArrayList<Guerreiro> filaAdversaria){
        if(alvo.estaDerrotado()){
            return;
        }
        
        //emburrar alvo pro final da fila
        boolean removido = filaAdversaria.remove(alvo);
        if(removido){
            filaAdversaria.add(alvo);
        }
    }
    
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada, boolean podeEmpurrar){
        if(this.estaDerrotado() || filaAdversaria.isEmpty()){
            return;
        }
        
        Guerreiro alvo = filaAdversaria.get(0);
        if(alvo.estaDerrotado()){
            return;
        }
        //aplica o atacar do super
        super.atacar(filaAdversaria, filaAliada);
        
        if(podeEmpurrar){
            empurrarAlvo(alvo, filaAdversaria);
        }
    }
    
    @Override
    public String toString(){
        return("Jedi Mestre: " + super.toString());
    }
}
