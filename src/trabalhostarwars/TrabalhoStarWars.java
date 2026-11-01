package trabalhostarwars;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrabalhoStarWars {

    //metodo estatico pq é da classe
//cria o guerreiro do lado 1 conforme o tipo lido no arquivo
    public static Guerreiro criarGuerreiroLado1(int tipo, String nome, int idade, double peso) {
        return switch (tipo) {
            case 1 ->
                new JediMestre(nome, idade, peso);
            case 2 ->
                new JediConsular(nome, idade, peso);
            case 3 ->
                new JediGeneral(nome, idade, peso);
            case 4 ->
                new CloneMedico(nome, idade, peso);
            case 5 ->
                new CloneComando(nome, idade, peso);
            case 6 ->
                new ClonePesado(nome, idade, peso);
            default ->
                null;
        };
    }

//cria o guerreiro do lado 2 conforme o tipo lido no arquivo
    public static Guerreiro criarGuerreiroLado2(int tipo, String nome, int idade, double peso) {
        return switch (tipo) {
            case 1 ->
                new DroideDeBatalhaB2(nome, idade, peso);
            case 2 ->
                new DroideAranha(nome, idade, peso);
            case 3 ->
                new DroideExterminador(nome, idade, peso);
            case 4 ->
                new AcolitoSith(nome, idade, peso);
            case 5 ->
                new AssassinoSith(nome, idade, peso);
            case 6 ->
                new LordeSith(nome, idade, peso);
            default ->
                null;
        };
    }

    //metodo estatico pq é da classe
//le um arquivo e preenche a fila; lado define qual fabrica usar (1 ou 2)
    public static void lerFila(String caminho, int lado, ArrayList<Guerreiro> fila) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int numeroDaLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroDaLinha++;
                linha = linha.trim();

                if (linha.isEmpty()) {
                    continue;
                }

                String[] partes = linha.split("\\s+");
                if (partes.length != 4) {
                    System.out.println("Linha " + numeroDaLinha + " invalida em " + caminho);
                    continue;
                }

                try {
                    int tipo = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    int idade = Integer.parseInt(partes[2]);
                    double peso = Double.parseDouble(partes[3].replace(',', '.'));

                    Guerreiro novo;
                    if (lado == 1) {
                        novo = criarGuerreiroLado1(tipo, nome, idade, peso);
                    } else {
                        novo = criarGuerreiroLado2(tipo, nome, idade, peso);
                    }

                    if (novo == null) {
                        System.out.println("Tipo " + tipo + " invalido na linha " + numeroDaLinha + " de " + caminho);
                    } else {
                        fila.add(novo);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido na linha " + numeroDaLinha + " de " + caminho);
                }
            }
        } catch (IOException e) {
            System.out.println("Nao foi possivel ler o arquivo " + caminho);
        }
    }

//le as 4 filas de um lado (fila11..fila14 ou fila21..fila24)
    public static ArrayList<ArrayList<Guerreiro>> lerLado(int lado) {
        ArrayList<ArrayList<Guerreiro>> ladoArena = criarLadoArena();

        for (int i = 0; i < 4; i++) {
            lerFila("fila" + lado + (i + 1) + ".txt", lado, ladoArena.get(i));
        }
        return (ladoArena);
    }

    //C) devolve o guerreiro mais velho dos dois lados (empate: o primeiro encontrado)
    public static Guerreiro encontrarMaisVelho(ArrayList<ArrayList<Guerreiro>> lado1, ArrayList<ArrayList<Guerreiro>> lado2) {
        Guerreiro maisVelho = maisVelhoDoLado(lado1, null);
        maisVelho = maisVelhoDoLado(lado2, maisVelho);
        return (maisVelho);
    }

    private static Guerreiro maisVelhoDoLado(ArrayList<ArrayList<Guerreiro>> lado, Guerreiro maisVelhoAtual) {
        for (int i = 0; i < lado.size(); i++) {
            for (int j = 0; j < lado.get(i).size(); j++) {
                Guerreiro temp = lado.get(i).get(j);
                if (maisVelhoAtual == null || temp.getIdade() > maisVelhoAtual.getIdade()) {
                    maisVelhoAtual = temp;
                }
            }
        }
        return (maisVelhoAtual);
    }

