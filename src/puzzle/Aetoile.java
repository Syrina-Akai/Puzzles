package puzzle;

import java.util.*;

import static puzzle.Main.idBut;

public class Aetoile {
    private Stack<String> solution = new Stack<>();
    private PriorityQueue ouvert;
    private ArrayList<String> fermer = new ArrayList<>();
    private ArrayList<String> fils = new ArrayList<>();
    private ArrayList<Integer> peres = new ArrayList<>();

    public Stack<String> getSolution() {
        return solution;
    }

    public PriorityQueue getOuvert() {
        return ouvert;
    }

    public ArrayList<String> getFermer() {
        return fermer;
    }

    public ArrayList<String> getFils() {
        return fils;
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
        while (!nextMoves.isEmpty()) {//faire moves
            taquin1 = new Taquin(false);
            int move = nextMoves.remove();
            taquin1.nextMove(taquin, move);
            if (!fermer.contains(taquin1.id)) {//verifie la distance nodes==> ensemble ferme
                taquin1.depth = taquin.depth++;
                ouvert.add(taquin1); //ensemble ouvert
                fils.add(taquin1.id);
                peres.add(fils.indexOf(taquin.id));
            }
        }
    }

    public void solve(Taquin taquin, int heuristique) {
        if (heuristique == 1) {
            this.ouvert = new PriorityQueue(new CompareH1());
        } else {
            this.ouvert = new PriorityQueue(new CompareH2());
        }

        this.ouvert.add(taquin);
        this.fils.add(taquin.id);
        this.peres.add(-1);

        do {
            taquin = (Taquin)this.ouvert.remove();
            this.fermer.add(taquin.id);
            if (!taquin.id.equals(Main.idBut)) {
                this.appendNextMoves(taquin);
            }
        } while(!taquin.id.equals(Main.idBut));

        for(int index = this.fils.indexOf(Main.idBut); index != -1; index = (Integer)this.peres.get(index)) {
            System.out.println("l'index est : " + index);
            this.solution.push((String)this.fils.get(index));
        }

        System.out.println("la taille du path est : " + this.solution.size());
    }

    public void afficheSolution() {
        Taquin taquin = new Taquin(false);
        while (!solution.empty()) {
            taquin.idToTaquin(solution.pop());
            taquin.afficherTaquin();
        }
    }
}
