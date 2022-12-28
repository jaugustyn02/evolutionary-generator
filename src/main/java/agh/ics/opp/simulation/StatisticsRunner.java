
package agh.ics.opp.simulation;

import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.types.SimulationStatistics;
import agh.ics.opp.simulation.types.Vector2d;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatisticsRunner {
    final IWorldMap map;
    int numOfAnimals = 0;
    int numOfPlants = 0;
    int numOfEmptySquares = 0;
    int[] mostPopularGenome = null;
    double avgAnimalEnergy;
    double avgAnimalLifeSpan;

    int numOfDeadAnimals = 0;
    int sumOfDeadAnimalsLifeSpan = 0;

    public StatisticsRunner(IWorldMap map) {
        this.map = map;
    }

    public SimulationStatistics getStatistics(){
        return new SimulationStatistics(
                numOfAnimals,
                numOfPlants,
                numOfEmptySquares,
                mostPopularGenome,
                avgAnimalEnergy,
                avgAnimalLifeSpan
        );
    }

    public void updateStatistics(){
        Set<Animal> animals = map.getAnimalsSet();
        numOfAnimals = animals.size();
        numOfPlants = map.getPlantsPositions().size();
        numOfEmptySquares = (int)IntStream.range(0, map.getWidth())
                .boxed()
                .flatMap(x -> IntStream.range(0, map.getHeight())
                        .mapToObj(y -> new Vector2d(x, y))
                ).filter(v -> !map.isOccupied(v)).count();
        mostPopularGenome = animals.stream().collect(Collectors.groupingBy(Animal::getGenome, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
        avgAnimalEnergy = animals.stream().mapToInt(Animal::getEnergy)
                .summaryStatistics().getAverage();
        numOfDeadAnimals += animals.stream().filter(animal -> animal.getEnergy() == 0).count();
        sumOfDeadAnimalsLifeSpan += animals.stream().filter(animal -> animal.getEnergy() == 0).mapToInt(Animal::getAge).sum();
        avgAnimalLifeSpan = (double) sumOfDeadAnimalsLifeSpan / numOfDeadAnimals;
    }
    public int getNumOfAnimals(){
        return numOfAnimals;
    }
    public int getNumOfPlants(){
        return numOfPlants;
    }
}

