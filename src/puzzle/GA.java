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

    public void initialisePopulation() {
        ArrayList<Double> moves;
        Chromosome chromosome;
        Random rand = new Random();
        if (root.vide > 2) {//i-3 ==> en haut
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.26));
            for (int i = 1; i < 8; i++) {
                moves.add(rand.nextDouble(1.0));
            }
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide % 3 != 2) {//i+1 ==> à droite
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.51 - 0.26) + 0.26);
            for (int i = 1; i < 8; i++) {
                moves.add(rand.nextDouble(1.0));
            }
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide < 6) {//i+3 ==> en bas
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.76 - 0.51) + 0.51);
            for (int i = 1; i < 8; i++) {
                moves.add(rand.nextDouble(1.0));
            }
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide % 3 != 0) {//i-1 ==> à gauche
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(1.0 - 0.76) + 0.76);
            for (int i = 1; i < 8; i++) {
                moves.add(rand.nextDouble(1.0));
            }
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

            for (double move : chromosome.getMoves()) {
                if(move<0.26){//up 0.254
                    if(taquin.vide <= 2){
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide - 3);
                }
                if(move>=0.26 && move<0.51){//right
                    if(taquin.vide % 3 == 2 ){
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide + 1);
                }
                if(move>=0.51 && move<0.76){//down
                    if(taquin.vide >= 6){
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide + 3);
                }
                if(move>=0.76){//left
                    if(taquin.vide % 3 == 0){
                        b = false;
                        break;
                    }
                    taquin.nextMove(taquin, taquin.vide - 1);
                }
                // add check of taquin but
            }
            if (b) {
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
