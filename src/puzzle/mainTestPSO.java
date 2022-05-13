package puzzle;

import puzzle.partie2.pso.PSO;

public class mainTestPSO {
    public static void main(String[] args) {
        PSO mypso = new PSO("357214860");
        mypso.PSOmain();
        mypso.afficherSolution();

    }
}
