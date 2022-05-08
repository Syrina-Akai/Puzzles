package puzzle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class GA {

    ArrayList<Chromosome> populations= new ArrayList<>();
    ArrayList<Chromosome> fitessPopulations= new ArrayList<>();

    Taquin root;

    public GA(Taquin root) {
        this.root = root;
        initialisePopulation();
    }

    public boolean isDoable(Taquin taquin, double move){
        if(move<0.26){//up 0.254
            if(taquin.vide <= 2){
                return false;
            }else {
                taquin.nextMove(taquin, taquin.vide - 3);
            }
        }
        if(move>=0.26 && move<0.51){//right
            if(taquin.vide % 3 == 2 ){
                return false;
            }
            else {
                taquin.nextMove(taquin, taquin.vide + 1);
            }
        }
        if(move>=0.51 && move<0.76){//down
            if(taquin.vide >= 6){
                return false;
            }
            else {
                taquin.nextMove(taquin, taquin.vide + 3);
            }
        }
        if(move>=0.76){//left
            if(taquin.vide % 3 == 0){
                return false;
            }
            else {
                taquin.nextMove(taquin, taquin.vide - 1);

            }
        }
        return true;
    }

    public void affichageMoves(ArrayList<Double> moves){
        ArrayList<String> printedMoves= new ArrayList<>();
        for (double move: moves) {
            if(move < 0.26){
                printedMoves.add("Up");
            }
            if(move >= 0.26 && move < 0.51){
                printedMoves.add("Right");
            }
            if(move >= 0.51 && move < 0.76){
                printedMoves.add("Down");
            }
            if(move >= 0.76){
                printedMoves.add("Left");
            }
        }
        System.out.println(printedMoves);
    }

    public void affichageMove(double move ){
        if(move < 0.26){
            System.out.println("Current move : Up");
        }
        if(move >= 0.26 && move < 0.51){
            System.out.println("Current move : Right");
        }
        if(move >= 0.51 && move < 0.76){
            System.out.println("Current move : Down");
        }
        if(move >= 0.76){
            System.out.println("Current move : Left");
        }
    }

    public ArrayList<Double> randomMoves(Taquin taquin){
        ArrayList<Double> moves= new ArrayList<>();
        taquin.afficherTaquin();
        Random rand = new Random();
        double move;
        for (int i = 1; i < 8; i++) {
            boolean test = true;
            do {
                move = rand.nextDouble(1.0);
                if (move < 0.26) {//up 0.254
                    if (taquin.vide > 2) {
                        taquin.nextMove(taquin, taquin.vide - 3);
                        taquin.afficherTaquin();
                        test = true;
                    } else {
                        test = false;
                    }
                }
                if (move >= 0.26 && move < 0.51) {//right
                    if (taquin.vide % 3 != 2) {
                        taquin.nextMove(taquin, taquin.vide + 1);
                        taquin.afficherTaquin();
                        test = true;
                    } else {
                        test = false;
                    }
                }
                if (move >= 0.51 && move < 0.76) {//down
                    if (taquin.vide < 6) {
                        taquin.nextMove(taquin, taquin.vide + 3);
                        taquin.afficherTaquin();
                        test = true;
                    } else {
                        test = false;
                    }
                }
                if (move >= 0.76) {//left
                    if (taquin.vide % 3 != 0) {
                        taquin.nextMove(taquin, taquin.vide - 1);
                        taquin.afficherTaquin();
                        test = true;
                    } else {
                        test = false;
                    }
                }
            } while (!test);
            affichageMove(move);
            moves.add(move);
        }
        return moves;
    }

    public void initialisePopulation() {
        ArrayList<Double> moves;
        Chromosome chromosome;
        Random rand = new Random();
        System.out.println("dkhelna initialisePopulation");

        if (root.vide > 2) {//i-3 ==> en haut
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.26));
            taquin.nextMove(taquin, taquin.vide - 3);
            moves.addAll(randomMoves(taquin));
            affichageMoves(moves);
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide % 3 != 2) {//i+1 ==> à droite
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.51 - 0.26) + 0.26);
            taquin.nextMove(taquin, taquin.vide + 1 );
            moves.addAll(randomMoves(taquin));
            affichageMoves(moves);
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide < 6) {//i+3 ==> en bas
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.76 - 0.51) + 0.51);
            taquin.nextMove(taquin, taquin.vide + 3);
            moves.addAll(randomMoves(taquin));
            affichageMoves(moves);
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide % 3 != 0) {//i-1 ==> à gauche
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(1.0 - 0.76) + 0.76);
            taquin.nextMove(taquin, taquin.vide - 1);
            moves.addAll(randomMoves(taquin));
            affichageMoves(moves);
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
    }


    public void fit(){
        //on va le tester
        Taquin taquin= new Taquin(root.id);
        CompareH1 compareH1= new CompareH1();
        boolean b = true;

        for (Chromosome chromosome : this.populations) {
            b=true;
            taquin= new Taquin(root.id);
            for (double move : chromosome.getMoves()) {
                if(move<=0.25){//up 0.254
                    if(taquin.vide <= 2){
                        System.out.println(taquin.vide );
                        System.out.println("move is : "+move+" ==> "+chromosome.getMoves().indexOf(move));
                        affichageMove(move);
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide - 3);
                }
                if(move>0.25 && move<=0.50){//right
                    if(taquin.vide % 3 == 2 ){
                        System.out.println(taquin.vide );
                        System.out.println("move is : "+move+" ==> "+chromosome.getMoves().indexOf(move));
                        affichageMove(move);
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide + 1);
                }
                if(move>0.5 && move<=0.75){//down
                    if(taquin.vide >= 6){
                        System.out.println(taquin.vide );
                        System.out.println("move is : "+move+" ==> "+chromosome.getMoves().indexOf(move));
                        affichageMove(move);
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide + 3);
                }
                if(move>0.75){//left
                    if(taquin.vide % 3 == 0){
                        System.out.println(taquin.vide );
                        System.out.println("move is : "+move+" ==> "+chromosome.getMoves().indexOf(move));
                        affichageMove(move);
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide - 1);
                }
                // add check of taquin but
            }
            if (b) {
                System.out.println("its true !! ");
                chromosome.setFitness(compareH1.distanceEtat(taquin));
            }
        }
    }



    public ArrayList<Chromosome> crossover(Chromosome parent1, Chromosome parent2){
        // the 2 children of the crossover
        ArrayList<Chromosome> children= new ArrayList<>();

        int indice1= parent1.getMoves().size()/2;
        int indice2= parent2.getMoves().size()/2;

        ArrayList<Double> moves1 = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves1.addAll(parent2.getMoves().subList(indice2, parent2.getMoves().size()));
        Chromosome child1=new Chromosome(moves1);
        if(child1.getFitness() !=0)
            children.add(child1);

        moves1 = new ArrayList<>(parent2.getMoves().subList(0, indice2));
        moves1.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child1=new Chromosome(moves1);
        if(child1.getFitness() != 0)
            children.add(child1);

        return children;
    }
}
