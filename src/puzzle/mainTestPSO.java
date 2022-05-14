package puzzle;

import puzzle.partie2.pso.PSO;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
public class mainTestPSO {
    public static void main(String[] args) {
        int time = 0;


        PSO mypso = new PSO("123784650");
        long startTime = System.nanoTime();
        mypso.PSOmain();
        long endTime = System.nanoTime();
        mypso.afficherSolution();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

    }
}
