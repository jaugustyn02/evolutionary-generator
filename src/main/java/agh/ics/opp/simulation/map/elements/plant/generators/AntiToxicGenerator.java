package agh.ics.opp.simulation.map.elements.plant.generators;

import agh.ics.opp.simulation.types.Vector2d;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.IWorldMap;

import java.util.concurrent.ThreadLocalRandom;

import java.util.*;

public class AntiToxicGenerator extends AbstractPlantGenerator implements IAnimalDeathObserver{
    final private Map<Vector2d, Integer> numOfDeaths = new HashMap<>();
    final private TreeSet<Vector2d> emptySquares;
    final private int plantEnergy;
    private int numOfZeroDeathsEmptySquares;

    public AntiToxicGenerator(IWorldMap map, int plantEnergy) {
        this.plantEnergy = plantEnergy;

        emptySquares = new TreeSet<>((new SquareComparator(this)));
        for(int x=0; x < map.getWidth(); x++){
            for(int y=0; y < map.getHeight(); y++){
                Vector2d position = new Vector2d(x, y);
                numOfDeaths.put(position, 0);
                emptySquares.add(position);
            }
        }
        numOfZeroDeathsEmptySquares = emptySquares.size();
    }

    @Override
    public Plant generatePlant() {
        if(emptySquares.isEmpty()) return null;
        int randomIndex;
        int preferableSquaresSize = (int)Math.floor((emptySquares.size()*0.2)+0.5); // 20% of emptySquares rounded
        preferableSquaresSize = Math.max(preferableSquaresSize, numOfZeroDeathsEmptySquares);
        // 80% chance that plant will grow on preferable position (around 20% of map)
        if((preferableSquaresSize > 0 && ThreadLocalRandom.current().nextInt(0, 5) != 0) || preferableSquaresSize == emptySquares.size()){
            randomIndex = ThreadLocalRandom.current().nextInt(0, preferableSquaresSize);
        }
        // 20% chance that plant will grow on not preferable position (around 80% of map)
        else {
            randomIndex = ThreadLocalRandom.current().nextInt(preferableSquaresSize, emptySquares.size());
        }
        Vector2d position = getPositionFromSet(emptySquares, randomIndex);
        return new Plant(position, plantEnergy);
    }

    @Override
    public void plantRemoved(Plant plant) {
        emptySquares.add(plant.getPosition());
        if(numOfDeaths.get(plant.getPosition()) == 0)
            numOfZeroDeathsEmptySquares++;
    }
    @Override
    public void plantPlaced(Plant plant){
        emptySquares.remove(plant.getPosition());
        if(numOfDeaths.get(plant.getPosition()) == 0)
            numOfZeroDeathsEmptySquares--;
    }
    @Override
    public void animalDiedAt(Vector2d position) {
        if(numOfDeaths.get(position) == 0)
            numOfZeroDeathsEmptySquares--;
        numOfDeaths.merge(position, 1, Integer::sum);
    }
    public Integer getNumOfDeaths(Vector2d position){
        return numOfDeaths.get(position);
    }
}
