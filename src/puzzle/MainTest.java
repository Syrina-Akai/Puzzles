package puzzle;


import puzzle.partie2.genitic.GA;

public class MainTest {

    //283106754==>7
    //283156074==>10
    //812704536==14
    //283164705 ==> prof
    //517304286==>24
	//053218764==> new (!GA)
    public static void main(String[] args) {
        GA ga = new GA(new Taquin("283164705"));
        long startTime = System.nanoTime();
        ga.generateSolution();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        System.out.println("stack size: " + ga.getSolution().size());
    }
}
