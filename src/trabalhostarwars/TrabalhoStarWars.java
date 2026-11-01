package trabalhostarwars;

import java.util.ArrayList;

public class TrabalhoStarWars {

    public static void main(String[] args) {
        {
            // Primeiro golpe elimina o único adversário comum
            {
                ArrayList<ArrayList<Guerreiro>> atacantes = criarLadoArena();
                ArrayList<ArrayList<Guerreiro>> adversarios = criarLadoArena();

                Guerreiro acolito = new AcolitoSith("Acolito", 20, 70);
                Guerreiro alvo = new AssassinoSith("Alvo", 25, 65);

                alvo.receberDano(85); // Fica com 15
                acolito.envenenar();

                atacantes.get(0).add(acolito);
                adversarios.get(0).add(alvo);

                Combate combate = new Combate(atacantes, adversarios);
                combate.executarAtaques(atacantes, adversarios, true);

                System.out.println("=== ALVO COMUM ===");
                System.out.println("Energia do alvo: " + alvo.getEnergia());
                System.out.println("Energia do acolito: " + acolito.getEnergia());
                System.out.println("Quantidade de adversarios: " + adversarios.get(0).size());
            }

            // Primeiro golpe elimina o B2; o segundo atinge o último descendente
            {
                ArrayList<ArrayList<Guerreiro>> atacantes = criarLadoArena();
                ArrayList<ArrayList<Guerreiro>> adversarios = criarLadoArena();

                Guerreiro acolito = new AcolitoSith("Acolito", 20, 70);
                Guerreiro b2 = new DroideDeBatalhaB2("B2", 15, 100);

                b2.receberDano(85); // Fica com 15

                atacantes.get(0).add(acolito);
                adversarios.get(0).add(b2);

                Combate combate = new Combate(atacantes, adversarios);
                combate.executarAtaques(atacantes, adversarios, true);

                System.out.println("\n=== B2 ===");
                System.out.println("Energia do B2 original: " + b2.getEnergia());
                System.out.println("Quantidade de adversarios: " + adversarios.get(0).size());

                for (Guerreiro guerreiro : adversarios.get(0)) {
                    System.out.println(
                            guerreiro.getNome() + " - Energia: " + guerreiro.getEnergia()
                    );
                }
            }
        }
        /*
        
        //guerreiros temporarios para teste
        Guerreiro Luke = new JediMestre("Luke", 25, 75.5);
        Guerreiro Judi = new JediMestre("Judi", 75, 50.62);
        Guerreiro Mupe = new CloneMedico("Mupe", 46, 62);
        Guerreiro Puti = new CloneMedico("Puti", 12, 43.0);
           
        //cria os dois lados com quatro filas cada
        ArrayList<ArrayList<Guerreiro>> lado1 = criarLadoArena();
        ArrayList<ArrayList<Guerreiro>> lado2 = criarLadoArena();
        
        //distribui os guerreiros nas filas do lado 1
        lado1.get(0).add(Luke);
        lado1.get(1).add(Judi);
        lado1.get(2).add(Mupe);
        lado1.get(0).add(Puti);

        //MOSTRAR OS DOIS LADOS DA ARENA
        System.out.println("LADO 1 - Jedi e Clones");
        exibirLadoArena(lado1);
        System.out.println("LADO 2 - Sith e Droides");
        exibirLadoArena(lado2);
        
        //MOSTRAR O MAIOR PESO DOS LADOS
        System.out.println("B)");
        System.out.println("Jedi e Clones pesam " + somarPesosDoLado(lado1) + " quilos.");
        System.out.println("Sith e Droides pesam " + somarPesosDoLado(lado2) + " quilos.");
         */
    }

    //metodo estatico pq é da classe
    //move o primeiro guerreiro para o final da fila
    public static void rotacionarFila(ArrayList<Guerreiro> fila) {
        //verificar se lista ta vazia, se tiver retorna antes de rotacionar
        if (fila.isEmpty()) {
            return;
        } else {
            Guerreiro rotacao = fila.remove(0); //pega o primeiro da fila (indice 0)
            fila.add(rotacao); //joga pro final (adicionando dnv)
        }
    }

    //metodo estatico pq é da classe
    //remove os guerreiros com energia menor ou igual a zero
    public static void removerDerrotado(ArrayList<Guerreiro> fila) {
        //verifico se a lista ta vazia
        if (fila.isEmpty()) {
            return;
        } else {
            for (int i = (fila.size() - 1); i >= 0; i--) { //percorrer a lista de tras pra frente pra nao pular nenhum guerreiro
                //chamar o metodo estaDerrotado pra cada guerreiro da lista, se for true remove
                Guerreiro temp = fila.get(i);
                if (temp.estaDerrotado() == true) {
                    fila.remove(i);
                }
            }
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
