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
public class AcolitoSith extends Guerreiro {

    public AcolitoSith(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    @Override
    public double getDanoDeAtaque() {
        return 15;
    }

    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }
        Guerreiro alvo1 = filaAdversaria.get(0);
        alvo1.receberDano(this.getDanoDeAtaque());
        this.aplicarDanoVeneno();
        Combate.processarDerrotados(filaAdversaria);

        if (this.estaDerrotado()) {
            return;
        }

        filaAliada.remove(this);
        filaAliada.add(this);
        if (!filaAdversaria.isEmpty()) {
            if (!filaAdversaria.get(filaAdversaria.size() - 1).estaDerrotado()) {
                Guerreiro alvo2 = filaAdversaria.get(filaAdversaria.size() - 1);
                alvo2.receberDano(this.getDanoDeAtaque());
                this.aplicarDanoVeneno();
            }
        }

    }

}
