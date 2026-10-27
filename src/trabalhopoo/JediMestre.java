
package trabalhostarwars;

public class JediMestre extends Guerreiro{
       
    //construtor inicializa dados por super
    public JediMestre(String nome, int idade, double peso) {
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        return(35);
    }    
}
