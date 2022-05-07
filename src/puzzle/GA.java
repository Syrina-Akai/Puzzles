package puzzle;

import java.util.ArrayList;

public class GA {

    ArrayList<Chromosome> populations= new ArrayList<>();
    ArrayList<Chromosome> fitessPopulations= new ArrayList<>();

    Taquin root=new Taquin("283164705");

    public GA(Taquin root) {
        this.root = root;
    }


    public int fitness(Chromosome chromosome){
        //on va le tester
        Taquin taquin= new Taquin(root.id);
        for (double move:chromosome.getMoves()) {
            if(move>0 && move<=0.25 ){//up
                if(taquin.vide <= 2){
                    return 0;
                }
                taquin.nextMove(taquin, taquin.vide - 3);
            }
            if(move>0.25 && move<=0.5){//right
                if(taquin.vide % 3 == 2 ){
                    return 0;
                }
                taquin.nextMove(taquin, taquin.vide + 1);
            }
            if(move>0.5 && move<=0.75){//down
                if(taquin.vide >= 6){
                    return 0;
                }
                taquin.nextMove(taquin, taquin.vide + 3);
            }
            if(move>0.75 && move<=1){//left
                if(taquin.vide % 3 == 0){
                    return 0;
                }
                taquin.nextMove(taquin, taquin.vide - 1);
            }
        }
        CompareH1 compareH1= new CompareH1();
        return compareH1.distanceEtat(taquin);
    }

    public ArrayList<Chromosome> crossover(Chromosome parent1, Chromosome parent2){
        ArrayList<Chromosome> children= new ArrayList<>();
        int indice1= parent1.getMoves().size()/2;
        int indice2= parent2.getMoves().size()/2;
        ArrayList<Double> moves1 = new ArrayList<>(parent1.getMoves().subList(0, indice1));
        moves1.addAll(parent2.getMoves().subList(indice2, parent2.getMoves().size()));
        Chromosome child1=new Chromosome(moves1);
        if(fitness(child1)!=0)
            children.add(child1);

        ArrayList<Double> moves2 = new ArrayList<>(parent2.getMoves().subList(0, indice2));
        moves2.addAll(parent1.getMoves().subList(indice1, parent1.getMoves().size()));
        child1=new Chromosome(moves2);
        if(fitness(child1)!=0)
            children.add(child1);

        return children;
    }
}
