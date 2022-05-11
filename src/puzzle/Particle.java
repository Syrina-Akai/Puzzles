package puzzle;
import java.util.ArrayList;

import static puzzle.Main.idBut;

public class Particle {

    //attributes
    private double position;
    private double velocity;
    private int pbest;
    private int currentPFit;
    private int pbestFit;
    private ArrayList<Taquin> moves;
    private String binaryMoves;
    private int decimalMoves;
    private ArrayList<Integer> butLines;
    private ArrayList<Integer> butColumns;

    //constructor
    public Particle(Taquin init,ArrayList<Taquin> moves){
        this.moves = moves;
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        pbestFit = fitness(init.id);
        //this.binaryMoves = binaryMoves;
        pbest = toDecimal();
        position = Math.random();
        velocity = Math.random();
        //but values init

        int matrice [][] = new int[0][];

        for(int i = 0 ; i<3 ; i++){
            for(int j = 0 ; j<3 ; j++){
                matrice[i][j] = Integer.parseInt(moves.get(moves.size() - 1).id.substring(i+j,i+j+1));
            }
        }
        for(int i = 0 ; i<3 ; i++){
            butLines.add(matrice[i][0]+matrice[i][1]+matrice[i][2]);
        }
        //columns
        for(int i = 0 ; i<3 ; i++){
            butColumns.add(matrice[i][0]+matrice[i][1]+matrice[i][2]);
        }

    }

    public Particle(Particle old, ArrayList<Taquin> moves){
        pbest = old.getPbest();
        this.moves = moves;
        position = old.getPosition(); //takes the old position and velocity and they will be updated at the start of the pso loop
        velocity = old.getVelocity();
        currentPFit = fitness(moves.get(moves.size() - 1).id);
        pbestFit = old.getPbestFit();
        decimalMoves = toDecimal();
        butLines = old.getButLines();
        butColumns = old.getButColumns();
    }



    //methods
    public int fitness(String taquinId){
        int fitnessVal = 0;
        for (int i = 0 ; i < (idBut.length() > taquinId.length() ? taquinId : idBut).length() ; i++){
            if (taquinId.charAt(i)!='0')
                fitnessVal += idBut.charAt(i) != taquinId.charAt(i) ? 1 : 0;
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

    public void updateVelocity(int gbest){
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


    public String toBinary(){
        return(Integer.toBinaryString(decimalMoves));
    }

    public int toDecimal(){
        int matrice [][] = new int[0][];
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Integer> columns = new ArrayList<Integer>();
        for(int i = 0 ; i<3 ; i++){
            for(int j = 0 ; j<3 ; j++){
                matrice[i][j] = Integer.parseInt(moves.get(moves.size() - 1).id.substring(i+j,i+j+1));
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
        int differences = 0;
        for (int i = 0 ; i < lines.size(); i++ ){
            differences+= Math.abs(lines.get(i)-butLines.get(i));
            differences+= Math.abs(columns.get(i)-butColumns.get(i));
        }
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

    public int getPbest() {
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

    public int getDecimalMoves() {
        return decimalMoves;
    }

    public void setDecimalMoves(int decimalMoves) {
        this.decimalMoves = decimalMoves;
    }

    public int getPbestFit() {
        return pbestFit;
    }

    public void setPbestFit(int pbestFit) {
        this.pbestFit = pbestFit;
    }

    public ArrayList<Integer> getButLines() {
        return butLines;
    }

    public void setButLines(ArrayList<Integer> butLines) {
        this.butLines = butLines;
    }

    public ArrayList<Integer> getButColumns() {
        return butColumns;
    }

    public void setButColumns(ArrayList<Integer> butColumns) {
        this.butColumns = butColumns;
    }
}

