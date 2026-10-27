package trabalhostarwars;

public abstract class Guerreiro {
    private String nome;
    private int idade;
    private double peso;
    private double energia;
    
    //construtor
    public Guerreiro(String nome, int idade, double peso){
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.energia = 100;
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
        this.energia = (this.energia - dano);
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
    
    public abstract double getDanoDeAtaque();
    
    //Metodo de ataque que vai receber o guerreiro alvo que vai sofrer ataque
    public void atacar(Guerreiro alvo){
        if(this.estaDerrotado() == true){ //this é quem ataca, to vendo se ele esta derrotado
            return;
        }
        else{
            alvo.receberDano(this.getDanoDeAtaque()); //alvo é quem recebe o dano do this
        }
    }
}
