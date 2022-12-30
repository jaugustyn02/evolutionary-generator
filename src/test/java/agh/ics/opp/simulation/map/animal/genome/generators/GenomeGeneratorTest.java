package agh.ics.opp.simulation.map.animal.genome.generators;

import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.behaviours.OrderlySelector;
import agh.ics.opp.simulation.map.elements.animal.genome.generators.GenomeGenerator;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenomeGeneratorTest {
    @Test
    public void correctLength() {
        int genomeLength = 10;
        GenomeGenerator genomeGenerator = new GenomeGenerator(genomeLength);

        int[] randomGenome = genomeGenerator.getRandomGenome();
        assertEquals(genomeLength, randomGenome.length);
    }
    @Test
    public void correctEnergyRatio() {
        int genomeLength = 10;
        GenomeGenerator genomeGenerator = new GenomeGenerator(genomeLength);
        OrderlySelector selector = new OrderlySelector(10);

        int[] parent1Genome = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] parent2Genome = new int[] {11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        Animal parent1 = new Animal(new Vector2d(0, 0), 50, 100, 1, parent1Genome,null , selector);
        Animal parent2 = new Animal(new Vector2d(0, 0), 50, 100, 1, parent2Genome, null, selector);

        int[] descendantGenome = genomeGenerator.getDescendantGenome(parent1, parent2);
        int parent1NumOfGenes = (int) Math.floor((double)parent1.getEnergy() * genomeLength / (parent1.getEnergy()+parent2.getEnergy()) + 0.5);
        int parent2NumOfGenes = genomeLength - parent1NumOfGenes;
        assertEquals(parent1NumOfGenes, countOccurrences(parent1Genome, descendantGenome));
        assertEquals(parent2NumOfGenes, countOccurrences(parent2Genome, descendantGenome));
    }

    private int countOccurrences(int[] array, int[] subArray) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == subArray[i]) {
                count++;
            }
        }
        return count;
    }
    @Test
    public void randomComposition() {
        int genomeLength = 10;
        GenomeGenerator genomeGenerator = new GenomeGenerator(genomeLength);
        OrderlySelector selector = new OrderlySelector(10);
        int[] parent1Genome = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] parent2Genome = new int[] {11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        Animal parent1 = new Animal(new Vector2d(0, 0), 50, 100, 1, parent1Genome, null, selector);
        Animal parent2 = new Animal(new Vector2d(0, 0), 50, 100, 1, parent2Genome, null, selector);

        int numOfIterations = 100;
        int numOfParent1FirstGenomes = 0;
        for (int i = 0; i < numOfIterations; i++) {
            int[] descendantGenome = genomeGenerator.getDescendantGenome(parent1, parent2);
            if (Arrays.equals(Arrays.copyOfRange(parent1Genome, 0, genomeLength / 2), Arrays.copyOfRange(descendantGenome, 0, genomeLength / 2))) {
                numOfParent1FirstGenomes++;
            }
        }
        assertTrue(numOfParent1FirstGenomes > 0);
        assertTrue(numOfParent1FirstGenomes < numOfIterations);
    }
}
