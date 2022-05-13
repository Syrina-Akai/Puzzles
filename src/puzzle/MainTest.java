package puzzle;


import puzzle.partie2.genitic.GA;

public class MainTest {

    //283106754==>7
    //283156074==>10
    //812704536==14
    //283164705 ==> prof
    //517304286==>24
    public static void main(String[] args) {
        GA ga = new GA(new Taquin("513264807"));
        ga.generateSolution();
    }
}
