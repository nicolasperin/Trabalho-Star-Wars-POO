/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhostarwars;

import java.util.ArrayList;

/**
 *
 * @author Nicolas Pagini Perin
 */
public class DroideExterminador extends Guerreiro {

    public DroideExterminador(String nome, int idade, double peso) {
        super(nome, idade, peso, 60);
    }

    @Override
    public double getDanoDeAtaque() {
       return 0; // o ataque desse cara não tem um valor fixo
    }
    
    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }

        Guerreiro alvo = filaAdversaria.get(0);
        alvo.receberDano(alvo.getEnergia(), this); //ele executa o inimigo
        this.aplicarDanoVeneno();
    }

    @Override
    public String getTipo() {
        return ("Droide Exterminador");
    }
    
    
}
