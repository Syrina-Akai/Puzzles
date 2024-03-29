package puzzle.partie2.genitic;

import puzzle.Taquin;
import puzzle.partie1.heuristic.Aetoile;
import puzzle.partie1.heuristic.ManhattanComparator;

import java.util.*;

import static puzzle.Main.idBut;

public class GA {

    ArrayList<Chromosome> population=new ArrayList<>();
    public HashSet<Chromosome> officialPopulation= new HashSet<>();
    public Chromosome solutionChromosome = null;

    Stack<String> solution = new Stack<>();
    public int generation = 0;
    int chromosomeSize = 0;
    int crossoverRate,mutationRate;
    int populationSize=450;
    boolean noSolution=false;
    Taquin root;
    ArrayList<Chromosome> newGeneration;

    public GA(Taquin root) {
        this.root = root;
        //chromosomeSize = new ManhattanComparator().distanceEtat(root);
        Aetoile aetoile= new Aetoile(1);
        aetoile.solve(root);
        chromosomeSize=aetoile.getSolution().size();
        System.out.println("la taille est : "+chromosomeSize);
        initialisePopulation();
    }

    public void initialisePopulation() {
        population = new ArrayList<>();
        ArrayList<Float> moves;
        Chromosome chromosome;
        Random rand = new Random();
        float move;
        int populationSizePerMove = 300;
        // The size of the chromosome is the Manhattan distance of the root taquin
        if (root.getVide() > 2) {//i-3 ==> en haut
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add((float) (rand.nextFloat()*(0.26 - 0.0) + 0.0));
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = (float) (rand.nextFloat()*(0.100 - 0.0) + 0.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
        if (root.getVide() % 3 != 2) {//i+1 ==> à droite
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add((float) (rand.nextFloat()*(0.51-0.26)+0.26));
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = (float) (rand.nextFloat()*(0.100 - 0.0) + 0.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
        if (root.getVide() < 6) {//i+3 ==> en bas
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add((float) (rand.nextFloat()* (0.76-0.51)+0.51));
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move = (float) (rand.nextFloat()*(0.100 - 0.0) + 0.0);
                    moves.add(move);
                }
                chromosome = new Chromosome(moves);
                this.population.add(chromosome);
            }
        }
        if (root.getVide() % 3 != 0) {//i-1 ==> à gauche
            for (int i = 0; i < populationSizePerMove; i++) {
                moves = new ArrayList<>();
                moves.add((float) (rand.nextFloat()*(1-0.76) +0.76));
                for (int j = 1; j < this.chromosomeSize; j++) {
                    move =(float) (rand.nextFloat()*(0.100 - 0.0) + 0.0);
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
            if (chromosome.isDoable(new Taquin(root.id)) ) {
                fittestPopulation.add(chromosome);
            }
        }
        fittestPopulation.sort(new SortChromosome());

        if (fittestPopulation.size() > populationSize) {

            this.population = new ArrayList<>(fittestPopulation.subList(0, populationSize+1));
        }
        else {
            this.population = new ArrayList<>();
            this.population.addAll(fittestPopulation);
        }
    }

    public ArrayList<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        ArrayList<Chromosome> children = new ArrayList<>();
        Random random= new Random();
        int indice1=random.nextInt(parent1.getMoves().size());
        int indice2=random.nextInt(parent2.getMoves().size());
        ArrayList<Float> moves;

        moves = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves.addAll(parent2.getMoves().subList(indice2, parent2.getMoves().size()));
        Chromosome child = new Chromosome(moves);
        child.isDoable(new Taquin(root.id));
        children.add(child);

        moves = new ArrayList<>(parent2.getMoves().subList(0, indice2));
        moves.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child = new Chromosome(moves);
        child.isDoable(new Taquin(root.id));
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
        int mutationStop=(this.newGeneration.size()*mutationRate)/100;
        //this is a copy of the population
        //we need to remove the cases li dernalhoum permutation pour eviter la repition
        LinkedList<Chromosome> populationCopy = new LinkedList<>(this.newGeneration);
        this.newGeneration=new ArrayList<>();
        Random rand = new Random();


        for (int i=0; i<mutationStop;i++){
            int mutantIndex  = rand.nextInt(populationCopy.size());
            Chromosome chromosome=populationCopy.remove(mutantIndex);
            int mutation = rand.nextInt(2);
            int chromosomeSize=chromosome.getMoves().size();
            switch (mutation) {
                case 0: // permutation
                    int firstPosition = rand.nextInt(chromosomeSize);
                    int secondPosition = rand.nextInt(chromosomeSize);

                    Float tmp = chromosome.getMoves().get(firstPosition);
                    chromosome.getMoves().set(firstPosition,chromosome.getMoves().get(secondPosition));
                    chromosome.getMoves().set(secondPosition,tmp);
                    break;
                case 1: // inverse
                    // To make sure that the new move is different from the old one
                    int position = rand.nextInt(chromosomeSize);
                    int inverse= rand.nextInt(2);
                    float move=0;
                    switch (inverse){
                        case 0:
                            //on ajoute 0.25
                            move= (float)(chromosome.getMoves().get(position)+0.25);
                            if(move>1)
                                move=(float)0.9;
                            break;
                        case 1:
                            //on enleve 0.25
                            move= (float)(chromosome.getMoves().get(position)-0.25);
                            if(move<0)
                                move=(float) 0.1;
                            break;
                    }
                    chromosome.getMoves().set(position,move);
                    break;
            }
            newGeneration.add(chromosome);
            if(populationCopy.isEmpty()){
                break;
            }
        }
    }

    public boolean isSolution(ArrayList<Chromosome> populations) {
        for (Chromosome chromosome : populations) {
            if (chromosome.getFitness() == 0 && !this.noSolution) {
                solutionChromosome =new Chromosome(chromosome.getMoves());
                solutionChromosome.isDoable(new Taquin(root.id));
                Stack<String> temp = chromosome.chromosomeToTaquins(root);
                while (!temp.empty()) {
                    this.solution.push(temp.pop());
                }
                return true;
            }
        }
        return false;
    }

    public void generateSolution() {

        int maxGenerations = 2000;
        int chances=0;
        crossoverRate=100;
        mutationRate=0;
        this.officialPopulation=new HashSet<>(this.population);
        this.noSolution=isSolution(this.population);
        while (!this.noSolution && this.population.size() > 0 && generation <= maxGenerations && chances<=20) {
            //1-evaluation de la population
            this.fit();
            this.noSolution=isSolution(this.population);
            if(this.noSolution){
                break;
            }
            newGeneration = new ArrayList<>();
            //2-generation de la nouvelle population
            int crossoverStop=(this.population.size()*crossoverRate)/100;
            for (int i = 0; i < crossoverStop-1; i++) {
                for (int j = i + 1; j < crossoverStop; j++) {
                    newGeneration.addAll(crossover(population.get(i), population.get(j)));
                }
            }
            //this.noSolution=isSolution(this.newGeneration);
            //3- Si la solution n'existe pas on fait la mutation
            if (this.generation <= maxGenerations )
                this.mutation();
            //nouvelle population
            generation++;
            this.officialPopulation.addAll(newGeneration);
            this.population=new ArrayList<>(this.officialPopulation);

            if(this.population.size()==0)
                break;

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
            if (crossoverRate==0)
                crossoverRate=100;
            else
                crossoverRate-=1;
            if(mutationRate==100)
                mutationRate=0;
            else
                mutationRate+=1;

        }

        if (solutionChromosome != null && this.population.size() > 0) {
            System.out.println("CONGRATS ! ");
            solutionChromosome.affichageMoves();
            System.out.println("la chance : "+chances);
            System.out.println("la generation : "+generation);
            System.out.println("chromosome size is : "+ solutionChromosome.getMoves().size());
            System.out.println("taille de la derniere population : "+this.officialPopulation.size());
            if(this.newGeneration!=null && !this.newGeneration.isEmpty())
                System.out.println("taille de la derniere generation : "+this.newGeneration.size());
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
    }

    public Stack<String> getSolution() {
        return solution;
    }

}
