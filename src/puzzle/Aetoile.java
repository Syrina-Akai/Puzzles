package puzzle;

import java.util.*;
import java.util.stream.Collectors;

import static puzzle.Main.idBut;

public class Aetoile {
    private Stack<String> solution;
    private PriorityQueue<Taquin> ouvert;
    private HashSet<Taquin> fermer;
    private ArrayList<String> fils;
    private ArrayList<Integer> peres;

    public Aetoile(int heuristique) {
        solution = new Stack<>();
        fermer = new HashSet<>();
        fils = new ArrayList<>();
        peres = new ArrayList<>();
        if (heuristique == 1) {
            ouvert = new PriorityQueue<Taquin>(new CompareH1());
        } else {
            ouvert = new PriorityQueue<Taquin>(new CompareH2());
        }
    }

    public Stack<String> getSolution() {
        return solution;
    }

    public PriorityQueue<Taquin> getOuvert() {
        return ouvert;
    }

    public HashSet<Taquin> getFermer() {
        return fermer;
    }

    public ArrayList<String> getFermeId(){
        ArrayList<Taquin> ferme=new ArrayList<Taquin>(this.getFermer());
        return  ferme.stream()
                .map(Taquin::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> getOuvertId(){
        ArrayList<Taquin> ouvert=new ArrayList<Taquin>(this.getOuvert());
        return  ouvert.stream()
                .map(Taquin::getId)
                .collect(Collectors.toCollection(ArrayList::new));
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
            if (!fermer.contains(taquin1)) {//verifie la distance nodes==> ensemble ferme
                ouvert.add(taquin1); //ensemble ouvert
                fils.add(taquin1.id);
                peres.add(fils.indexOf(taquin.id));
            }
        }
    }

    public void solve(Taquin taquin) {
        ouvert.add(taquin);
        fils.add(taquin.id);
        peres.add(-1);

        do {
            taquin = ouvert.remove();
            fermer.add(taquin);
            if (!taquin.id.equals(Main.idBut)) {
                appendNextMoves(taquin);
            }
        } while(!taquin.id.equals(Main.idBut));
        System.out.println("fermer est : "+fermer);
        for(int index = fils.indexOf(Main.idBut); index != -1; index = (Integer)peres.get(index)) {
            solution.push((String)fils.get(index));
        }

        System.out.println("la taille du path est : " + solution.size());
    }

    public void afficheSolution() {
        Taquin taquin = new Taquin(false);
        while (!solution.empty()) {
            taquin.idToTaquin(solution.pop());
            taquin.afficherTaquin();
        }
    }
}
