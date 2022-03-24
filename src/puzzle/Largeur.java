package puzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static puzzle.Main.idBut;

public class Largeur {
    private ArrayList<String> nodes;
    private ArrayList<Integer> indexParents;
    private ArrayList<Integer> depth;
    private Stack<String> solution;

    public Stack<String> getSolution() {
        return solution;
    }

    public Largeur() {
        this.nodes = new ArrayList<>();
        this.indexParents = new ArrayList<>();
        this.depth = new ArrayList<>();
        solution = new Stack<>();
    }

    public void appendNextMoves(Taquin taquin, int indexP, int parentDepth) {
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
            if (!nodes.contains(taquin1.id)) {
                nodes.add(taquin1.id);
                indexParents.add(indexP);
                this.depth.add(parentDepth + 1);
            }
        }
    }

    public void solve(Taquin root) {
        int maxDepth = 20;
        this.nodes.add(root.id);
        this.indexParents.add(-1);
        this.depth.add(0);
        Taquin taquin = root;
        int index = 0;

        while(!this.nodes.contains(Main.idBut) && index < this.nodes.size() && (Integer)this.depth.get(index) < maxDepth) {
            this.appendNextMoves(taquin, index, (Integer)this.depth.get(index));
            ++index;
            taquin = new Taquin(false);
            if (index < this.nodes.size()) {
                taquin.idToTaquin((String)this.nodes.get(index));
            }
        }

        if (this.nodes.contains(Main.idBut)) {
            for(index = this.nodes.indexOf(Main.idBut); index != -1; index = (Integer)this.indexParents.get(index)) {
                this.solution.push((String)this.nodes.get(index));
            }

            System.out.println("CONGRATS! Solution:");
        } else {
            System.out.println("Game Over ! Solution not found at depth" + maxDepth);
        }

    }

    public void afficheSolution() {
        Taquin taquin = new Taquin(false);
        while (!solution.empty()) {
            taquin.idToTaquin(solution.pop());
            taquin.afficherTaquin();
        }
    }

}