package puzzle.partie2.genitic;

import puzzle.Taquin;
import puzzle.partie1.heuristic.ManhattanComparator;

import java.util.ArrayList;
import java.util.Stack;



public class Chromosome {
    private ArrayList<Double> moves;
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
        return "" + printedMoves + fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return moves.equals(that.moves);
    }

    public void affichageMoves(){
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

    public boolean isDoable(Taquin taquin){
        boolean isDoable=true;
        for (double move :moves) {
            if(move<0.26){//up 0.254
                if(taquin.getVide() <= 2){
                    isDoable= false;
                    break;
                }else {
                    taquin.nextMove(taquin, taquin.getVide() - 3);
                }
            }
            if(move>=0.26 && move<0.51){//right
                if(taquin.getVide() % 3 == 2 ){
                    isDoable= false;
                    break;
                }
                else {
                    taquin.nextMove(taquin, taquin.getVide() + 1);
                }
            }
            if(move>=0.51 && move<0.76){//down
                if(taquin.getVide() >= 6){
                    isDoable= false;
                    break;
                }
                else {
                    taquin.nextMove(taquin, taquin.getVide() + 3);
                }
            }
            if(move>=0.76){//left
                if(taquin.getVide() % 3 == 0){
                    isDoable= false;
                    break;
                }
                else {
                    taquin.nextMove(taquin, taquin.getVide() - 1);
                }
            }
        }
        if(isDoable){
            fitness = new ManhattanComparator().distanceEtat(taquin);
        }else {
            fitness= -1;
        }
        return isDoable;
    }

    public Stack<String> chromosomeToTaquins(Taquin root){
        Stack<String> temp = new Stack<>();
        temp.push(root.getId());
        ArrayList<Double> solutionPath = this.getMoves();
        Taquin taquin = new Taquin(root.getId());
        for (int i = 0; i < solutionPath.size(); i++) {
            double move = solutionPath.get(i);
            if(move<0.26){//up 0.254
                taquin.nextMove(taquin, taquin.getVide() - 3);
            }
            if(move>=0.26 && move<0.51){//right
                taquin.nextMove(taquin, taquin.getVide() + 1);
            }
            if(move>=0.51 && move<0.76){//down
                taquin.nextMove(taquin, taquin.getVide() + 3);
            }
            if(move>=0.76){//left
                taquin.nextMove(taquin, taquin.getVide() - 1);
            }
            temp.push(taquin.id);
        }
        return temp;
    }
}


