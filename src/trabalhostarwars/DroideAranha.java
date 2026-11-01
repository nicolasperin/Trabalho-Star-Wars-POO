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
public class DroideAranha extends Guerreiro{

    public DroideAranha(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    @Override
    public double getDanoDeAtaque() {
        return 10;
    }
    
    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < filaAdversaria.size(); i++){
            Guerreiro alvo = filaAdversaria.get(i);
            alvo.receberDano(this.getDanoDeAtaque(), this);     
        }
        this.aplicarDanoVeneno();
    }

    @Override
    public String getTipo() {
        return ("Droide Aranha");
    }
    
    
}
