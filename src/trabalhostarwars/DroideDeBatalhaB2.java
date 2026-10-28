/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhostarwars;

/**
 *
 * @author Nicolas Pagini Perin
 */
public class DroideDeBatalhaB2 extends Guerreiro{

    public DroideDeBatalhaB2(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }

    @Override
    public double getDanoDeAtaque() {
        return 10;
    }

    public DroideDeBatalhaB2(String nome, int idade, double peso, double energia) {
        super(nome, idade, peso, energia);
    }
    
    private double energiaInicial = this.getEnergia();
    
    
    
}
