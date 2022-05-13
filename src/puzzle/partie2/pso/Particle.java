package puzzle.partie2.pso;
import puzzle.Taquin;

import java.util.ArrayList;

import static puzzle.Main.idBut;

public class Particle {

    //attributes
    private double position;
    private double velocity;
    private double pbest;
    private int currentPFit;
    private int pbestFit;
    private ArrayList<Taquin> moves;
    private String binaryMoves;
    private double decimalMoves;
    private ArrayList<Double> butLines = new ArrayList<Double>();
    private ArrayList<Double> butColumns = new ArrayList<Double>();
    private double[][] matriceBut = new double[3][3];
    //idk
    //constructor
    public Particle(Taquin init,ArrayList<Taquin> moves){
        this.moves = moves;
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        pbestFit = fitness(init.id);
        //this.binaryMoves = binaryMoves;
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
        //Lines
        for(int i = 0 ; i<3 ; i++){
            butLines.add(matriceBut[i][0]+matriceBut[i][1]+matriceBut[i][2]);
        }
        //columns
        for(int i = 0 ; i<3 ; i++){
            butColumns.add(matriceBut[i][0]+matriceBut[i][1]+matriceBut[i][2]);
        }
        pbest = toDecimal();
    }

    public Particle(Particle old, ArrayList<Taquin> moves){
        pbest = old.getPbest();
        this.moves = moves;
        position = old.getPosition(); //takes the old position and velocity and they will be updated at the start of the pso loop
        velocity = old.getVelocity();
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        pbestFit = old.getPbestFit();
        butLines = old.getButLines();
        butColumns = old.getButColumns();
        decimalMoves = toDecimal();
    }



    //methods
    /*public int fitness(String taquinId){
        int fitnessVal = 0;
        for (int i = 0 ; i < (idBut.length() > taquinId.length() ? taquinId : idBut).length() ; i++){
            if (taquinId.charAt(i)!='0')
                fitnessVal += idBut.charAt(i) != taquinId.charAt(i) ? 1 : 0;
        }
        return fitnessVal;
    }*/
    public int fitness(String taquinId){
        int fitnessVal = 0;
        int[][] matrice=new int[3][3];
        for(int i = 0 ; i<3 ; i++){
            for(int j = 0 ; j<3 ; j++){
                matrice[i][j] = Integer.parseInt(taquinId.substring(i+j,i+j+1));
            }
        }
        for(int i = 0 ; i<3 ; i++) {
            for (int j = 0; j < 3; j++) {
                fitnessVal+=(3*i+j)*matrice[i][j];
            }
        }
        return fitnessVal;
    }

    public void updatePBest(){
        //calculate the fitness and compare with pbest, replace if smaller
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        if(currentPFit < pbestFit){
            pbest = decimalMoves;
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


    /*public String toBinary(){
        return(Integer.toBinaryString(decimalMoves));
    }*/

    public double toDecimal(){
        double[][] matrice = new double[3][3];
        ArrayList<Double> lines = new ArrayList<Double>();
        ArrayList<Double> columns = new ArrayList<Double>();
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

        //lines
        for(int i = 0 ; i<3 ; i++){
            lines.add(matrice[i][0]+matrice[i][1]+matrice[i][2]);
        }
        //columns
        for(int i = 0 ; i<3 ; i++){
            columns.add(matrice[i][0]+matrice[i][1]+matrice[i][2]);
        }
        double differences = 0;

        for (int i = 0 ; i < lines.size(); i++ ) {
            for (int j = 0 ; j < lines.size(); j++ ){
                differences+=Math.pow(matrice[i][j]-matriceBut[i][j],2);
            }
        }
        differences=Math.sqrt(differences);
        return(differences);
    }


    //getters and setters
    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getPbest() {
        return pbest;
    }

    public void setPbest(int pbest) {
        this.pbest = pbest;
    }

    public int getCurrentPFit() {
        return currentPFit;
    }

    public void setCurrentPFit(int currentPFit) {
        this.currentPFit = currentPFit;
    }

    public ArrayList<Taquin> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Taquin> moves) {
        this.moves = moves;
    }

    public String getBinaryMoves() {
        return binaryMoves;
    }

    public void setBinaryMoves(String binaryMoves) {
        this.binaryMoves = binaryMoves;
    }

    public double getDecimalMoves() {
        return decimalMoves;
    }

    public void setDecimalMoves(double decimalMoves) {
        this.decimalMoves = decimalMoves;
    }

    public int getPbestFit() {
        return pbestFit;
    }

    public void setPbestFit(int pbestFit) {
        this.pbestFit = pbestFit;
    }

    public ArrayList<Double> getButLines() {
        return butLines;
    }

    public void setButLines(ArrayList<Double> butLines) {
        this.butLines = butLines;
    }

    public ArrayList<Double> getButColumns() {
        return butColumns;
    }

    public void setButColumns(ArrayList<Double> butColumns) {
        this.butColumns = butColumns;
    }
}

