package puzzle.partie1;

import puzzle.Taquin;

import java.util.*;
import java.util.stream.Collectors;

public class Profondeur {
    private ArrayList<String> nodes;
    private ArrayList<Integer> indexParents;
    private ArrayList<Integer> depth;
    private Stack<String > solution;
    private HashSet<Taquin> ferme;

    private int maxDepth;
    private String idBut = "123804765";
    private boolean found = false; //boolean that tells us if a solution has been found or not to stop recursivity

    public Stack<String> getSolution() {
        return solution;
    }

    public HashSet<Taquin> getFerme() {
        return ferme;
    }

    public ArrayList<String> getFermeId(){
        ArrayList<Taquin> ferme=new ArrayList<Taquin>(this.getFerme());
        return  ferme.stream()
                .map(Taquin::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public Profondeur() {
        this.nodes = new ArrayList<>();
        this.indexParents = new ArrayList<>();
        this.depth = new ArrayList<>(); // to remove
        maxDepth = 0;
        this.solution = new Stack<>(); // la solution
        this.ferme = new HashSet<>();
    }
    public void generateChildren(Taquin taquin, int indexP, int parentDepth){ //parentDepth consider changing
        Queue<Integer> allMoves = new LinkedList<>(); //creation de tous les fils
        if(taquin.getVide()%3!=0){//i-1 ==> à gauche
            allMoves.add(taquin.getVide()-1);
        }
        if(taquin.getVide()>2){//i-3 ==> en haut
            allMoves.add(taquin.getVide()-3);
        }
        if(taquin.getVide()%3!=2){//i+1 ==> à droite
            allMoves.add(taquin.getVide()+1);
        }
        if(taquin.getVide()<6){//i+3 ==> en bas
            allMoves.add(taquin.getVide()+3);
        }

        while(!allMoves.isEmpty()){ // on boucle pour la recursivité dans tous les fils
            Taquin taquin1; // on cré les fils au fur et a mesure de la boucle
            taquin1 = new Taquin(false);
            taquin1.setDepth(parentDepth+1);
            int move = allMoves.remove();
            taquin1.nextMove(taquin, move);

            //if never existed, create
            int min = 999; // we can only create if it was never created before, and if it did, must be of higher depth
            if(nodes.contains(taquin1.id)){
                for(int i = 0; i<nodes.size();i++){
                    if(taquin1.id.equals(nodes.get(i))){
                        if(min>depth.get(i)){
                            min = depth.get(i);
                        }
                    }
                }
            }

            //here min contains the smallest depth, if it never existed it will be 999
            if (parentDepth+1 < min && parentDepth+1 <= maxDepth) { // creation conditions
                //first cond is already exists, different depths
                //second is to control the max depth
                nodes.add(taquin1.id); //we add it
                indexParents.add(indexP);
                this.depth.add(parentDepth + 1);
                //most left child

                if (taquin1.id.equals(idBut)) {
                    found = true; //if we found the result, we change the boolean to stop the recursive loop
                    ferme.add(taquin1);

                }

                if(!found){
                    // only go through recursivity if has never been found
                    ferme.add(taquin1);

                    generateChildren(taquin1, nodes.indexOf(taquin1.id), parentDepth + 1);
                }

            }
        }
    }

    public void solve(Taquin root, int Depth) {
        // put idBut and maxDepth as input parameters
        this.maxDepth = Depth;
        nodes.add(root.id);
        indexParents.add(-1);
        depth.add(0);
        Taquin taquin = root;
        ferme.add(root);
        //starts the tree generation
        if (!nodes.contains(idBut)) {
            generateChildren(taquin, 0, depth.get(0));
        }
        // Solution found
        if (found) {
            int index = nodes.indexOf(idBut);
            while (index != -1) {
                solution.push(nodes.get(index));
                index = indexParents.get(index);
            }
            System.out.println("CONGRATS! Solution:");
            //afficheSolution();
        }
        // Solution not found
        else
            System.out.println("Game Over ! Solution not found at depth" + maxDepth);
    }

    public void afficheSolution() {
        Taquin taquin = new Taquin(false);
        while (!solution.empty()){
            taquin.idToTaquin(solution.pop());
            taquin.afficherTaquin();
        }
        /*System.out.println("ferme");
        if(!ferme.isEmpty()){
            System.out.println(ferme);
        }*/
    }
    //creates child of child in a loop until it reaches the max depth
    //if solution wasn't found, go back to the parent, and create next child
    //if all children were created, and solution wasn't found, then go back to parent and create next child
    //if the child that was created is identical to another taquin that was already created before we check its depth
    //if current depth is smaller, we create and continue, if it's bigger than the already created one, we don't create the child
    //also if current taquin's depth is the same as an already identical created one, we don't create it

}