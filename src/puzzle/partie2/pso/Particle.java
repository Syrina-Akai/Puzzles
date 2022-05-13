package puzzle.partie2.pso;
import puzzle.Taquin;
import puzzle.partie1.heuristic.ManhattanComparator;

import java.util.ArrayList;

public class Particle {

    //attributes
    private double position;
    private double velocity;
    private double pbest;
    private int currentPFit;
    private int pbestFit;
    private ArrayList<Taquin> moves;
    private double decimalMoves;
    private double[][] matriceBut = new double[3][3];

    //constructor for initial generation
    public Particle(Taquin init,ArrayList<Taquin> moves){
        this.moves = moves;
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        pbestFit = fitness(init.id);
        position = Math.random();
        velocity = Math.random();
        //but values init

        Taquin but = new Taquin("123804765");

        for(int i = 0 ; i<3 ; i++){
            for(int j = 0 ; j<3 ; j++){
                matriceBut[i][j] = Double.parseDouble(but.id.substring(i+j,i+j+1));
            }
        }
        //normaliser : faire la somme de chaque ligne et colonne , si somme > 1 , diviser
        for(int i=0;i<3;i++){
            double sum=0;
            for(int j=0;j<3;j++){
                sum+=matriceBut[i][j];
            }
            if(sum>5){
                for(int j=0;j<3;j++){
                    matriceBut[i][j]=matriceBut[i][j]/sum;
                }
            }
        }
        pbest = toDecimal();
    }

    //constructor for updating the particles
    public Particle(Particle old, ArrayList<Taquin> moves){
        pbest = old.getPbest();
        this.moves = moves;
        position = old.getPosition(); //takes the old position and velocity and they will be updated at the start of the pso loop
        velocity = old.getVelocity();
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        pbestFit = old.getPbestFit();
        decimalMoves = toDecimal();
    }

    //methods
    public int fitness(String id){
        Taquin temp = new Taquin(id);
        ManhattanComparator manh = new ManhattanComparator();
        return(manh.distanceEtat(temp));
    }


    public void updatePBest(){
        //calculate the fitness and compare with pbest, replace if smaller
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        if(currentPFit <= pbestFit){
            pbest = decimalMoves;
            pbestFit = currentPFit;
        }
    }

    public void updateVelocity(Double gbest){
        double newVelo;
        double c0 = 0.7298;
        double c1 = 1.4960, c2 = 1.4960;
        double fact1 = pbest-position;
        double fact2 = gbest-position;
        newVelo = c0*velocity + c1*Math.random()*fact1 + c2*Math.random()*fact2;
        velocity = newVelo;
    }
    public void updatePosition(){
        double newPos;
        newPos = position + velocity;
        position = newPos;
    }

    public double toDecimal(){
        double[][] matrice = new double[3][3];
        for(int i = 0 ; i<3 ; i++){
            for(int j = 0 ; j<3 ; j++){
                matrice[i][j] = Double.parseDouble(moves.get(moves.size() - 1).id.substring(i+j,i+j+1));
            }
        }
        //normaliser : faire la somme de chaque ligne et colonne , si somme > 1 , diviser
        for(int i=0;i<3;i++){
            double sum=0;
            for(int j=0;j<3;j++){
                sum+=matrice[i][j];
            }
            if(sum>5){
                for(int j=0;j<3;j++){
                    matrice[i][j] =matrice[i][j]/sum;
                }
            }
        }
        double differences = 0;

        for (int i = 0 ; i < 3; i++ ) {
            for (int j = 0 ; j < 3; j++ ){
                differences+=Math.pow(matrice[i][j]-matriceBut[i][j],2);
            }
        }
        differences=Math.sqrt(differences);
        return(differences);
    }


    //getters and setters used
    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getPbest() {
        return pbest;
    }

    public ArrayList<Taquin> getMoves() {
        return moves;
    }

    public int getPbestFit() {
        return pbestFit;
    }

}

