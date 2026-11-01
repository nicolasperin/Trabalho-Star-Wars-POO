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


/*
    public static void main(String[] args){
        // =====================================================
        // TESTE 1 - JEDI MESTRE
        // =====================================================
        {
            System.out.println("\n========== TESTE 1: BLOQUEIO JEDI MESTRE ==========");

            ArrayList<ArrayList<Guerreiro>> lado1 = criarLadoArena();
            ArrayList<ArrayList<Guerreiro>> lado2 = criarLadoArena();

            JediMestre luke = new JediMestre("Luke", 25, 75.5);

            Guerreiro aliado = new CloneMedico("Mupe", 46, 62);

            Guerreiro sith1 = new AssassinoSith("Sith 1", 25, 65);
            Guerreiro sith2 = new AcolitoSith("Sith 2", 20, 70);
            Guerreiro sith3 = new AssassinoSith("Sith 3", 30, 68);

            lado1.get(0).add(luke);
            lado1.get(1).add(aliado);

            lado2.get(0).add(sith1);
            lado2.get(0).add(sith2);
            lado2.get(1).add(sith3);

            Combate combate = new Combate(lado1, lado2);

            System.out.println("\nANTES:");
            exibirLadoArena(lado1);
            exibirLadoArena(lado2);

            // Luke ataca primeiro e bloqueia a fila 0 adversária
            combate.executarAtaques(lado1, lado2, true);

            System.out.println("\nDEPOIS DO ATAQUE DO JEDI:");
            exibirLadoArena(lado2);

            // Agora os adversários tentam atacar
            combate.executarAtaques(lado2, lado1, false);

            System.out.println("\nDEPOIS DA RESPOSTA ADVERSÁRIA:");
            exibirLadoArena(lado1);

            System.out.println("\nENERGIAS FINAIS:");
            System.out.println("Luke: " + luke.getEnergia());
            System.out.println("Mupe: " + aliado.getEnergia());
        }

        // =====================================================
        // TESTE 2 - JEDI CONSULAR
        // =====================================================
        {
            System.out.println("\n========== TESTE 2: JEDI CONSULAR ==========");

            ArrayList<ArrayList<Guerreiro>> lado1 = criarLadoArena();
            ArrayList<ArrayList<Guerreiro>> lado2 = criarLadoArena();

            JediConsular consular = new JediConsular("Consular", 30, 65);

            Guerreiro alvo1 = new AssassinoSith("Alvo 1", 20, 60);
            Guerreiro alvo2 = new AssassinoSith("Alvo 2", 25, 65);
            Guerreiro alvo3 = new AssassinoSith("Alvo 3", 30, 70);

            lado1.get(1).add(consular);

            lado2.get(0).add(alvo1);
            lado2.get(1).add(alvo2);
            lado2.get(2).add(alvo3);

            Combate combate = new Combate(lado1, lado2);
            combate.executarAtaques(lado1, lado2, true);

            System.out.println("Alvo 1 (adjacente): " + alvo1.getEnergia());
            System.out.println("Alvo 2 (principal): " + alvo2.getEnergia());
            System.out.println("Alvo 3 (adjacente): " + alvo3.getEnergia());
        }

        // =====================================================
        // TESTE 3 - JEDI GENERAL
        // =====================================================
        {
            System.out.println("\n========== TESTE 3: JEDI GENERAL ==========");

            ArrayList<ArrayList<Guerreiro>> lado1 = criarLadoArena();
            ArrayList<ArrayList<Guerreiro>> lado2 = criarLadoArena();

            JediGeneral general = new JediGeneral("General", 40, 80);

            Guerreiro alvo1 = new AssassinoSith("Alvo 1", 20, 60);
            Guerreiro alvo2 = new AssassinoSith("Alvo 2", 25, 65);

            general.receberDano(40);
            alvo1.receberDano(60);
            alvo2.receberDano(50);

            lado1.get(0).add(general);
            lado2.get(0).add(alvo1);
            lado2.get(0).add(alvo2);

            Combate combate = new Combate(lado1, lado2);

            System.out.println("Energia inicial: " + general.getEnergia());
            System.out.println("Dano inicial: " + general.getDanoDeAtaque());

            combate.executarAtaques(lado1, lado2, true);

            System.out.println("Após primeiro ataque:");
            System.out.println("Energia: " + general.getEnergia());
            System.out.println("Dano: " + general.getDanoDeAtaque());

            combate.executarAtaques(lado1, lado2, true);

            System.out.println("Após segundo ataque:");
            System.out.println("Energia: " + general.getEnergia());
            System.out.println("Dano: " + general.getDanoDeAtaque());
        }

        // =====================================================
        // TESTE 4 - CLONE MÉDICO
        // =====================================================
        {
            System.out.println("\n========== TESTE 4: CLONE MEDICO ==========");

            ArrayList<ArrayList<Guerreiro>> lado1 = criarLadoArena();
            ArrayList<ArrayList<Guerreiro>> lado2 = criarLadoArena();

            CloneMedico medico = new CloneMedico("Medico", 35, 70);
            Guerreiro aliado = new CloneComando("Aliado", 25, 75);
            Guerreiro inimigo = new AssassinoSith("Inimigo", 30, 65);

            aliado.receberDano(40);

            lado1.get(0).add(medico);
            lado1.get(0).add(aliado);
            lado2.get(0).add(inimigo);

            Combate combate = new Combate(lado1, lado2);

            System.out.println("Energia inicial do aliado: " + aliado.getEnergia());

            combate.executarAtaques(lado1, lado2, true);

            System.out.println("Energia final do aliado: " + aliado.getEnergia());
            System.out.println("Energia do inimigo: " + inimigo.getEnergia());
        }

        // =====================================================
        // TESTE 5 - CLONE COMANDO
        // =====================================================
        {
            System.out.println("\n========== TESTE 5: CLONE COMANDO ==========");

            ArrayList<ArrayList<Guerreiro>> lado1 = criarLadoArena();
            ArrayList<ArrayList<Guerreiro>> lado2 = criarLadoArena();

            CloneComando comando1 = new CloneComando("Comando 1", 25, 70);
            CloneComando comando2 = new CloneComando("Comando 2", 30, 75);
            CloneComando comando3 = new CloneComando("Comando 3", 28, 72);

            Guerreiro inimigo = new AssassinoSith("Inimigo", 35, 80);

            lado1.get(0).add(comando1);
            lado1.get(0).add(comando2);
            lado1.get(0).add(comando3);
            lado2.get(0).add(inimigo);

            Combate combate = new Combate(lado1, lado2);

            System.out.println("Energia inicial do inimigo: " + inimigo.getEnergia());

            combate.executarAtaques(lado1, lado2, true);

            System.out.println("Energia final do inimigo: " + inimigo.getEnergia());
        }

        // =====================================================
        // TESTE 6 - CLONE PESADO
        // =====================================================
        {
            System.out.println("\n========== TESTE 6: CLONE PESADO ==========");

            ArrayList<ArrayList<Guerreiro>> lado1 = TrabalhoStarWars.criarLadoArena();
            ArrayList<ArrayList<Guerreiro>> lado2 = TrabalhoStarWars.criarLadoArena();

            JediGeneral general = new JediGeneral("General", 30, 80);
            ClonePesado pesado = new ClonePesado("Pesado", 25, 100);

            AssassinoSith inimigoFila0 = new AssassinoSith("Sith 1", 25, 80);
            AssassinoSith inimigoFila1 = new AssassinoSith("Sith 2", 25, 80);

            // O inimigo da fila 1 começa com apenas 30 de energia.
            inimigoFila1.receberDano(70);

            // Montagem das filas
            lado1.get(0).add(general);
            lado1.get(1).add(pesado);

            lado2.get(0).add(inimigoFila0);
            lado2.get(1).add(inimigoFila1);

            Combate combate = new Combate(lado1, lado2);

            System.out.println("\nANTES DOS ATAQUES:");
            System.out.println("General: " + general.getEnergia());
            System.out.println("Clone Pesado: " + pesado.getEnergia());
            System.out.println("Inimigo fila 0: " + inimigoFila0.getEnergia());
            System.out.println("Inimigo fila 1: " + inimigoFila1.getEnergia());

            // Primeiro, o lado 1 ataca.
            combate.executarAtaques(lado1, lado2, true);

            System.out.println("\nDEPOIS DO ATAQUE DO LADO 1:");
            System.out.println("General: " + general.getEnergia());
            System.out.println("Clone Pesado: " + pesado.getEnergia());
            System.out.println("Inimigo fila 0: " + inimigoFila0.getEnergia());
            System.out.println("Inimigo fila 1: " + inimigoFila1.getEnergia());

            // Agora, o lado 2 responde.
            combate.executarAtaques(lado2, lado1, false);

            System.out.println("\nDEPOIS DA RESPOSTA DO LADO 2:");
            System.out.println("General: " + general.getEnergia());
            System.out.println("Clone Pesado: " + pesado.getEnergia());
            System.out.println("Inimigo fila 0: " + inimigoFila0.getEnergia());
            System.out.println("Inimigo fila 1: " + inimigoFila1.getEnergia());
        }

        System.out.println("\n========== TESTE 7: JEDI CONSULAR NAS EXTREMIDADES ==========");

        // Teste na fila 1
        ArrayList<ArrayList<Guerreiro>> ladoAdversario1 = TrabalhoStarWars.criarLadoArena();

        JediConsular consular1 = new JediConsular("Consular 1", 30, 70);

        AssassinoSith alvoFila1 = new AssassinoSith("Alvo fila 1", 25, 70);
        AssassinoSith alvoFila2 = new AssassinoSith("Alvo fila 2", 25, 70);
        AssassinoSith alvoFila3 = new AssassinoSith("Alvo fila 3", 25, 70);

        ladoAdversario1.get(0).add(alvoFila1);
        ladoAdversario1.get(1).add(alvoFila2);
        ladoAdversario1.get(2).add(alvoFila3);

        consular1.atacarEmArea(ladoAdversario1, 0);

        System.out.println("\nConsular na fila 1:");
        System.out.println("Alvo fila 1 (principal): " + alvoFila1.getEnergia());
        System.out.println("Alvo fila 2 (adjacente): " + alvoFila2.getEnergia());
        System.out.println("Alvo fila 3 (não atingido): " + alvoFila3.getEnergia());

        // Teste na fila 4
        ArrayList<ArrayList<Guerreiro>> ladoAdversario2 = TrabalhoStarWars.criarLadoArena();

        JediConsular consular2 = new JediConsular("Consular 2", 30, 70);

        AssassinoSith alvoFila4 = new AssassinoSith("Alvo fila 4", 25, 70);
        AssassinoSith alvoFila3b = new AssassinoSith("Alvo fila 3", 25, 70);
        AssassinoSith alvoFila2b = new AssassinoSith("Alvo fila 2", 25, 70);

        ladoAdversario2.get(3).add(alvoFila4);
        ladoAdversario2.get(2).add(alvoFila3b);
        ladoAdversario2.get(1).add(alvoFila2b);

        consular2.atacarEmArea(ladoAdversario2, 3);

        System.out.println("\nConsular na fila 4:");
        System.out.println("Alvo fila 4 (principal): " + alvoFila4.getEnergia());
        System.out.println("Alvo fila 3 (adjacente): " + alvoFila3b.getEnergia());
        System.out.println("Alvo fila 2 (não atingido): " + alvoFila2b.getEnergia());

        System.out.println("\n========== TESTE 8: CLONE COMANDO APÓS MORTE ==========");

        ArrayList<Guerreiro> filaAliada = new ArrayList<>();
        ArrayList<Guerreiro> filaAdversaria = new ArrayList<>();

        CloneComando comando1 = new CloneComando("Comando 1", 25, 70);
        CloneComando comando2 = new CloneComando("Comando 2", 25, 70);
        CloneComando comando3 = new CloneComando("Comando 3", 25, 70);

        AssassinoSith alvo1 = new AssassinoSith("Alvo 1", 25, 70);
        AssassinoSith alvo2 = new AssassinoSith("Alvo 2", 25, 70);

        filaAliada.add(comando1);
        filaAliada.add(comando2);
        filaAliada.add(comando3);

        filaAdversaria.add(alvo1);

        System.out.println("\nAntes da morte:");
        System.out.println("Clone Comando atrás: 2");
        System.out.println("Dano esperado: 56");

        comando1.atacar(filaAdversaria, filaAliada);

        System.out.println("Energia do primeiro alvo: " + alvo1.getEnergia());

        // Derrotando o segundo Clone Comando
        comando2.receberDano(100);
        Combate.processarDerrotados(filaAliada);

        filaAdversaria.clear();
        filaAdversaria.add(alvo2);

        System.out.println("\nDepois da morte:");
        System.out.println("Clone Comando atrás: 1");
        System.out.println("Dano esperado: 48");

        comando1.atacar(filaAdversaria, filaAliada);

        System.out.println("Energia do segundo alvo: " + alvo2.getEnergia());

        System.out.println("\n========== TESTE 9: CLONE PESADO CONTRA VÁRIOS ATACANTES ==========");

        ArrayList<ArrayList<Guerreiro>> lado1 = TrabalhoStarWars.criarLadoArena();
        ArrayList<ArrayList<Guerreiro>> lado2 = TrabalhoStarWars.criarLadoArena();

        ClonePesado pesado = new ClonePesado("Clone Pesado", 25, 100);

        AssassinoSith atacante1 = new AssassinoSith("Atacante 1", 25, 70);
        AssassinoSith atacante2 = new AssassinoSith("Atacante 2", 25, 70);

        lado1.get(1).add(pesado);

        lado2.get(0).add(atacante1);
        lado2.get(1).add(atacante2);

        Combate combate = new Combate(lado1, lado2);

        System.out.println("\nEnergia inicial do Clone Pesado: " + pesado.getEnergia());

        combate.executarAtaques(lado1, lado2, true);

        System.out.println("\nDepois do primeiro ataque:");
        System.out.println("Clone Pesado: " + pesado.getEnergia());

        combate.executarAtaques(lado2, lado1, false);

        System.out.println("\nDepois dos ataques adversários:");
        System.out.println("Clone Pesado: " + pesado.getEnergia());
        System.out.println("Atacante 1: " + atacante1.getEnergia());
        System.out.println("Atacante 2: " + atacante2.getEnergia());

        System.out.println("\n========== TESTE 10: DOIS CLONES PESADOS ==========");

        ArrayList<ArrayList<Guerreiro>> lado1Teste10 = TrabalhoStarWars.criarLadoArena();
        ArrayList<ArrayList<Guerreiro>> lado2Teste10 = TrabalhoStarWars.criarLadoArena();

        ClonePesado pesado1 = new ClonePesado("Clone Pesado 1", 25, 100);
        ClonePesado pesado2 = new ClonePesado("Clone Pesado 2", 25, 100);

        AssassinoSith inimigo1 = new AssassinoSith("Inimigo 1", 25, 70);
        AssassinoSith inimigo2 = new AssassinoSith("Inimigo 2", 25, 70);

        lado1Teste10.get(0).add(pesado1);
        lado1Teste10.get(1).add(pesado2);

        lado2Teste10.get(0).add(inimigo1);
        lado2Teste10.get(1).add(inimigo2);

        Combate combateTeste10 = new Combate(lado1Teste10, lado2Teste10);

        System.out.println("\nEnergias iniciais:");
        System.out.println("Clone Pesado 1: " + pesado1.getEnergia());
        System.out.println("Clone Pesado 2: " + pesado2.getEnergia());

        combateTeste10.executarAtaques(lado1Teste10, lado2Teste10, true);

        System.out.println("\nDepois da ativação das habilidades:");
        System.out.println("Inimigo 1: " + inimigo1.getEnergia());
        System.out.println("Inimigo 2: " + inimigo2.getEnergia());

        combateTeste10.executarAtaques(lado2Teste10, lado1Teste10, false);

        System.out.println("\nDepois da resposta adversária:");
        System.out.println("Clone Pesado 1: " + pesado1.getEnergia());
        System.out.println("Clone Pesado 2: " + pesado2.getEnergia());
        
        System.out.println("\n========== FIM DOS TESTES ==========");
    }    

*/