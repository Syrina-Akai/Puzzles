package puzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Largeur {
    private ArrayList<String> ferme;
    private Queue<String> ouvert;
    private Stack<String> solution;
    private ArrayList<String> fils;
    private ArrayList<Integer> peres;


    public Largeur() {
        ferme = new ArrayList<>();
        ouvert = new LinkedList<>();
        solution = new Stack<>();
        fils = new ArrayList<>();
        peres = new ArrayList<>();
    }

    public Stack<String> getSolution() {
        return solution;
    }

    public void appendNextMoves(Taquin taquin) {
        Queue<Integer> nextMoves = new LinkedList<>();
        if (taquin.vide % 3 != 0) {//i-1 ==> à gauche
            nextMoves.add(taquin.vide - 1);
        }
        if (taquin.vide > 2) {//i-3 ==> en haut
            nextMoves.add(taquin.vide - 3);
        }
        if (taquin.vide % 3 != 2) {//i+1 ==> à droite
            nextMoves.add(taquin.vide + 1);
        }
        if (taquin.vide < 6) {//i+3 ==> en bas
            nextMoves.add(taquin.vide + 3);
        }
        Taquin taquin1;
        while (!nextMoves.isEmpty()) {//faire tous les moves
            taquin1 = new Taquin(false);
            int move = nextMoves.remove();
            taquin1.nextMove(taquin, move);
            if (!ferme.contains(taquin1.id)) {
                ouvert.add(taquin1.id);
                fils.add(taquin1.id);
                peres.add(fils.indexOf(taquin.id));
            }
        }
    }

    public void solve(Taquin taquin) {
        int maxDepth = 20;
        ouvert.add(taquin.id);
        fils.add(taquin.id);
        peres.add(-1);

        do {
            taquin = new Taquin(false);
            taquin.idToTaquin(ouvert.remove());
            if (!ferme.contains(taquin.id))
                ferme.add(taquin.id);
            if (!taquin.id.equals(Main.idBut))
                appendNextMoves(taquin);
        } while(!taquin.id.equals(Main.idBut));

        for(int index = fils.indexOf(Main.idBut); index != -1; index = peres.get(index)) {
            solution.push(fils.get(index));
        }

        System.out.print("Congrats, Solution Found: ");
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