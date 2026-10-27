
package trabalhostarwars;

public class CloneMedico extends Guerreiro{
    
    //construtor inicializa dados por super
    public CloneMedico(String nome, int idade, double peso){
        super(nome, idade, peso);
    }
    
    @Override
    public double getDanoDeAtaque(){
        return(20);
    }
}
