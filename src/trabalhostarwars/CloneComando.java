package trabalhostarwars;

import java.util.ArrayList;
/**
 * @author PauloLagasse
 */
public class CloneComando extends Guerreiro{
    //contrutor para inicializar os dados do guerreiro
    public CloneComando(String nome, int idade, double peso){
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        return(40);
    }
    
    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada){
        if(this.estaDerrotado() || filaAdversaria.isEmpty()){
            return;
        }
        
        double dano = getDanoDeAtaque();
        
        for(int i = 1; i < filaAliada.size(); i++){
            Guerreiro temp = filaAliada.get(i);
            if(temp instanceof CloneComando){
                if(temp.estaDerrotado() == false){
                    dano = dano + 8;
                }
            }
        }
        
        Guerreiro alvo = filaAdversaria.get(0);
        if(alvo.estaDerrotado()){
            return;
        }
        
        alvo.receberDano(dano); 
        this.aplicarDanoVeneno();
    }
    
    @Override
    public String toString(){
        return("Clone Comando: " + super.toString());
    }
}
