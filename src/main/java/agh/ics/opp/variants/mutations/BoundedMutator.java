package agh.ics.opp.variants.mutations;

public class BoundedMutator implements IGenomeMutator {
    final int minNumOfMutations;
    final int maxNumOfMutations;

    public BoundedMutator(int minNumOfMutations, int maxNumOfMutations) {
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
    }

    @Override
    public int[] mutateGenome(int[] genome) {
        return genome;
    }
}
