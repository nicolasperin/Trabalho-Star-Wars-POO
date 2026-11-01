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
public class DroideDeBatalhaB2 extends Guerreiro {

    private double energiaInicial = this.getEnergia();

    public DroideDeBatalhaB2(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    public DroideDeBatalhaB2(String nome, int idade, double peso, double energia) {
        super(nome, idade, peso, energia);
    }

    @Override
    public double getDanoDeAtaque() {
        return 10;
    }

    public void gerarDescendentes(ArrayList<Guerreiro> filaAliada) {
        if (energiaInicial > 1) {
            int energiaDescendente = (int) energiaInicial / 2;

            DroideDeBatalhaB2 desc1 = new DroideDeBatalhaB2(
                this.getNome() + "1",
                this.getIdade(),
                this.getPeso(),
                energiaDescendente
            );

            DroideDeBatalhaB2 desc2 = new DroideDeBatalhaB2(
                this.getNome() + "2",
                this.getIdade(),
                this.getPeso(),
                energiaDescendente
            );

            filaAliada.add(desc1);
            filaAliada.add(desc2);
        }
    }

    @Override
    public String getTipo() {
        return ("Droide de Batalha B2");
    }
    
    
}
