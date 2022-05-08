package puzzle;

import java.util.ArrayList;

public class Chromosome {
    private ArrayList<Double> moves;
    private int parent1 = 0;
    private int parent2 = 0;
    private float fitness = -1;

    public Chromosome(ArrayList<Double> moves) {
        this.moves = moves;
    }

    public ArrayList<Double> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Double> moves) {
        this.moves = moves;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int getParent1() {
        return parent1;
    }

    public void setParent1(int parent1) {
        this.parent1 = parent1;
    }

    public int getParent2() {
        return parent2;
    }

    public void setParent2(int parent2) {
        this.parent2 = parent2;
    }

    @Override
    public String toString() {
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
        return ""+printedMoves;
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
}


