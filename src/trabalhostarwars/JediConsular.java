package trabalhostarwars;
import java.util.ArrayList;
/**
 * @author PauloLagasse
 */
public class JediConsular extends Guerreiro{
    //contrutpr para inicializar os dados do guerreiro
    public JediConsular(String nome, int idade, double peso){
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        return(30);
    }
    
    @Override
    public void recuperarEnergia(double qtdEnergiaRecuperar){
        double qtdQueFaltaParaMax = (100 - this.getEnergia());
        
        double qtdRecuperar = Math.min(qtdEnergiaRecuperar, qtdQueFaltaParaMax);
        super.recuperarEnergia(qtdRecuperar);
    }
    
    private boolean atingirPrimeiroDaFila(ArrayList<Guerreiro> filaAdversaria, double dano){
        if(filaAdversaria.isEmpty()){
            return(false);
        }
        Guerreiro alvo = filaAdversaria.get(0);
        if(alvo.estaDerrotado()){
            return(false);
        }else{
            alvo.receberDano(dano, this);
            return(true);
        }
    }
    
    //metodo para atacar as filas do lado tbm. Vai receber o lado adversario e o indice da linha do consular
    public void atacarEmArea(ArrayList<ArrayList<Guerreiro>> ladoAdversario, int indiceFila){
        //ataque na memsa linha
        if(this.estaDerrotado()){
            return;
        }
        
        boolean realizouAtaque = false;
        //ataca o adiversario na mesma fila que ele com 30 de dano
        ArrayList<Guerreiro> fila = ladoAdversario.get(indiceFila);
        if(atingirPrimeiroDaFila(fila, 30)){
            realizouAtaque = true;
        }
        
        if(indiceFila > 0 && indiceFila < 3){ //tem anterior e proximo
            ArrayList<Guerreiro> filaAnterior = ladoAdversario.get(indiceFila - 1);
            if(atingirPrimeiroDaFila(filaAnterior, 15)){
                realizouAtaque = true;
            }
            ArrayList<Guerreiro> filaProximo = ladoAdversario.get(indiceFila + 1);
            if(atingirPrimeiroDaFila(filaProximo, 15)){
                realizouAtaque = true;
            }    
        }
        else if(indiceFila == 0){ //so tem proximo
            ArrayList<Guerreiro> filaProximo = ladoAdversario.get(indiceFila + 1);
            if(atingirPrimeiroDaFila(filaProximo, 15)){
                realizouAtaque = true;
            }
        }
        else{ //so tem anterior
            ArrayList<Guerreiro> filaAnterior = ladoAdversario.get(indiceFila - 1);
            if(atingirPrimeiroDaFila(filaAnterior, 15)){
                realizouAtaque = true;
            }
        }
        
        if(realizouAtaque){ //so vai aaplicar o veneno se ele atacou pelo menos 1
            this.aplicarDanoVeneno();
        }
    }

    @Override
    public String getTipo() {
        return ("Jedi Consular");
    }
    
    
}
