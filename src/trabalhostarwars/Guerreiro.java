package trabalhostarwars;

import java.util.ArrayList;

public abstract class Guerreiro {

    private String nome;
    private int idade;
    private double peso;
    private double energia;
    private boolean envenenado = false;
    private Guerreiro ultimoAtacante = null;

    public Guerreiro getUltimoAtacante() {
        return (ultimoAtacante);
    }

    //construtor
    public Guerreiro(String nome, int idade, double peso) {
        this(nome, idade, peso, 100);
    }

    public Guerreiro(String nome, int idade, double peso, double energia) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.energia = energia;
    }

    //metodos com retorno, permite consultar nome, idade, peso e energia
    public String getNome() {
        return (nome);
    }

    public int getIdade() {
        return (idade);
    }

    public double getPeso() {
        return (peso);
    }

    public double getEnergia() {
        return (energia);
    }

    //metodo de receber dano
    public void receberDano(double dano) { //parametro indica qtd dano que será descontado
        if (dano <= 0) {
            return;
        }
        this.energia = (this.energia - dano);
    }

    public void receberDano(double dano, Guerreiro atacante) {
        if (dano <= 0) {
            return;
        }
        this.ultimoAtacante = atacante;
        this.receberDano(dano);
    }

    //metodo de recuperar energia, onde receber a do objeto e acrescenta mais a quantidade
    public void recuperarEnergia(double qtdEnergiaRecuperar) {
        if (this.estaDerrotado() || qtdEnergiaRecuperar <= 0) {
            return;
        }
        this.energia = (this.energia + qtdEnergiaRecuperar);
    }

    //metodo de esta derrotado
    public boolean estaDerrotado() {
        if (this.energia <= 0) {
            return (true);
        } else {
            return (false);
        }
    }

    public void envenenar() {
        this.envenenado = true;
    }

    public boolean estaEnvenenado() {
        return this.envenenado;
    }

    public void aplicarDanoVeneno() {
        if (this.envenenado) {
            this.receberDano(5);
        }
    }

    public abstract double getDanoDeAtaque();

    public abstract String getTipo();

//mostra o peso sem ".0" quando for inteiro (70.0 vira 70)
    public static String formatarPeso(double peso) {
        if (peso == Math.floor(peso)) {
            return String.valueOf((long) peso);
        }
        return String.valueOf(peso);
    }

    //Metodo de ataque que vai receber o guerreiro alvo que vai sofrer ataque
    public void atacar(ArrayList<Guerreiro> filaAdversaria, ArrayList<Guerreiro> filaAliada) {
        if (this.estaDerrotado() || filaAdversaria.isEmpty()) {
            return;
        }

        Guerreiro alvo = filaAdversaria.get(0);
        if (alvo.estaDerrotado()) {
            return;
        }
        alvo.receberDano(this.getDanoDeAtaque(), this);
        this.aplicarDanoVeneno();
    }

    //APRESENTAR DADOS DOS GUERREIROS
    @Override

    public String toString() {
        return (getTipo() + ": " + nome + ", " + idade + " anos, " + formatarPeso(peso) + " kilos");
    }
}
