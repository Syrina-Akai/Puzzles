package puzzle;

import java.util.*;

public class GA {

    ArrayList<Chromosome> population = new ArrayList<>();
    //ArrayList<Chromosome> fittestPopulation = new ArrayList<>();
    //ArrayList<Chromosome> newGeneration = new ArrayList<>();
    Chromosome solution = null;
    int generation = 0;
    int chromosomeSize = 0;
    int crossoverRate,mutationRate;
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

        this.population = new ArrayList<>(fittestPopulation.subList(0, fittestPopulation.size()/3));
        /*if (fittestPopulation.size() > chromosomeSize*2) {
            this.population = new ArrayList<>(fittestPopulation.subList(0, (chromosomeSize*40)/100));
        }
        else {
            this.population = new ArrayList<>();
            this.population.addAll(fittestPopulation);
        }*/
        System.out.println(("population size: " + this.population.size()));
    }

    public ArrayList<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        ArrayList<Chromosome> children = new ArrayList<>();
        int indice1 = (parent1.getMoves().size() / 2) + 1;
        ArrayList<Double> moves;

        moves = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves.addAll(parent2.getMoves().subList(indice1, parent2.getMoves().size()));
        Chromosome child = new Chromosome(moves);
        child.isDoable();
        children.add(child);

        moves = new ArrayList<>(parent2.getMoves().subList(0, indice1));
        moves.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child = new Chromosome(moves);
        child.isDoable();
        children.add(child);

        return children;
    }

    public ArrayList<Chromosome> twoPointsCrossover(Chromosome parent1, Chromosome parent2){
        ArrayList<Chromosome> children = new ArrayList<>();
        ArrayList<Double> moves;
        Chromosome child;

        int indice1=parent1.getMoves().size()/3;
        int indice2=(parent1.getMoves().size()*2)/3;

        moves=new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves.addAll(parent2.getMoves().subList(indice1, indice2));
        moves.addAll(parent1.getMoves().subList(indice2, parent1.getMoves().size()));
        child=new Chromosome(moves);
        child.isDoable();
        children.add(child);

        moves=new ArrayList<>(parent2.getMoves().subList(0, indice1));
        moves.addAll(parent1.getMoves().subList(indice1, indice2));
        moves.addAll(parent2.getMoves().subList(indice2, parent2.getMoves().size()));
        child=new Chromosome(moves);
        child.isDoable();
        children.add(child);
        return children;
    }

    public void mutation(){
        /*
        * on va generer un num random entre le 0-2
        * si le num est 0 alors on choisi 2 cases alleatoires de la population et on permute
        * si le num est 1 alors on fait la mutation li malfin ndirouha....
        * */
        //on fait les mutations d'apres le rate de mutation
        int mutationStop=(this.population.size()*mutationRate)/100;
        //this is a copy of the population
        //we need to remove the cases li dernalhoum permutation pour eviter la repition
        LinkedList<Chromosome> populationCopy = new LinkedList<>(this.population);
        Random rand = new Random();

        for (int i=0; i<mutationStop;i++){
            int mutantIndex  = rand.nextInt(populationCopy.size());
            Chromosome chromosome=populationCopy.remove(mutantIndex);
            int mutation = rand.nextInt(2);
            switch (mutation) {
                case 0: // permutation
                    int firstPosition = rand.nextInt(chromosome.getMoves().size());
                    int secondPosition = rand.nextInt(chromosome.getMoves().size());

                    double tmp = chromosome.getMoves().get(firstPosition);
                    chromosome.getMoves().set(firstPosition,chromosome.getMoves().get(secondPosition));
                    chromosome.getMoves().set(secondPosition,tmp);
                    break;
                case 1: // inverse
                    // To make sure that the new move is different from the old one
                    int position = rand.nextInt(chromosome.getMoves().size());
                    double oldMove = chromosome.getMoves().get(position),newMove=0.0;
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

                    chromosome.getMoves().set(position, newMove);
                    break;
            }
        }
    }

    public void mutatate() {
        int mutationStop=(this.population.size()*mutationRate)/100;
        for (int i=0; i<mutationStop;i++){
            Chromosome chromosome=this.population.get(i);
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
        int maxGenerations = 1000;
        int chances=0;
        crossoverRate=100;
        mutationRate=0;
        boolean noSolution=isSolution(this.population);
        while (!noSolution && this.population.size() > 0 && generation <= maxGenerations && chances<=20) {
            //1-evaluation de la population
            this.fit();
            System.out.println("Chance : "+chances);
            System.out.println("Generation: " + generation);
            System.out.println("Population: " + this.population + "\n");
            newGeneration = new ArrayList<>();
            //2-generation de la nouvelle population
            int crossoverStop=(this.population.size()*crossoverRate)/100;
            for (int i = 0; i < crossoverStop-1; i++) {
                for (int j = i + 1; j < crossoverStop; j++) {
                    newGeneration.addAll(crossover(population.get(i), population.get(j)));
                }
            }
            //*************NEW CROSSOVER****************//

            /*
            * on fait pas un crossover pour tt les elements
            * on va choisir alleatoirement 2 elements de la population
            * on leur fait le crossover
            * hakka ydir le crossover avec le rate li rana habinou mais machi m3ahoum kml...
            * */
            /////////////////// THE CODE //////////////////////////////////
            LinkedList<Chromosome> populationCopy = new LinkedList<>(this.population);
            for (int i = 0; i < crossoverStop; i++) {
                Random random=new Random();
                int index1 = random.nextInt(populationCopy.size());
                int index2 = random.nextInt(populationCopy.size());
                while (index2==index1){
                    index2 = random.nextInt(populationCopy.size());
                }

                newGeneration.addAll(crossover(populationCopy.remove(index1), populationCopy.remove(index2)));
            }
            this.population = new ArrayList<>();
            this.population.addAll(newGeneration);
            generation++;
            noSolution=isSolution(this.population);
            //3- Si la solution n'existe pas on fait la mutation
            if (this.generation <= maxGenerations && !noSolution)
                this.mutation();

            //4-Set chances for the population
            if(population.size()<=1 || (generation == maxGenerations+1 && !noSolution)){
                chances++;
                this.chromosomeSize+=1;
                initialisePopulation();
                generation=0;
                crossoverRate=100;
                mutationRate=0;
            }

            //5- modification de crossover rate et mutation rate
            if(crossoverRate!=0)
                crossoverRate-=3;
            if(mutationRate!=100)
                mutationRate+=3;
        }

        if (solution != null && this.population.size() > 0) {
            System.out.println("CONGRATS ! ");
            solution.affichageMoves();
        } else {
            if(this.population.size() <= 0){
                System.out.println("pas de population :) ");
            }
            if(generation > maxGenerations){
                System.out.println("all generation");
            }
            if(!noSolution){
                System.out.println("pas de solution :) ");
            }
            if(chances==20){
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
