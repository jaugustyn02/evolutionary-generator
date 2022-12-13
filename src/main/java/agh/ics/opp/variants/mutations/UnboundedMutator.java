package agh.ics.opp.variants.mutations;

import java.util.Arrays;

public class UnboundedMutator implements IGenomeMutator {
    final int minNumOfMutations;
    final int maxNumOfMutations;
    int randomMutationAmount;

    public UnboundedMutator(int minNumOfMutations, int maxNumOfMutations) {
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
        this.randomMutationAmount = (int) (Math.random()*(maxNumOfMutations - minNumOfMutations+1) + minNumOfMutations);
    }

    @Override
    public int[] getMutatedGenome(int[] genome) {
        int changesCounter = 0;
        int indexToMutate;
        boolean[] changedGenes = new boolean[genome.length];
        Arrays.fill(changedGenes, Boolean.FALSE);
        while (changesCounter < this.randomMutationAmount){
            indexToMutate = (int)(Math.random()*(genome.length +1));
            if (!changedGenes[indexToMutate]){
                genome[indexToMutate] = (int) (Math.random()*(8));
                changesCounter++;
                changedGenes[indexToMutate] = true;
            }
        }
        return genome;
    }
}
