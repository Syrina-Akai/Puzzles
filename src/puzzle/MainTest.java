package puzzle;

import java.util.ArrayList;
import java.util.Random;

import static puzzle.Main.idTest;

public class MainTest {

    public static void main(String[] args) {
        PSO mypso = new PSO("123845076");
        mypso.PSOmain();
        System.out.println("\n\n\n        SOLUTION       ");
        mypso.afficherSolution();
        //GA ga = new GA(new Taquin(idTest));
        //ga.generateSolution();
        /*ga.fitness(ga.populations);
        for (int i = 0; i < ga.populations.size(); i++) {
            System.out.print(ga.populations.get(i));
            System.out.println(", Fitness: " + ga.populations.get(i).getFitness());
        }*/


        /*Random rand = new Random();
        ArrayList<Double> moves;

        Taquin taquin = new Taquin("283164705");
        moves = new ArrayList<>();
        taquin.afficherTaquin();
        moves.add(rand.nextDouble(0.51 - 0.26) + 0.26);//right
        taquin.nextMove(taquin, taquin.vide+1);
        taquin.afficherTaquin();
        double move;
        for (int i = 1; i < 8; i++) {
            boolean test=true;
            do {
                move=rand.nextDouble(1.0);
                System.out.println("on a le move : "+move);
                if(move<0.26){//up 0.254
                    if(taquin.vide > 2){
                        taquin.nextMove(taquin, taquin.vide - 3);
                        test=true;
                    }else {
                        test= false;
                    }
                }
                if(move>=0.26 && move<0.51){//right
                    if(taquin.vide % 3 != 2 ){
                        taquin.nextMove(taquin, taquin.vide + 1);
                        test=true;
                    }
                    else {
                        test= false;
                    }
                }
                if(move>=0.51 && move<0.76){//down
                    if(taquin.vide < 6){
                        taquin.nextMove(taquin, taquin.vide + 3);
                        test=true;
                    }
                    else {
                        test= false;
                    }
                }
                if(move>=0.76){//left
                    if(taquin.vide % 3 != 0){
                        taquin.nextMove(taquin, taquin.vide - 1);
                        test=true;
                    }
                    else {
                        test= false;
                    }
                }
                if (!test) {
                    System.out.println("the move : "+move+" est : "+test+" on \n");
                    taquin.afficherTaquin();
                }

            }while(!test);

            System.out.println("***the move : "+move+" taquin : ");
            taquin.afficherTaquin();
            System.out.println("\n");
            moves.add(move);
        }


        taquin= new Taquin(idTest);
        CompareH1 compareH1= new CompareH1();
        boolean b = true;


        b=true;
        for (double move1 : moves) {
            if (move1 < 0.26) {//up 0.254
                if (taquin.vide <= 2) {
                    b = false;
                    break;
                }
                taquin.nextMove(taquin, taquin.vide - 3);
            }
            if (move1 >= 0.26 && move1 < 0.51) {//right
                if (taquin.vide % 3 == 2) {
                    b = false;
                    break;
                }
                taquin.nextMove(taquin, taquin.vide + 1);
            }
            if (move1 >= 0.51 && move1 < 0.76) {//down
                if (taquin.vide >= 6) {
                    b = false;
                    break;
                }
                taquin.nextMove(taquin, taquin.vide + 3);
            }
            if (move1 >= 0.76) {//left
                if (taquin.vide % 3 == 0) {
                    b = false;
                    break;
                }
                taquin.nextMove(taquin, taquin.vide - 1);
            }
            // add check of taquin but

            System.out.println("le b est : " + b);
            if (b) {
                System.out.println("its true !! ");
                System.out.println("the fitness is : " + compareH1.distanceEtat(taquin));
            }
        }*/
    }
}
