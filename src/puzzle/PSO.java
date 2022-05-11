package puzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static puzzle.Main.idBut;

public class PSO {
    //attributes
    private Taquin init;
    private int gbest;
    private Particle gbestParticle;
    private ArrayList<Particle> particles = new ArrayList<Particle>();

    public PSO(String initID){
        init = new Taquin(initID);
        initialize();
    }


    //methods
    public void PSOmain(){
        //do the init idk
        int generation = 1;
        while(gbest != 0) {
            System.out.println("generation "+ generation);
            for (Particle element : particles) {
                element.updateVelocity(gbest);
                element.updatePosition();
                element.updatePBest();
                updateGbest();
            }
            updateParticles();
            System.out.println(gbest);
            generation++;
        }
        //loop through all particles
        //calculate their velocity and position
        //update pbest
        //update gbest
        //repeat until gbest = 0
    }

    //create all new particles with all possible moves
    public void updateParticles(){
        //step 1: loop through each particle
        //step 2: retrieve the last taquin of the movelist in that particle
        //step 3: create new particles with the new added moves
        //step 4: add new particle to temporary arraylist
        //step 5: once all new particles have been created, replace the particles arraylist with setter
        ArrayList<Particle> newParticles = new ArrayList<Particle>();
        for (Particle element: particles) {
            ArrayList<Taquin> elemMoves = element.getMoves();
            Taquin lastTaquin = elemMoves.get(elemMoves.size()-1);
            Queue<Integer> nextMoves = new LinkedList<>();
            //we retrieve last taquin in the particle to generate next moves
            if (lastTaquin.vide % 3 != 0) {//i-1 ==> à gauche
                nextMoves.add(lastTaquin.vide - 1);
            }
            if (lastTaquin.vide > 2) {//i-3 ==> en haut
                nextMoves.add(init.vide - 3);
            }
            if (lastTaquin.vide % 3 != 2) {//i+1 ==> à droite
                nextMoves.add(lastTaquin.vide + 1);
            }
            if (lastTaquin.vide < 6) {//i+3 ==> en bas
                nextMoves.add(lastTaquin.vide + 3);
            }
            //we loop through the moves to create the particles and add them
            while(!nextMoves.isEmpty()){
                //we get the previous move set
                ArrayList<Taquin> addedMove = elemMoves;

                Taquin part = new Taquin(false);
                //new taquin with one of the next moves
                part.nextMove(lastTaquin, nextMoves.remove());
                //we add that taquin to the old move set
                addedMove.add(part);
                //we add it to the new particle list
                newParticles.add(new Particle(element, addedMove));
                //System.out.println("##################################################################");
            }
            //System.out.println("\n###################################################################\n");

        }
        particles = newParticles;
    }
    public void updateGbest(){
        for (Particle element : particles) {
            if(fitness(gbestParticle.getMoves().get(gbestParticle.getMoves().size()-1).id) >= element.getPbestFit()){
                System.out.println("gbest");
                System.out.println(gbest);
                System.out.println("gbest fitness");
                System.out.println(fitness(gbestParticle.getMoves().get(gbestParticle.getMoves().size()-1).id));
                gbest = element.getPbest();
                gbestParticle = element;
            }
        }
    }

    public int fitness(String taquinId){
        int fitnessVal = 0;
        for (int i = 0 ; i < (idBut.length() > taquinId.length() ? taquinId : idBut).length() ; i++){
            if (taquinId.charAt(i)!='0')
                fitnessVal += idBut.charAt(i) != taquinId.charAt(i) ? 1 : 0;
        }
        return fitnessVal;
    }

    public void initialize(){
        //create the first generation
        //init gbest
        Queue<Integer> nextMoves = new LinkedList<>();
        if (init.vide % 3 != 0) {//i-1 ==> à gauche
            nextMoves.add(init.vide - 1);
        }
        if (init.vide > 2) {//i-3 ==> en haut
            nextMoves.add(init.vide - 3);
        }
        if (init.vide % 3 != 2) {//i+1 ==> à droite
            nextMoves.add(init.vide + 1);
        }
        if (init.vide < 6) {//i+3 ==> en bas
            nextMoves.add(init.vide + 3);
        }
        while(!nextMoves.isEmpty()){
            //create the first particles
            //taquin init will be the initial taquin
            //later taquin init will be the last of the previous moveset
            //arraylist of taquins of moves will be an array list of one child
            //for the update particles it will be passed on the next generation with one addition to it
            Taquin part = new Taquin(false);
            part.nextMove(init, nextMoves.remove());
            ArrayList<Taquin> list = new ArrayList<Taquin>();
            list.add(init);
            list.add(part);
            particles.add(new Particle(init, list));
        }

        ArrayList<Taquin> initmov = new ArrayList<Taquin>();
        initmov.add(init);
        gbestParticle = new Particle(init, initmov);
        updateGbest();
    }

    public void afficherSolution(){
        System.out.println("generated particles "+ particles.size());
        System.out.println("size " + gbestParticle.getMoves().size());
        for (Taquin elem: gbestParticle.getMoves()) {
            elem.afficherTaquin();
        }
    }

}
