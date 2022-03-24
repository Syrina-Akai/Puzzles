package puzzle;

import java.util.Comparator;

import static puzzle.Main.idBut;

public class CompareH2 implements Comparator<Taquin> {

    public static int distanceEtat(String s2) {
        int count = 0;
        for (int i = 0; i < (idBut.length() > s2.length() ? s2 : idBut).length(); i++) {
            if (s2.charAt(i) != '0')
                count += idBut.charAt(i) != s2.charAt(i) ? 1 : 0;
        }
        return count;
    }

    @Override
    public int compare(Taquin taquin1, Taquin taquin2) {
        int d1 = distanceEtat(taquin1.id) + taquin1.depth, d2 = distanceEtat(taquin2.id) + taquin2.depth;
        if (d1 > d2) {
            return 1;
        } else {
            return d1 < d2 ? -1 : 0;
        }
    }
}
