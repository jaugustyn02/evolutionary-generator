package agh.ics.opp.variants.behaviours;

public class OrderlySelector implements IGeneSelector {
    final int genomeLength;

    public OrderlySelector(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    @Override
    public int getNextGeneIndex(int currentGeneIndex) {
        return (currentGeneIndex + 1) % this.genomeLength;
    }
}
