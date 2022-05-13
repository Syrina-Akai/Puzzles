package puzzle.partie1;

import puzzle.Main;
import puzzle.Taquin;

import java.util.*;
import java.util.stream.Collectors;


public class Largeur {
    private HashSet<Taquin> ferme;
    private Queue<Taquin> ouvert;
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

    public Queue<Taquin> getOuvert() {
        return ouvert;
    }

    public ArrayList<String> getFils() {
        return fils;
    }

    public void appendNextMoves(Taquin taquin) {
        Queue<Integer> nextMoves = new LinkedList<>();
        if (taquin.getVide() % 3 != 0) {//i-1 ==> à gauche
            nextMoves.add(taquin.getVide() - 1);
        }
        if (taquin.getVide() > 2) {//i-3 ==> en haut
            nextMoves.add(taquin.getVide() - 3);
        }
        if (taquin.getVide() % 3 != 2) {//i+1 ==> à droite
            nextMoves.add(taquin.getVide() + 1);
        }
        if (taquin.getVide() < 6) {//i+3 ==> en bas
            nextMoves.add(taquin.getVide() + 3);
        }
        Taquin taquin1;
        while (!nextMoves.isEmpty()) {//faire tous les moves
            taquin1 = new Taquin(false);
            taquin1.setDepth(taquin.getDepth()+1);
            int move = nextMoves.remove();
            taquin1.nextMove(taquin, move);
            if (!ferme.contains(taquin1)) {
                ouvert.add(taquin1);
                fils.add(taquin1.id);
                peres.add(fils.indexOf(taquin.id));
            }
        }

    }

    public void solve(Taquin taquin, int maxDepth ) {
        taquin.setDepth(0);
        ouvert.add(taquin);
        fils.add(taquin.id);
        peres.add(-1);
        Taquin temp;

        do {
            taquin = new Taquin(false);
            temp = ouvert.remove();
            taquin.idToTaquin(temp.id);
            taquin.setDepth(temp.getDepth());
            ferme.add(taquin);
            if (!taquin.id.equals(Main.idBut)){
                appendNextMoves(taquin);
            }
        } while(!taquin.id.equals(Main.idBut) && taquin.getDepth()<=maxDepth);

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