package puzzle;
import java.util.Comparator;

import static puzzle.Main.idBut;

public class CompareH1 implements Comparator<Taquin> {

    public int indexToCase(int index) {
        int ligne = 0;
        if (index < 3) {
            ligne = 0;
        } else {
            if (index < 6) {
                ligne = 1;
            } else {
                ligne = 2;
            }
        }
        return ligne;
    }

    public int distanceCase(int indexActuel, int indexCible) {
        int ligneActuel = indexToCase(indexActuel);
        int ligneCible = indexToCase(indexCible);
        int ligne = Math.abs(ligneActuel - ligneCible);
        int colonne = Math.abs(indexActuel % 3 - indexCible % 3);
        return ligne + colonne;
    }

    public int distanceEtat(Taquin taquin) {

        String id = taquin.id;
        int indexCible, distance = 0;
        for (int i = 0; i < taquin.id.length(); i++) {
            if (id.charAt(i) != '0') {
                indexCible = idBut.indexOf(id.charAt(i));
                distance += distanceCase(i, indexCible);
            }
        }
        return distance;
    }

    @Override
    public int compare(Taquin taquin1, Taquin taquin2) {
        int d1 = distanceEtat(taquin1)+taquin1.depth, d2 = distanceEtat(taquin2)+taquin2.depth;
        if (d1 > d2)
            return 1;
        else if (d1 < d2) {
            return -1;
        }
        return 0;
    }
}
