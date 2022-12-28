package agh.ics.opp.simulation.map.elements.animal.genome.generators;

import agh.ics.opp.simulation.types.MapDirection;
import agh.ics.opp.simulation.map.elements.animal.Animal;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GenomeGenerator {
    private final int genomeLength;

    public GenomeGenerator(int genomeLength){
        this.genomeLength = genomeLength;
    }

    public int[] getRandomGenome(){
        return Arrays.stream(new int[genomeLength]).map(a -> MapDirection.getRandom().ordinal()).toArray();
    }

    public int[] getDescendantGenome(Animal parent1, Animal parent2){
        int parent1stSide = ThreadLocalRandom.current().nextInt(0, 2);
        int parent1NumOfGenes = (int) Math.floor((double)parent1.getEnergy() * genomeLength / (parent1.getEnergy()+parent2.getEnergy()) + 0.5);
        int parent2NumOfGenes = genomeLength - parent1NumOfGenes;

        int[] descendantGenome = new int[genomeLength];
        if (parent1stSide == 0){
            System.arraycopy(parent1.getGenome(), 0, descendantGenome, 0, parent1NumOfGenes);
            System.arraycopy(parent2.getGenome(), parent1NumOfGenes, descendantGenome, parent1NumOfGenes, parent2NumOfGenes);
        }
        else {
            System.arraycopy(parent2.getGenome(), 0, descendantGenome, 0, parent2NumOfGenes);
            System.arraycopy(parent1.getGenome(), parent2NumOfGenes, descendantGenome, parent2NumOfGenes, parent1NumOfGenes);
        }
        return descendantGenome;
    }
}
