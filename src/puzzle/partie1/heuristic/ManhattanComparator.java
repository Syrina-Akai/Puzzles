package puzzle.partie1.heuristic;

import puzzle.Main;
import puzzle.Taquin;

import java.util.Comparator;

public class ManhattanComparator implements Comparator<Taquin> {
    public ManhattanComparator() {
    }

    public int indexToCase(int index) {
        int ligne;
        if (index < 3) {
            ligne = 0;
        } else if (index < 6) {
            ligne = 1;
        } else {
            ligne = 2;
        }

        return ligne;
    }

    public int distanceCase(int indexActuel, int indexCible) {
        int ligneActuel = this.indexToCase(indexActuel);
        int ligneCible = this.indexToCase(indexCible);
        int ligne = Math.abs(ligneActuel - ligneCible);
        int colonne = Math.abs(indexActuel % 3 - indexCible % 3);
        return ligne + colonne;
    }

    public int distanceEtat(Taquin taquin) {
        String id = taquin.id;
        int distance = 0;

        for(int i = 0; i < taquin.id.length(); ++i) {
            if (id.charAt(i) != '0') {
                int indexCible = Main.idBut.indexOf(id.charAt(i));
                distance += this.distanceCase(i, indexCible);
            }
        }

        return distance;
    }

    public int compare(Taquin taquin1, Taquin taquin2) {
        int d1 = this.distanceEtat(taquin1) + taquin1.getDepth();
        int d2 = this.distanceEtat(taquin2) + taquin2.getDepth();
        if (d1 > d2) {
            return 1;
        } else {
            return d1 < d2 ? -1 : 0;
        }
    }
}