package puzzle.part1.heuristic;

import puzzle.Taquin;

import java.util.Comparator;

import static puzzle.Main.idBut;

public class HammingComparator implements Comparator<Taquin> {

    public static int distanceEtat(String s2) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (s2.charAt(i) != '0')
                count += idBut.charAt(i) != s2.charAt(i) ? 1 : 0;
        }
        return count;
    }

    @Override
    public int compare(Taquin taquin1, Taquin taquin2) {
        int d1 = distanceEtat(taquin1.id) + taquin1.getDepth(), d2 = distanceEtat(taquin2.id) + taquin2.getDepth();
        if (d1 > d2) {
            return 1;
        } else {
            return d1 < d2 ? -1 : 0;
        }
    }
}
