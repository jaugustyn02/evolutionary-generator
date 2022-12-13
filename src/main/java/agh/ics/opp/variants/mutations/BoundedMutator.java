package agh.ics.opp.variants.mutations;
import java.util.Arrays;
import java.util.Random;

public class BoundedMutator implements IGenomeMutator {
    final int minNumOfMutations;
    final int maxNumOfMutations;
    int randomMutationAmount;


    public BoundedMutator(int minNumOfMutations, int maxNumOfMutations) {
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
        this.randomMutationAmount = (int) (Math.random()*(maxNumOfMutations - minNumOfMutations+1) + minNumOfMutations);
    }

    @Override
    public int[] getMutatedGenome(int[] genome) {
        int changesCounter = 0;
        int indexToMutate;
        int newGene;
        boolean[] changedGenes = new boolean[genome.length];
        Arrays.fill(changedGenes, Boolean.FALSE);
        while (changesCounter < this.randomMutationAmount){
            indexToMutate = (int)(Math.random()*(genome.length +1));
            if (!changedGenes[indexToMutate]){
                int plusMinus = new Random().nextBoolean() ? 1 : -1;
                newGene = (genome[indexToMutate]+ plusMinus)%7;
                genome[indexToMutate] = newGene!=-1 ? newGene : 7;
                changesCounter++;
                changedGenes[indexToMutate] = true;
            }
        }
        return genome;
    }
}
