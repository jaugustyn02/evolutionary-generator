package agh.ics.opp.variants.behaviours;

public class ChaoticSelector implements IGeneSelector{
    private final int genomeLength;
    public ChaoticSelector(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    @Override
    public int getNextGeneIndex(int currentGeneIndex) {
        int chaoticCheck = (int) (Math.random() * (101));
        int nextChaoticGene;
        if (chaoticCheck <=80)
            return (currentGeneIndex + 1) % this.genomeLength;
        else {
            nextChaoticGene = (int) (Math.random() * (this.genomeLength + 1));
            while (nextChaoticGene == currentGeneIndex+1){
                nextChaoticGene = (int) (Math.random() * (this.genomeLength + 1));
            }
            return nextChaoticGene;
        }
    }
}
