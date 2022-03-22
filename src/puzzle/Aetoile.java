package puzzle;
import java.util.*;

import static puzzle.Main.idBut;

public class Aetoile {
    Stack<String> solution = new Stack<>();
    PriorityQueue ouvert ;
    ArrayList<String> fermer = new ArrayList<>();
    ArrayList<String> fils=new ArrayList<>();
    ArrayList<Integer> peres=new ArrayList<>();

    public void appendNextMoves(Taquin taquin) {
        Queue<Integer> nextMoves = new LinkedList<>();
        if(taquin.vide%3!=0){//i-1 ==> à gauche
            nextMoves.add(taquin.vide-1);
        }
        if(taquin.vide>2){//i-3 ==> en haut
            nextMoves.add(taquin.vide-3);
        }
        if(taquin.vide%3!=2){//i+1 ==> à droite
            nextMoves.add(taquin.vide+1);
        }
        if(taquin.vide<6){//i+3 ==> en bas
            nextMoves.add(taquin.vide+3);
        }
        Taquin taquin1;
        while (!nextMoves.isEmpty()) {//faire moves
            taquin1 = new Taquin( false);
            int move = nextMoves.remove();
            taquin1.nextMove(taquin, move);
            if(!fermer.contains(taquin1.id) ){//verifie la distance nodes==> ensemble ferme
                taquin1.depth=taquin.depth++;
                ouvert.add(taquin1); //ensemble ouvert
                //parent fils
                fils.add(taquin1.id);
                peres.add(fils.indexOf(taquin.id));
            }
        }
    }

    public void solve(Taquin taquin, int heuristique) {

        if(heuristique==1){
            ouvert =new PriorityQueue(new CompareH1());
        }else
            ouvert =new PriorityQueue(new CompareH2());
        ouvert.add(taquin);
        fils.add(taquin.id);
        peres.add(-1);
        do {
            taquin= (Taquin) ouvert.remove();
            fermer.add(taquin.id);
            if(!taquin.id.equals(idBut))
                appendNextMoves(taquin);
            System.out.println(fermer);
        }while(!taquin.id.equals(idBut));
        System.out.println("fermer est : "+fermer);
        System.out.println("ouvert est : "+ouvert);
        System.out.println("fils est : "+fils);
        System.out.println("index est : "+peres);
        int index=fils.indexOf(idBut);
        while(index!=-1){
            System.out.println("l'index est : "+index);
            solution.push(fils.get(index));
            index=peres.get(index);

        }
        System.out.println("la taille du path est : "+solution.size());
        afficheSolution();
    }

    public void afficheSolution() {
        Taquin taquin = new Taquin(false);
        while (!solution.empty()) {
            taquin.idToTaquin(solution.pop());
            taquin.afficherTaquin();
        }
    }
}
