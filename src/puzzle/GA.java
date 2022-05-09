package puzzle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import static puzzle.Main.idTest;

public class GA {

    ArrayList<Chromosome> populations= new ArrayList<>();
    ArrayList<Chromosome> fitessPopulations= new ArrayList<>();
    ArrayList<Chromosome> newGeneration = new ArrayList<>();
    Chromosome solution=null;
    int generation=0;
    Taquin root;

    public GA(Taquin root) {
        this.root = root;
        initialisePopulation();
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
                        test = true;
                    } else {
                        test = false;
                    }
                }
                if (move >= 0.26 && move < 0.51) {//right
                    if (taquin.vide % 3 != 2) {
                        taquin.nextMove(taquin, taquin.vide + 1);
                        test = true;
                    } else {
                        test = false;
                    }
                }
                if (move >= 0.51 && move < 0.76) {//down
                    if (taquin.vide < 6) {
                        taquin.nextMove(taquin, taquin.vide + 3);
                        test = true;
                    } else {
                        test = false;
                    }
                }
                if (move >= 0.76) {//left
                    if (taquin.vide % 3 != 0) {
                        taquin.nextMove(taquin, taquin.vide - 1);
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
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide % 3 != 2) {//i+1 ==> à droite
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.51 - 0.26) + 0.26);
            taquin.nextMove(taquin, taquin.vide + 1 );
            moves.addAll(randomMoves(taquin));
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide < 6) {//i+3 ==> en bas
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(0.76 - 0.51) + 0.51);
            taquin.nextMove(taquin, taquin.vide + 3);
            moves.addAll(randomMoves(taquin));
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
        if (root.vide % 3 != 0) {//i-1 ==> à gauche
            Taquin taquin = new Taquin(root.id);
            moves = new ArrayList<>();
            moves.add(rand.nextDouble(1.0 - 0.76) + 0.76);
            taquin.nextMove(taquin, taquin.vide - 1);
            moves.addAll(randomMoves(taquin));
            chromosome = new Chromosome(moves);
            this.populations.add(chromosome);
        }
    }

    public void fitness(ArrayList<Chromosome> populations){
        fitessPopulations= new ArrayList<>();
        for (Chromosome chromosome: populations ) {
            chromosome.isDoable();
            if(chromosome.getFitness()!=-1){
                fitessPopulations.add(chromosome);
                fitessPopulations.sort(new SortChromosome());
            }
        }
    }

    public ArrayList<Chromosome> crossover1(Chromosome parent1, Chromosome parent2){
        // the 2 children of the crossover
        ArrayList<Chromosome> children= new ArrayList<>();

        int indice1= parent1.getMoves().size()/2;
        int indice2= parent2.getMoves().size()/2;

        ArrayList<Double> moves = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves.addAll(parent2.getMoves().subList(indice2, parent2.getMoves().size()));
        Chromosome child=new Chromosome(moves);
        child.isDoable();
        if(child.getFitness() !=-1){
            children.add(child);
        }

        moves = new ArrayList<>(parent2.getMoves().subList(0, indice2));
        moves.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child=new Chromosome(moves);
        child.isDoable();
        if(child.getFitness() !=-1){
            children.add(child);
        }

        return children;
    }

    public ArrayList<Chromosome> crossover2(Chromosome parent1, Chromosome parent2){
        // the 2 children of the crossover
        ArrayList<Chromosome> children= new ArrayList<>();
        Chromosome fitess;
        Chromosome lessFit;
        if(parent1.getFitness()> parent2.getFitness()){
            fitess=new Chromosome(parent1.getMoves());
            lessFit=new Chromosome(parent2.getMoves());
        }else {
            fitess=new Chromosome(parent2.getMoves());
            lessFit=new Chromosome(parent1.getMoves());
        }


        int indice1= fitess.getMoves().size()*3/4;
        int indice2= lessFit.getMoves().size()/2;

        ArrayList<Double> moves = new ArrayList<>(fitess.getMoves().subList(0, indice1));
        moves.addAll(lessFit.getMoves().subList(indice2, lessFit.getMoves().size()));
        Chromosome child=new Chromosome(moves);
        child.isDoable();
        if(child.getFitness() !=-1){
            children.add(child);
        }

        moves = new ArrayList<>(lessFit.getMoves().subList(0, indice2));
        moves.addAll(fitess.getMoves().subList(indice1, fitess.getMoves().size()));
        child=new Chromosome(moves);
        child.isDoable();
        if(child.getFitness() !=-1){
            children.add(child);
        }

        return children;
    }

    public boolean isSolution(ArrayList<Chromosome> populations){
        for (Chromosome chromosome:populations) {
            if(chromosome.getFitness()==0){
                solution=chromosome;
                return true;
            }
        }
        return false;
    }

    public void generateSolution(){
        //1-the random generation of the first population is done in the constructor
        while (!isSolution(this.populations) && this.populations.size()>0 && generation<=10){
            System.out.println("generation : "+generation);
            this.fitness(this.populations);//2-test of the population and evoluate it
            //3-the crossover
            for (int i = 0; i < this.fitessPopulations.size()-1; i++) {
                for (int j =i+1; j<this.fitessPopulations.size(); j++){
                    newGeneration.addAll(crossover1(fitessPopulations.get(i), fitessPopulations.get(j)));
                }
            }
            generation++;
            //4-test and fitness of the new generation
            this.fitness(this.newGeneration);
            this.populations= new ArrayList<>();
            this.populations.addAll(this.newGeneration);
        }
        if(solution!=null && this.populations.size()>0){
            System.out.println("CONGRATS ! ");
            solution.affichageMoves();
        }else{
            System.out.println("Solution not found :/ ");
        }
    }

}
