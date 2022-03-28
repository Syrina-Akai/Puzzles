package puzzle;

import java.util.*;


public class Largeur {
    private HashSet<Taquin> ferme;
    private Queue<String> ouvert;
    private Stack<String> solution;
    private ArrayList<String> fils;
    private ArrayList<Integer> peres;


    public Largeur() {
        ferme = new HashSet<>();
        ouvert = new LinkedList<>();
        solution = new Stack<>();
        fils = new ArrayList<>();
        peres = new ArrayList<>();
    }

    public Stack<String> getSolution() {
        return solution;
    }

    public HashSet<Taquin> getFermer() {
        return ferme;
    }

    public Queue<String> getOuvert() {
        return ouvert;
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
        while (!nextMoves.isEmpty()) {//faire tous les moves
            taquin1 = new Taquin(false);
            int move = nextMoves.remove();
            taquin1.nextMove(taquin, move);
            if (!ferme.contains(taquin1)) {
                ouvert.add(taquin1.id);
                fils.add(taquin1.id);
                peres.add(fils.indexOf(taquin.id));
            }
        }
    }

    public void solve(Taquin taquin, int maxDepth ) {
        taquin.depth=0;
        ouvert.add(taquin.id);
        fils.add(taquin.id);
        peres.add(-1);
        do {
            taquin = new Taquin(false);
            taquin.idToTaquin(ouvert.remove());
            ferme.add(taquin);
            if (!taquin.id.equals(Main.idBut))
                appendNextMoves(taquin);
        } while(!taquin.id.equals(Main.idBut) && taquin.depth<=maxDepth );

        taquin.idToTaquin(Main.idBut);
        if(ferme.contains(taquin)){
            for(int index = fils.indexOf(Main.idBut); index != -1; index = peres.get(index)) {
                solution.push(fils.get(index));
            }
            System.out.print("Congrats, Solution Found ");
            return;
        }
        System.out.println("Solution not found");

    }

    public void afficheSolution() {
        Taquin taquin = new Taquin(false);
        while (!solution.empty()) {
            taquin.idToTaquin(solution.pop());
            taquin.afficherTaquin();
        }
    }

}