package agh.ics.opp.simulation.map.elements.animal.genome.mutations;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class BoundedMutator implements IGenomeMutator {
    final int minNumOfMutations;
    final int maxNumOfMutations;

    public BoundedMutator(int minNumOfMutations, int maxNumOfMutations) {
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
    }

    @Override
    public int[] getMutatedGenome(int[] genome) {
        int randomNumOfMutations = ThreadLocalRandom.current().nextInt(minNumOfMutations, maxNumOfMutations+1);
//        System.out.println("Genom przechodzi mutacje "+randomNumOfMutations+" genów z "+Arrays.toString(genome));
        int changesCounter = 0;
        int indexToMutate;
        int newGene;
        boolean[] changedGenes = new boolean[genome.length];
        Arrays.fill(changedGenes, Boolean.FALSE);
        while (changesCounter < randomNumOfMutations){
            indexToMutate = ThreadLocalRandom.current().nextInt(0, genome.length);
            if (!changedGenes[indexToMutate]){
                int plusMinus = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
                newGene = (genome[indexToMutate]+ plusMinus) % 8;
                genome[indexToMutate] = newGene!=-1 ? newGene : 7;
                changesCounter++;
                changedGenes[indexToMutate] = true;
            }
        }
//        System.out.println("                                na "+Arrays.toString(genome));
        return genome;
    }
}
