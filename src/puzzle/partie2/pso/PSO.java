package puzzle.partie2.pso;

import puzzle.partie1.heuristic.ManhattanComparator;
import puzzle.Taquin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class PSO {
    //attributes
    private Taquin init;
    public double gbest;
    private Particle gbestParticle;
    public ArrayList<Particle> particles = new ArrayList<Particle>();
    private ArrayList<String> visited = new ArrayList<String>();
    private Stack<String> solution = new Stack<>();

    public PSO(String initID){
        init = new Taquin(initID);
        initialize();
    }


    //methods
    public void PSOmain(){
        int generation = 1;
        while(fitness(gbestParticle.getMoves().get(gbestParticle.getMoves().size()-1).id) != 0) {
            System.out.println("generation "+ generation);
            for (Particle element : particles) {
                element.updateVelocity(gbest);
                element.updatePosition();
                element.updatePBest();
            }
            updateGbest();
            updateParticles();
            System.out.println(gbest);
            generation++;
        }

        ArrayList<Taquin> solutionPath = this.gbestParticle.getMoves();
        for (int i = solutionPath.size() - 1; i >= 0; i--) {
            this.solution.push(solutionPath.get(i).id);
        }
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
            Queue<Integer> nextMoves = new LinkedList<Integer>();
            Queue<String> direction = new LinkedList<>();
            
            //we retrieve last taquin in the particle to generate next moves
            if (lastTaquin.getVide() % 3 != 0) {//i-1 ==> à gauche
                nextMoves.add(lastTaquin.getVide() - 1);
                direction.add("Gauche");
            }
            if (lastTaquin.getVide() > 2) {//i-3 ==> en haut
                nextMoves.add(lastTaquin.getVide() - 3);
                direction.add("Haut");
            }
            if (lastTaquin.getVide() % 3 != 2) {//i+1 ==> à droite
                nextMoves.add(lastTaquin.getVide() + 1);
                direction.add("Droite");
            }
            if (lastTaquin.getVide() < 6) {//i+3 ==> en bas
                nextMoves.add(lastTaquin.getVide() + 3);
                direction.add("Bas");
            }
            //we loop through the moves to create the particles and add them
            while(!nextMoves.isEmpty()){
                //we get the previous move set
                ArrayList<Taquin> addedMove = new ArrayList<>(elemMoves);
                Taquin part = new Taquin(false);
                //new taquin with one of the next moves
                part.nextMove(lastTaquin, nextMoves.remove());
                //we add that taquin to the old move set
                addedMove.add(part);
                if(!visited.contains(addedMove.get(addedMove.size()-1).id)){
                    //we add it to the new particle list
                    Particle newParticle = new Particle(element, addedMove);
                    ArrayList<String> oldDirection = new ArrayList<>(newParticle.getDirections());
                    oldDirection.add(direction.remove());
                    newParticle.setDirections(oldDirection);
                    newParticles.add(newParticle);
                    visited.add(addedMove.get(addedMove.size()-1).id);
                }

            }

        }
        particles = newParticles;
    }
    public void updateGbest(){
        for (Particle element : particles) {
            if(fitness(gbestParticle.getMoves().get(gbestParticle.getMoves().size()-1).id) >= element.getPbestFit()){
                gbest = element.getPbest();
                gbestParticle = element;
            }
        }
    }

    public int fitness(String id){
        Taquin temp = new Taquin(id);
        ManhattanComparator manh = new ManhattanComparator();
        return(manh.distanceEtat(temp));
    }

    public void initialize(){
        //create the first generation
        //init gbest
        Queue<Integer> nextMoves = new LinkedList<>();
        Queue<String> direction = new LinkedList<>();
        if (init.getVide() % 3 != 0) {//i-1 ==> à gauche
            nextMoves.add(init.getVide() - 1);
            direction.add("Gauche");
        }
        if (init.getVide() > 2) {//i-3 ==> en haut
            nextMoves.add(init.getVide() - 3);
            direction.add("Haut");
        }
        if (init.getVide() % 3 != 2) {//i+1 ==> à droite
            nextMoves.add(init.getVide() + 1);
            direction.add("Droite");
        }
        if (init.getVide() < 6) {//i+3 ==> en bas
            nextMoves.add(init.getVide() + 3);
            direction.add("Bas");
        }
        while(!nextMoves.isEmpty()){
            Taquin part = new Taquin(false);
            part.nextMove(init, nextMoves.remove());
            ArrayList<Taquin> list = new ArrayList<Taquin>();
            list.add(init);
            list.add(part);
            Particle newParticle = new Particle(init, list);
            ArrayList<String> oldDirection = new ArrayList<>(newParticle.getDirections());
            oldDirection.add(direction.remove());
            newParticle.setDirections(oldDirection);
            particles.add(newParticle);
        }

        ArrayList<Taquin> initmov = new ArrayList<Taquin>();
        initmov.add(init);
        gbestParticle = new Particle(init, initmov);
        updateGbest();
    }

    public Stack<String> getSolution() {
        return this.solution;
    }

    public ArrayList<String> afficherSolution(){
        System.out.println("generated particles "+ particles.size());
        System.out.println("size " + gbestParticle.getMoves().size());
        for (Taquin elem: gbestParticle.getMoves()) {
            elem.afficherTaquin();
        }
        System.out.println(gbestParticle.getDirections());
        return gbestParticle.getDirections();
    }

}
