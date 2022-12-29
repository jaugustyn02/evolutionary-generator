
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
        return "\nNumber of animals: " + numOfAnimals + "\n" +
                "Number of plants: " + numOfPlants + "\n" +
                "Number of empty squares: " + numOfEmptySquares + "\n" +
                "Most popular genome: " + Arrays.toString(mostPopularGenome) + "\n" +
                "Average animal energy: " + Math.round(avgAnimalEnergy*100.0)/100.0 + "\n" +
                "Average animal life span: " + Math.round(avgAnimalLifeSpan*100.0)/100.0 + "\n";
    }
}

