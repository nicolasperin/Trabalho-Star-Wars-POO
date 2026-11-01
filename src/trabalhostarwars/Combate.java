/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhostarwars;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Nicolas Pagini Perin
 */
public class Combate {

    private ArrayList<ArrayList<Guerreiro>> lado1;
    private ArrayList<ArrayList<Guerreiro>> lado2;
    private ArrayList<Guerreiro> guerreirosQueJaSeMoveram = new ArrayList<>();
    //registro cronologico de quem foi derrotado e de qual fila saiu
    //static porque processarDerrotados e static (o AcolitoSith chama direto)
    private static ArrayList<Guerreiro> derrotados = new ArrayList<>();
    private static ArrayList<ArrayList<Guerreiro>> filasDosDerrotados = new ArrayList<>();
    private Guerreiro alvoForcado = null;
    private ArrayList<ArrayList<Guerreiro>> filasBloqueadas = new ArrayList<>();
    
    private static final int LIMITE_DE_RODADAS = 10000;
private int totalDeRodadas = 0;

//repete as rodadas ate um lado ficar sem guerreiros (ou estourar o limite)
public void executarCombate() {
    while (!TrabalhoStarWars.ladoDaArenaVazio(lado1) && !TrabalhoStarWars.ladoDaArenaVazio(lado2) && totalDeRodadas < LIMITE_DE_RODADAS) {
        executarRodada();
        totalDeRodadas++;
    }
}

//devolve o lado vencedor (null se nao houve vencedor)
public ArrayList<ArrayList<Guerreiro>> obterLadoVencedor() {
    boolean lado1Vazio = TrabalhoStarWars.ladoDaArenaVazio(lado1);
    boolean lado2Vazio = TrabalhoStarWars.ladoDaArenaVazio(lado2);

    if (lado2Vazio && !lado1Vazio) {
        return lado1;
    }
    if (lado1Vazio && !lado2Vazio) {
        return lado2;
    }
    return null;
}

    Random random = new Random();

    public Combate(ArrayList<ArrayList<Guerreiro>> lado1, ArrayList<ArrayList<Guerreiro>> lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
        derrotados.clear();
        filasDosDerrotados.clear();
    }

    public int sortear() {
        return random.nextInt(2) + 1;
    }

    public ArrayList<Guerreiro> obterGuerreirosDaFrente(ArrayList<ArrayList<Guerreiro>> lado) {
        ArrayList<Guerreiro> guerreirosDoInicioDeCadaFila = new ArrayList<>();

        for (int i = 0; i < lado.size(); i++) {

            ArrayList<Guerreiro> fila = lado.get(i);

            if (!fila.isEmpty()) {
                guerreirosDoInicioDeCadaFila.add(fila.get(0));
            } else {
                guerreirosDoInicioDeCadaFila.add(null);
            }

        }

        return guerreirosDoInicioDeCadaFila;
    }

    //se o lado adversario ativou o Clone Pesado, ele é o alvo; senão usa a fila mais próxima
//devolve o indice da fila adversaria que vai receber o ataque (-1 se o lado adversario acabou)
    private int escolherIndiceFilaAlvo(int indiceFilaAtacante, ArrayList<ArrayList<Guerreiro>> ladoAdversario, boolean ladoAtacaPrimeiro) {

        if (!ladoAtacaPrimeiro && alvoForcado != null && !alvoForcado.estaDerrotado()) {
            for (int i = 0; i < ladoAdversario.size(); i++) {
                if (ladoAdversario.get(i).contains(alvoForcado)) {
                    return i;
                }
            }
        }

        return retornarIndiceDaPrimeiraFilaEncontrada(indiceFilaAtacante, ladoAdversario);
    }

    public void rotacionarGuerreirosDaRodada(ArrayList<ArrayList<Guerreiro>> lado, ArrayList<Guerreiro> guerreirosDaFrente) {

        for (int i = 0; i < lado.size(); i++) {

            ArrayList<Guerreiro> filaAtual = lado.get(i);
            Guerreiro guerreiroAtual = guerreirosDaFrente.get(i);

            if (guerreiroAtual != null && !guerreiroAtual.estaDerrotado() && !filaAtual.isEmpty() && filaAtual.get(0) == guerreiroAtual && !guerreirosQueJaSeMoveram.contains(guerreiroAtual)) {

                filaAtual.remove(0);
                filaAtual.add(guerreiroAtual);

            }

        }

    }

    public int retornarIndiceDaPrimeiraFilaEncontrada(int indiceInicial, ArrayList<ArrayList<Guerreiro>> lado) {

        for (int i = 0; i < 4; i++) {
            int indiceAtual = (indiceInicial + i) % 4;
            ArrayList<Guerreiro> atual = lado.get(indiceAtual);

            if (!atual.isEmpty()) {
                return indiceAtual;
            }
        }

        return -1;
    }
    //compara por identidade (==): o equals do ArrayList compara o conteudo

