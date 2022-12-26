package agh.ics.opp.simulation.map.elements.animal.genome.mutations;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class UnboundedMutator implements IGenomeMutator {
    final int minNumOfMutations;
    final int maxNumOfMutations;

    public UnboundedMutator(int minNumOfMutations, int maxNumOfMutations) {
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
    }

    @Override
    public int[] getMutatedGenome(int[] genome) {
        int randomNumOfMutations = ThreadLocalRandom.current().nextInt(minNumOfMutations, maxNumOfMutations+1);
        int changesCounter = 0;
        int indexToMutate;
        boolean[] changedGenes = new boolean[genome.length];
        Arrays.fill(changedGenes, Boolean.FALSE);
        while (changesCounter < randomNumOfMutations){
            indexToMutate = ThreadLocalRandom.current().nextInt(0, genome.length);
            if (!changedGenes[indexToMutate]){
                genome[indexToMutate] = ThreadLocalRandom.current().nextInt(0, 8);
                changesCounter++;
                changedGenes[indexToMutate] = true;
            }
        }
        return genome;
    }
}
