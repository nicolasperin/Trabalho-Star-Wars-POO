package trabalhostarwars;

import java.util.ArrayList;

public class TrabalhoStarWars {
    public static void main(String[] args) {
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

        exibirLadoArena(lado1);
    }
    
    //metodo estatico pq é da classe
    //move o primeiro guerreiro para o final da fila
    public static void rotacionarFila(ArrayList<Guerreiro> fila){
        //verificar se lista ta vazia, se tiver retorna antes de rotacionar
        if(fila.isEmpty()){
            return;
        }
        else{
            Guerreiro rotacao = fila.remove(0); //pega o primeiro da fila (indice 0)
            fila.add(rotacao); //joga pro final (adicionando dnv)
        }
    }
    
    //metodo estatico pq é da classe
    //remove os guerreiros com energia menor ou igual a zero
    public static void removerDerrotado(ArrayList<Guerreiro> fila){
        //verifico se a lista ta vazia
        if(fila.isEmpty()){
            return;
        }
        else{
            for(int i = (fila.size() - 1); i >= 0; i--){ //percorrer a lista de tras pra frente pra nao pular nenhum guerreiro
                //chamar o metodo estaDerrotado pra cada guerreiro da lista, se for true remove
                Guerreiro temp = fila.get(i);                
                if(temp.estaDerrotado() == true){
                    fila.remove(i);
                }        
            }
        }
    }
    
    //metodo estatico pq é da classe
    //cria um lado com quatro filas vazias e independentes
    public static ArrayList<ArrayList<Guerreiro>> criarLadoArena(){
        ArrayList<ArrayList<Guerreiro>> lado = new ArrayList<>();
        
        for(int i = 0; i < 4; i++){
            lado.add(new ArrayList<>());
        }
        return(lado);
    }
    
    //metodo estatico pq é da classe
    //percorre as filas e mostra seus guerreiros (qykaquer lado)
    //i = fila ; j = guerreiro na fila
    public static void exibirLadoArena(ArrayList<ArrayList<Guerreiro>> lado){
        for (int i = 0; i < lado.size(); i++) {
            System.out.println(lado.get(i).size());
            if(lado.get(i).isEmpty()){
                System.out.println("Imprimindo da lista " + i + ": Vazia");
            }
            else{
                for(int j = 0; j < lado.get(i).size(); j++){ //laco que percorrer a fila completa
                    Guerreiro temp = lado.get(i).get(j); //acessa a lista do indice e imprime o guerreiro da posicao j
                    System.out.println("Imprimindo da lista " + i + ": " + temp.getNome());
                }
            }
        }
    }
    
    public static boolean ladoDaArenaVazio(ArrayList<ArrayList<Guerreiro>> lado){
        for(int i = 0; i < lado.size(); i++){
            if(!(lado.get(i).isEmpty())){
                return (false);
            }
        }
        return(true); 
    }
}