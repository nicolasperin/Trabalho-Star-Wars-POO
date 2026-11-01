/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhostarwars;

import java.util.ArrayList;

public class AssassinoSith extends Guerreiro {

    public AssassinoSith(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    @Override
    public double getDanoDeAtaque() {
        return 20;
    }
    
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }
        
        Guerreiro alvo = filaAdversaria.get(0);
        alvo.receberDano(this.getDanoDeAtaque(), this);
        alvo.envenenar();
        this.aplicarDanoVeneno();
    }

    @Override
    public String getTipo() {
        return ("Assassino Sith");
    }
    
}
