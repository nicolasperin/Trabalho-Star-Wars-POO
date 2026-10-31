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
    private Guerreiro ultimoDerrotado = null;
    private Guerreiro autorUltimoAtaque = null;
    private Guerreiro alvoForcado = null;
    private ArrayList<ArrayList<Guerreiro>> filasBloqueadas = new ArrayList<>();

    Random random = new Random();

    public Combate(ArrayList<ArrayList<Guerreiro>> lado1, ArrayList<ArrayList<Guerreiro>> lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
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
    private ArrayList<Guerreiro> escolherFilaAlvo(int indiceFilaAtacante, ArrayList<ArrayList<Guerreiro>> ladoAdversario, boolean ladoAtacaPrimeiro) {

        if (!ladoAtacaPrimeiro && alvoForcado != null && !alvoForcado.estaDerrotado()) {
            for (int i = 0; i < ladoAdversario.size(); i++) {
                if (ladoAdversario.get(i).contains(alvoForcado)) {
                    return ladoAdversario.get(i);
                }
            }
        }

        int indiceDaFilaMaisProxima = retornarIndiceDaPrimeiraFilaEncontrada(indiceFilaAtacante, ladoAdversario);

        if (indiceDaFilaMaisProxima == -1) {
            return null;
        }

        return ladoAdversario.get(indiceDaFilaMaisProxima);
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

    public static void processarDerrotados(ArrayList<Guerreiro> fila) {
        for (int i = 0; i < fila.size(); i++) {

            Guerreiro guerreiroAtual = fila.get(i);

            if (guerreiroAtual.estaDerrotado()) {

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

            ArrayList<Guerreiro> proximaFila = escolherFilaAlvo(i, ladoAdversario, ladoAtacaPrimeiro);

            if (proximaFila == null) {
                return;
            }

            Guerreiro alvoDoAtaque = proximaFila.get(0); //guardo o alvo antes do ataque (usado pelo Jedi Mestre)

            if (!guerreiroAtual.estaDerrotado()) {

                guerreiroAtual.atacar(proximaFila, filaAtacanteAtual);
                processarDerrotados(filaAtacanteAtual); //atacantes podem morrer pro efeito do veneno

                if (guerreiroAtual instanceof AcolitoSith) {
                    guerreirosQueJaSeMoveram.add(guerreiroAtual);
                }
            }

            processarDerrotados(proximaFila);

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