//monta o trecho "Acólito Sith Anu, de 40 anos e 50 kilos"
    public static String descreverGuerreiro(Guerreiro g) {
        return (g.getTipo() + " " + g.getNome() + ", de " + g.getIdade() + " anos e " + Guerreiro.formatarPeso(g.getPeso()) + " kilos");
    }

    public static void main(String[] args) {

        ArrayList<ArrayList<Guerreiro>> lado1 = lerLado(1);
        ArrayList<ArrayList<Guerreiro>> lado2 = lerLado(2);

        //a, b e c usam o estado inicial, entao vem antes do combate
        System.out.println("a)");
        System.out.println("LADO 1:");
        exibirLadoArena(lado1);
        System.out.println("LADO 2:");
        exibirLadoArena(lado2);

        System.out.println("b)");
        System.out.println("Jedi e Clones pesam " + Guerreiro.formatarPeso(somarPesosDoLado(lado1)) + " kilos");
        System.out.println("Sith e Droides pesam " + Guerreiro.formatarPeso(somarPesosDoLado(lado2)) + " kilos");

        System.out.println("c)");
        Guerreiro maisVelho = encontrarMaisVelho(lado1, lado2);
        if (maisVelho != null) {
            System.out.println("O " + maisVelho.getTipo() + " " + maisVelho.getNome() + " é o mais velho e tem " + maisVelho.getIdade() + " anos");
        }

        Combate combate = new Combate(lado1, lado2);
        combate.executarCombate();

        ArrayList<ArrayList<Guerreiro>> ladoVencedor = combate.obterLadoVencedor();

        System.out.println("d)");
        if (ladoVencedor == null) {
            System.out.println("Nao houve vencedor.");
            return;
        }

        boolean lado1Venceu = (ladoVencedor == lado1);
        String nomeVencedor = lado1Venceu ? "Jedi e Clones" : "Sith e Droides";
        String nomePerdedor = lado1Venceu ? "Sith e Droides" : "Jedi e Clones";
        ArrayList<ArrayList<Guerreiro>> ladoPerdedor = lado1Venceu ? lado2 : lado1;

        System.out.println(nomeVencedor + " venceram.");

        Guerreiro ultimoDerrotado = combate.obterUltimoDerrotadoDoLado(ladoPerdedor);

        System.out.println("e)");
        if (ultimoDerrotado != null) {
            System.out.println("O " + descreverGuerreiro(ultimoDerrotado) + ", foi o último a ser derrotado no lado dos " + nomePerdedor);
        }

        System.out.println("f)");
        if (ultimoDerrotado != null && ultimoDerrotado.getUltimoAtacante() != null) {
            System.out.println("O " + descreverGuerreiro(ultimoDerrotado.getUltimoAtacante()) + ", desferiu o último ataque no " + descreverGuerreiro(ultimoDerrotado) + ".");
        }
    }


    //metodo estatico pq é da classe
    //cria um lado com quatro filas vazias e independentes
    public static ArrayList<ArrayList<Guerreiro>> criarLadoArena() {
        ArrayList<ArrayList<Guerreiro>> lado = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            lado.add(new ArrayList<>());
        }
        return (lado);
    }

    //metodo estatico pq é da classe
    //percorre as filas e mostra seus guerreiros (qykaquer lado)
    //i = fila ; j = guerreiro na fila
    //A) EXIBA OS DADOS DE AMBOS OS LADOS
    public static void exibirLadoArena(ArrayList<ArrayList<Guerreiro>> lado) {
        for (int i = 0; i < lado.size(); i++) {
            System.out.println("Fila " + (i + 1) + ":");
            if (lado.get(i).isEmpty()) {
                System.out.println("  Vazia");
            } else {
                for (int j = 0; j < lado.get(i).size(); j++) { //laco que percorrer a fila completa
                    Guerreiro temp = lado.get(i).get(j); //acessa a lista do indice e imprime o guerreiro da posicao j
                    System.out.println("  " + temp.toString());
                }
            }
        }
    }

    public static boolean ladoDaArenaVazio(ArrayList<ArrayList<Guerreiro>> lado) {
        for (int i = 0; i < lado.size(); i++) {
            if (!(lado.get(i).isEmpty())) {
                return (false);
            }
        }
        return (true);
    }

    //B)A SOMA DOS PESOS DE AMBOS OS LADOS
    public static double somarPesosDoLado(ArrayList<ArrayList<Guerreiro>> lado) {
        double soma = 0;

        for (int i = 0; i < lado.size(); i++) {
            for (int j = 0; j < lado.get(i).size(); j++) {
                Guerreiro temp = lado.get(i).get(j);
                soma = soma + temp.getPeso();
            }
        }
        return (soma);
    }
}
