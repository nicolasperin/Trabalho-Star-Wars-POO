package trabalhostarwars;
import java.util.ArrayList;
public abstract class Guerreiro {
    private String nome;
    private int idade;
    private double peso;
    private double energia;
    private boolean envenenado = false;
    
    //construtor
    public Guerreiro(String nome, int idade, double peso) {
        this(nome, idade, peso, 100);
    }


    public Guerreiro(String nome, int idade, double peso, double energia){
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.energia = energia;
    }
    
    //metodos com retorno, permite consultar nome, idade, peso e energia
    public String getNome(){
        return(nome);
    }
    
    public int getIdade(){
        return(idade);
    }
    
    public double getPeso(){
        return(peso);
    }
    
    public double getEnergia(){
        return(energia);
    }
    
    //metodo de receber dano
    public void receberDano(double dano){ //parametro indica qtd dano que será descontado
        if(dano <= 0){
            return;
        }
        this.energia = (this.energia - dano);
    }
    
    //metodo de recuperar energia, onde receber a do objeto e acrescenta mais a quantidade
    public void recuperarEnergia(double qtdEnergiaRecuperar){
        if(this.estaDerrotado() || qtdEnergiaRecuperar <= 0){
            return;
        }
        this.energia = (this.energia + qtdEnergiaRecuperar);
    }
    
    //metodo de esta derrotado
    public boolean estaDerrotado(){
        if(this.energia <= 0){
            return(true);
        }
        else{
            return(false);
        }
    }
    
    public void envenenar(){
        this.envenenado = true;
    }
    
    public boolean estaEnvenenado(){
        return this.envenenado;
    }
    
    public void aplicarDanoVeneno(){
        if (this.envenenado){
            this.receberDano(5);
        }
    }
    
    public abstract double getDanoDeAtaque();
    
    //Metodo de ataque que vai receber o guerreiro alvo que vai sofrer ataque

    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }

        Guerreiro alvo = filaAdversaria.get(0);
        if(alvo.estaDerrotado()){
            return;
        }
        alvo.receberDano(this.getDanoDeAtaque());
        this.aplicarDanoVeneno();
    }
    
    //APRESENTAR DADOS DOS GUERREIROS
    @Override
    public String toString(){
        return(nome + ", " + idade + " anos, " + peso + " quilos");
    }
}
