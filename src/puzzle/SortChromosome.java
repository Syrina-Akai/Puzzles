package puzzle;

import java.util.Comparator;

public class SortChromosome implements Comparator<Chromosome> {
    @Override
    public int compare(Chromosome chromosome1, Chromosome chromosome2) {
        if (chromosome1.getFitness() > chromosome2.getFitness()) {
            return 1;
        } else {
            return chromosome1.getFitness() < chromosome2.getFitness() ? -1 : 0;
        }
    }
}
