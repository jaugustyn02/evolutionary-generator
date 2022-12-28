
package agh.ics.opp.simulation.types;

import java.util.Arrays;

public record SimulationStatistics (
        int numOfAnimals,
        int numOfPlants,
        int numOfEmptySquares,
        int[] mostPopularGenome,
        double avgAnimalEnergy,
        double avgAnimalLifeSpan
){
    @Override
    public String toString(){
        return "numOfAnimals: " + numOfAnimals + "\n" +
               "numOfPlants: " + numOfPlants + "\n" +
               "numOfEmptySquares: " + numOfEmptySquares + "\n" +
               "mostPopularGenome: " + Arrays.toString(mostPopularGenome) + "\n" +
               "avgAnimalEnergy: " + avgAnimalEnergy + "\n" +
               "avgAnimalLifeSpan: " + avgAnimalLifeSpan + "\n";
    }
}

