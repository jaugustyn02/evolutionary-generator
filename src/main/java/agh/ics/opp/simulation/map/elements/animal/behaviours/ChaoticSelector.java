package agh.ics.opp.simulation.map.elements.animal.behaviours;

import java.util.concurrent.ThreadLocalRandom;

public class ChaoticSelector implements IGeneSelector{
    private final int genomeLength;
    public ChaoticSelector(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    @Override
    public int getNextGeneIndex(int currentGeneIndex) {
        int chaoticCheck = ThreadLocalRandom.current().nextInt(0, 5);
        int rightNextGeneIndex = (currentGeneIndex + 1) % this.genomeLength;
        int nextChaoticGene;
        if (chaoticCheck < 4)
            return rightNextGeneIndex;
        else {
            do {
                nextChaoticGene = ThreadLocalRandom.current().nextInt(0, genomeLength);
            } while (nextChaoticGene == rightNextGeneIndex);
            return nextChaoticGene;
        }
    }
}
