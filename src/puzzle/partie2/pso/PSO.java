package puzzle.partie2.pso;

import puzzle.Taquin;

import java.util.ArrayList;

public class PSO {
    public PSO(String initID){
        init = new Taquin(initID);
    }
    //attributes
    private Taquin init;
    private int gbest;
    private Particle gbestParticle;
    private ArrayList<Particle> particles;

    //methods
    public void PSOmain(){

    }

    public void updateParticles(){

    }
    public void updateGbest(){

    }

    public void initialize(){
        //create the first generation
        //init gbest
    }

}
