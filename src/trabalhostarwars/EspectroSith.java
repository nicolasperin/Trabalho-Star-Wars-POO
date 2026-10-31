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
public class EspectroSith extends Guerreiro{

    public EspectroSith(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    @Override
    public double getDanoDeAtaque() {
        return 5;
    }
    
    
    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }

        Guerreiro alvo = filaAdversaria.get(0);
        alvo.receberDano(this.getDanoDeAtaque());
        this.aplicarDanoVeneno();

    }
    
}
