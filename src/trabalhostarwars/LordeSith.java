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
public class LordeSith extends Guerreiro {

    public LordeSith(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    @Override
    public double getDanoDeAtaque() {
        return 50;
    }

    public void criarAcolitos(ArrayList<Guerreiro> filaAliada) {
        for (int i = 0; i < 4; i++) {
            AcolitoSith acolito = new AcolitoSith(this.getNome(), 0, 60);
            filaAliada.add(acolito);
        }
    }

    @Override
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }

        Guerreiro alvo = filaAdversaria.get(0);
        if (alvo.estaDerrotado()) {
            return;
        }
        alvo.receberDano(this.getDanoDeAtaque());

        if (alvo.estaDerrotado()) {
            Guerreiro espectro = new EspectroSith(alvo.getNome(), alvo.getIdade(), alvo.getPeso());
            filaAliada.add(espectro);
        }

        this.aplicarDanoVeneno();

    }

}
