package agh.ics.opp.simulation.map.animal.genome.mutations;

import agh.ics.opp.simulation.map.elements.animal.genome.mutations.UnboundedMutator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnboundedMutatorTest {
    @Test
    public void correctLength() {
        int genomeLength = 10;
        int minNumOfMutations = 1;
        int maxNumOfMutations = 3;
        UnboundedMutator genomeMutator = new UnboundedMutator(minNumOfMutations, maxNumOfMutations);

        int[] genome = new int[genomeLength];
        int[] mutatedGenome = genomeMutator.getMutatedGenome(genome);
        assertEquals(genomeLength, mutatedGenome.length);
    }
    @Test
    public void correctNumberOfMutations() {
        int genomeLength = 10;
        int minNumOfMutations =1;
        int maxNumOfMutations = 3;
        UnboundedMutator genomeMutator = new UnboundedMutator(minNumOfMutations, maxNumOfMutations);

        int[] genome = new int[genomeLength];
        int[] genomeCopy = genome.clone();
        int[] mutatedGenome = genomeMutator.getMutatedGenome(genome);
        int numOfMutations = countDifferences(genomeCopy, mutatedGenome);
        assertTrue(numOfMutations >= minNumOfMutations);
        assertTrue(numOfMutations <= maxNumOfMutations);

    }

    private int countDifferences(int[] array1, int[] array2) {
        int count = 0;
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                count++;
            }
        }
        return count;
    }
    @Test
    public void correctMutationRange() {
        int genomeLength = 10;
        int minNumOfMutations = 1;
        int maxNumOfMutations = 3;
        UnboundedMutator genomeMutator = new UnboundedMutator(minNumOfMutations, maxNumOfMutations);

        int numOfIterations = 100;
        for (int i = 0; i < numOfIterations; i++) {
            int[] genome = new int[genomeLength];
            int[] mutatedGenome = genomeMutator.getMutatedGenome(genome);
            for (int j = 0; j < genomeLength; j++) {
                assertTrue(mutatedGenome[j] >= 0);
                assertTrue(mutatedGenome[j] <= 7);
            }
        }
    }

}
