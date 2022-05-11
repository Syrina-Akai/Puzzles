package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GA {

    ArrayList<Chromosome> population = new ArrayList<>();
    //ArrayList<Chromosome> fittestPopulation = new ArrayList<>();
    //ArrayList<Chromosome> newGeneration = new ArrayList<>();
    Chromosome solution = null;
    int generation = 0;
    int chromosomeSize = 0;
    Taquin root;

    public GA(Taquin root) {
        this.root = root;

        chromosomeSize = new CompareH1().distanceEtat(root);
        System.out.println("la taille est : "+chromosomeSize);


        initialisePopulation();
    }

    public void initialisePopulation() {
        population = new ArrayList<>();
        ArrayList<Double> moves;
        Chromosome chromosome;
        Random rand = new Random();
        double move;
        int populationSizePerMove = 100;
        // The size of the chromosome is the Manhattan distance of the root taquin
        if (root.vide > 2) {//i-3 ==> en haut
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add(rand.nextDouble(0.26));
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = rand.nextDouble(1.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
        if (root.vide % 3 != 2) {//i+1 ==> à droite
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add(rand.nextDouble(0.51 - 0.26) + 0.26);
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = rand.nextDouble(1.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
        if (root.vide < 6) {//i+3 ==> en bas
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add(rand.nextDouble(0.76 - 0.51) + 0.51);
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = rand.nextDouble(1.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
        if (root.vide % 3 != 0) {//i-1 ==> à gauche
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add(rand.nextDouble(1.0 - 0.76) + 0.76);
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = rand.nextDouble(1.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
    }

    public void fit() {
        ArrayList<Chromosome> fittestPopulation = new ArrayList<>();
        for (Chromosome chromosome : this.population) {
            if (chromosome.isDoable()) {
                fittestPopulation.add(chromosome);
            }
        }
        fittestPopulation.sort(new SortChromosome());
        if (fittestPopulation.size() > chromosomeSize*2) {
            this.population = new ArrayList<>(fittestPopulation.subList(0, chromosomeSize*2));
        }
        else {
            this.population = new ArrayList<>();
            this.population.addAll(fittestPopulation);
        }
        System.out.println(("population size: " + this.population.size()));
    }

    public ArrayList<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        // the 2 children of the crossover
        ArrayList<Chromosome> children = new ArrayList<>();
        int indice1 = (parent1.getMoves().size() / 2) + 1;
        //int indice2 = parent2.getMoves().size() / 2;
        ArrayList<Double> moves;

        moves = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves.addAll(parent2.getMoves().subList(indice1, parent2.getMoves().size()));
        Chromosome child = new Chromosome(moves);
        child.isDoable();
        /*if (child.getFitness() != -1) {
            children.add(child);
        }*/
        children.add(child);

        moves = new ArrayList<>(parent2.getMoves().subList(0, indice1));
        moves.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child = new Chromosome(moves);
        child.isDoable();
        /*if (child.getFitness() != -1) {
            children.add(child);
        }*/
        children.add(child);

        return children;
    }

    public void mutatate() {
        for (Chromosome chromosome : this.population) {
            Random rand = new Random();
            ArrayList<Double> chromosomeMoves = chromosome.getMoves();
            int indice = rand.nextInt(chromosomeMoves.size());
            double oldMove = chromosomeMoves.get(indice);
            double newMove = 0.0;

            // To make sure that the new move is different from the old one
            if (oldMove < 0.26) {
                do {
                    newMove = rand.nextDouble(1.0);
                } while (newMove < 0.26);
            }
            if (oldMove >= 0.26 && oldMove < 0.51) {
                do {
                    newMove = rand.nextDouble(1.0);
                } while (newMove >= 0.26 && newMove < 0.51);
            }
            if (oldMove >= 0.51 && oldMove < 0.76) {
                do {
                    newMove = rand.nextDouble(1.0);
                } while (newMove >= 0.51 && newMove < 0.76);
            }
            if (oldMove >= 0.76) {
                do {
                    newMove = rand.nextDouble(1.0);
                } while (newMove >= 0.76);
            }

            chromosomeMoves.set(indice, newMove);
            chromosome.setMoves(chromosomeMoves);
        }
    }

    public void generateSolution() {
        System.out.println("la taille de la solution " + chromosomeSize);
        ArrayList<Chromosome> newGeneration;
        SortChromosome sortChromosome = new SortChromosome();
        int maxGenerations = 1000;
        int chances=0;
        boolean noSolution=isSolution(this.population);
        while (!noSolution && this.population.size() > 0 && generation <= maxGenerations && chances<=9) {
            this.fit();
            System.out.println("Chance : "+chances);
            System.out.println("Generation: " + generation);
            System.out.println("Population: " + this.population + "\n");
            // The crossover
            newGeneration = new ArrayList<>();
            for (int i = 0; i < this.population.size(); i++) {
                for (int j = i + 1; j < this.population.size(); j++) {
                    //System.out.println("i : " + i + ", j : " + j);
                    newGeneration.addAll(crossover(population.get(i), population.get(j)));
                }
            }
            generation++;
            //4-test and fitness of the new generation
            this.population = new ArrayList<>();
            this.population.addAll(newGeneration);
            noSolution=isSolution(this.population);
            // So there will be no mutation to the last population cuz makanech fitting now
            if (this.generation < maxGenerations && !noSolution)
                this.mutatate();
            if(this.population.size()<=1 || (generation == maxGenerations+1 && !noSolution)){
                chances++;
                this.chromosomeSize+=2;
                initialisePopulation();
                generation=0;
            }
        }

        if (solution != null && this.population.size() > 0) {
            System.out.println("CONGRATS ! ");
            solution.affichageMoves();
        } else {
            if(chances==9){
                System.out.println("got all your chances ! get off :) ");
            }
            System.out.println("Solution not found :/ ");
        }
        System.out.println("chromosome size is : "+chromosomeSize);
    }


    public ArrayList<Double> randomMoves(Taquin taquin) {
        ArrayList<Double> moves = new ArrayList<>();
        Random rand = new Random();
        double move;
        for (int i = 1; i < chromosomeSize; i++) {
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
            moves.add(move);
        }
        return moves;
    }

    public void affichageMoves(ArrayList<Double> moves) {
        ArrayList<String> printedMoves = new ArrayList<>();
        for (double move : moves) {
            if (move < 0.26) {
                printedMoves.add("Up");
            }
            if (move >= 0.26 && move < 0.51) {
                printedMoves.add("Right");
            }
            if (move >= 0.51 && move < 0.76) {
                printedMoves.add("Down");
            }
            if (move >= 0.76) {
                printedMoves.add("Left");
            }
        }
        System.out.println(printedMoves);
    }

    public void affichageMove(double move) {
        if (move < 0.26) {
            System.out.println("Current move : Up");
        }
        if (move >= 0.26 && move < 0.51) {
            System.out.println("Current move : Right");
        }
        if (move >= 0.51 && move < 0.76) {
            System.out.println("Current move : Down");
        }
        if (move >= 0.76) {
            System.out.println("Current move : Left");
        }
    }

    public ArrayList<Chromosome> crossover2(Chromosome parent1, Chromosome parent2) {
        // the 2 children of the crossover
        ArrayList<Chromosome> children = new ArrayList<>();


        int indice1 = parent1.getMoves().size() * 3 / 4;
        int indice2 = parent2.getMoves().size() / 2;

        ArrayList<Double> moves = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves.addAll(parent2.getMoves().subList(indice2, parent2.getMoves().size()));
        Chromosome child = new Chromosome(moves);
        child.isDoable();
        if (child.getFitness() != -1) {
            children.add(child);
        }

        moves = new ArrayList<>(parent2.getMoves().subList(0, indice2));
        moves.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child = new Chromosome(moves);
        child.isDoable();
        if (child.getFitness() != -1) {
            children.add(child);
        }

        return children;
    }

    public boolean isSolution(ArrayList<Chromosome> populations) {
        for (Chromosome chromosome : populations) {
            if (chromosome.getFitness() == 0) { //sinon on met <=2 ou bien un truc du genre
                this.solution = chromosome;
                return true;
            }
        }
        return false;
    }

}
