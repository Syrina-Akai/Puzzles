package puzzle;

import java.util.ArrayList;
import java.util.Random;

import static puzzle.Main.idBut;

public class Taquin {
    public String id = "";
    int vide;
    int depth;

    public Taquin(boolean root) {
        if (root) {
            init();
        }
    }

    public Taquin(String id) {
        this.id = id;
        this.vide = id.indexOf('0');
    }

    public String getId() {
        return id;
    }

    public int getVide() {
        return vide;
    }

    public void setVide(int vide) {
        this.vide = vide;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
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
        depth = parent.depth + 1;
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

    public ArrayList<Integer> taquinToIndexes(){
        ArrayList<Integer> indexTaquin=new ArrayList<>();
        String but= idBut.substring(0,idBut.indexOf("0"))+idBut.substring(idBut.indexOf("0")+1);
        String id=this.id.substring(0,this.id.indexOf("0"))+this.id.substring(this.id.indexOf("0")+1);
        for (int i=0;i<id.length();i++){
            indexTaquin.add(but.indexOf(id.charAt(i)));
        }
        return indexTaquin;
    }

    public boolean isSolvable(){
        ArrayList<Integer> indexTaquin=taquinToIndexes();
        int count=0;
        for(int i=0;i<indexTaquin.size();i++){
            for (int j=i+1;j<indexTaquin.size();j++){
                if(indexTaquin.get(i)>indexTaquin.get(j))
                    count++;
            }
        }
        return count%2 == 0;
    }

    public String toString() {
        return id;
    }


    @Override
    public int hashCode(){
        return Integer.parseInt(id)%362897;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Taquin taquin = (Taquin)o;
        return taquin.id.equals(this.id);
    }
}