    private boolean ladoContemFila(ArrayList<ArrayList<Guerreiro>> lado, ArrayList<Guerreiro> fila) {
        for (int i = 0; i < lado.size(); i++) {
            if (lado.get(i) == fila) {
                return true;
            }
        }
        return false;
    }

//percorre o registro de tras pra frente e devolve o ultimo derrotado daquele lado
    public Guerreiro obterUltimoDerrotadoDoLado(ArrayList<ArrayList<Guerreiro>> lado) {
        for (int i = derrotados.size() - 1; i >= 0; i--) {
            if (ladoContemFila(lado, filasDosDerrotados.get(i))) {
                return derrotados.get(i);
            }
        }
        return null;
    }

    public static void processarDerrotados(ArrayList<Guerreiro> fila) {
        for (int i = 0; i < fila.size(); i++) {

            Guerreiro guerreiroAtual = fila.get(i);

            if (guerreiroAtual.estaDerrotado()) {
                derrotados.add(guerreiroAtual);
                filasDosDerrotados.add(fila);

                switch (guerreiroAtual) {

                    case DroideDeBatalhaB2 droideDeBatalhaB2 ->
                        droideDeBatalhaB2.gerarDescendentes(fila);
                    case LordeSith lordeSith ->
                        lordeSith.criarAcolitos(fila);
                    default -> {

                    }
                }
                fila.remove(i);
                i--;
            }
        }
    }

    public void executarAtaques(ArrayList<ArrayList<Guerreiro>> ladoAtacante, ArrayList<ArrayList<Guerreiro>> ladoAdversario, boolean ladoAtacaPrimeiro) {
        for (int i = 0; i < 4; i++) {

            ArrayList<Guerreiro> filaAtacanteAtual = ladoAtacante.get(i);

            //fila vazia ou fila empurrada pelo Jedi Mestre nao ataca nessa rodada
            if (filaAtacanteAtual.isEmpty() || filasBloqueadas.contains(filaAtacanteAtual)) {
                continue;
            }

            Guerreiro guerreiroAtual = filaAtacanteAtual.get(0);

            int indiceFilaAlvo = escolherIndiceFilaAlvo(i, ladoAdversario, ladoAtacaPrimeiro);

            if (indiceFilaAlvo == -1) {
                return;
            }

            ArrayList<Guerreiro> proximaFila = ladoAdversario.get(indiceFilaAlvo);
            Guerreiro alvoDoAtaque = proximaFila.get(0); //guardo o alvo antes do ataque (usado pelo Jedi Mestre)

            if (!guerreiroAtual.estaDerrotado()) {

                if (guerreiroAtual instanceof JediConsular consular) {
                    consular.atacarEmArea(ladoAdversario, indiceFilaAlvo);
                } else {
                    guerreiroAtual.atacar(proximaFila, filaAtacanteAtual);
                }
                processarDerrotados(filaAtacanteAtual); //atacantes podem morrer pelo efeito do veneno

                if (guerreiroAtual instanceof AcolitoSith) {
                    guerreirosQueJaSeMoveram.add(guerreiroAtual);
                }
            }

            //o Consular pode ter acertado varias filas, entao processa todas as filas adversarias
            for (int j = 0; j < ladoAdversario.size(); j++) {
                processarDerrotados(ladoAdversario.get(j));
            }

            //habilidades que so valem para quem ataca primeiro
            if (ladoAtacaPrimeiro && !guerreiroAtual.estaDerrotado()) {

                if (guerreiroAtual instanceof ClonePesado) {
                    alvoForcado = guerreiroAtual; //o ultimo a ativar sobrescreve o anterior
                }

                if (guerreiroAtual instanceof JediMestre && !alvoDoAtaque.estaDerrotado()) {
                    proximaFila.remove(alvoDoAtaque);
                    proximaFila.add(alvoDoAtaque); //empurra o alvo pro final da fila
                    filasBloqueadas.add(proximaFila); //a fila nao ataca nessa rodada
                }
            }
        }
    }

    public void executarRodada() {
        ArrayList<Guerreiro> frenteLado1 = obterGuerreirosDaFrente(lado1);
        ArrayList<Guerreiro> frenteLado2 = obterGuerreirosDaFrente(lado2);
        guerreirosQueJaSeMoveram.clear();
        filasBloqueadas.clear();
        alvoForcado = null;
        int sorteio = this.sortear();

        if (sorteio == 1) {
            executarAtaques(this.lado1, this.lado2, true);
            executarAtaques(this.lado2, this.lado1, false);
        } else {
            executarAtaques(this.lado2, this.lado1, true);
            executarAtaques(this.lado1, this.lado2, false);
        }

        rotacionarGuerreirosDaRodada(this.lado1, frenteLado1);
        rotacionarGuerreirosDaRodada(this.lado2, frenteLado2);
    }

}
