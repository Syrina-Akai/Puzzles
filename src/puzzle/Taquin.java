package puzzle;

import java.util.Random;

public class Taquin {
    public String id = "";
    int vide;
    int depth;

    public Taquin(boolean root) {
        if (root) {
            depth = 0;
            init();
        }
    }

    public void init() {
        Random rand = new Random();
        int val;
        for (int i = 0; i < 9; i++) {
            do {
                val = rand.nextInt(9);
            } while (id.contains(String.valueOf(val)));
            id += val;
        }
        vide = id.indexOf("0");
    }

    public void nextMove(Taquin parent, int move) {
        // permutation de vide avec move
        StringBuilder stringBuilder = new StringBuilder(parent.id);
        stringBuilder.setCharAt(parent.vide, parent.id.charAt(move));
        stringBuilder.setCharAt(move, '0');
        id = stringBuilder.toString();
        vide = move;
    }

    public void afficherTaquin() {
        System.out.println("\n");
        for (int i = 0; i < 9; i++) {
            System.out.print(id.charAt(i) + "\t");
            if ((i + 1) % 3 == 0) {//une ligne
                System.out.println();
            }
        }
    }

    public void idToTaquin(String id) {//besoin de ça pour les nouveaux taquins
        this.id = id;
        this.vide = id.indexOf('0');
    }

    public String toString() {
        return id;
    }

}
