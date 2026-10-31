package trabalhostarwars;
import java.util.ArrayList;
/**
 * @author PauloLagasse
 */
public class CloneMedico extends Guerreiro{
    
    //construtor inicializa dados por super
    public CloneMedico(String nome, int idade, double peso){
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        return(20);
    }
    
    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada){
        if(this.estaDerrotado() || filaAdversaria.isEmpty()){
            return;
        }
        
        //buscar o primerio da outra lista e atacar ele
        Guerreiro alvo = filaAdversaria.get(0);
        if(alvo.estaDerrotado()){
            return;
        }
        alvo.receberDano(getDanoDeAtaque());
        
        //verificar se existe alguem depois do medioc, pq ai eu vou curar ele
        if(filaAliada.size() > 1){
            Guerreiro aliadoSeguinte = filaAliada.get(1);
            if(!(aliadoSeguinte.estaDerrotado())){
                aliadoSeguinte.recuperarEnergia(20);
            }
        }
        this.aplicarDanoVeneno();
    }
    
    @Override
    public String toString(){
        return("Clone Medico: " + super.toString());
    }
}
