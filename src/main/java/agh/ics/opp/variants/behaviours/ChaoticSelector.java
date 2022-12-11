package agh.ics.opp.variants.behaviours;

public class ChaoticSelector implements IGeneSelector{
    private final int genomeLength;
    public ChaoticSelector(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    @Override
    public int getNextGeneIndex(int currentGeneIndex) {
        return 0;
    }
}
