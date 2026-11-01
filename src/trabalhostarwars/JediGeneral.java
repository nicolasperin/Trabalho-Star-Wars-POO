package trabalhostarwars;
import java.util.ArrayList;
/**
 * @author PauloLagasse
 */
public class JediGeneral extends Guerreiro{
    
    private int inimigosDerrotados = 0;
    
    //contrutpr para inicializar os dados do guerreiro
    public JediGeneral(String nome, int idade, double peso){
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        //esse guerrreiro a cada inimigo derrotado ele aumenta 5 pontos de dano
        return(50 + (5 * inimigosDerrotados));
    }
    
    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada){
        if(this.estaDerrotado() || filaAdversaria.isEmpty()){
            return;
        }
        
        Guerreiro alvo = filaAdversaria.get(0);
        if(alvo.estaDerrotado()){
            return;
        }
        
        alvo.receberDano(getDanoDeAtaque());
        if(alvo.estaDerrotado()){
            inimigosDerrotados++;
            this.recuperarEnergia(20);
        }
        
        this.aplicarDanoVeneno();
    }
    
    @Override
    public void recuperarEnergia(double qtdEnergiaRecuperar){
        double qtdQueFaltaParaMax = (100 - this.getEnergia());
        
        double qtdRecuperar = Math.min(qtdEnergiaRecuperar, qtdQueFaltaParaMax);
        super.recuperarEnergia(qtdRecuperar);
    }
    
    @Override
    public String toString(){
        return("Jedi General: " + super.toString());
    }
}
