package agh.ics.opp.generators;

import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Plant;
import agh.ics.opp.maps.IWorldMap;
import java.util.concurrent.ThreadLocalRandom;

import java.util.*;

public class AntiToxicGenerator extends AbstractPlantGenerator implements IAnimalDeathObserver{
//                 <position, numOfDeaths>
    final private Map<Vector2d, Integer> deaths = new HashMap<>();
    final private TreeSet<Vector2d> emptySquares;
    final private int plantEnergy;
    private int numOfNonZeroDeathsEmptySquares = 0;

    public AntiToxicGenerator(IWorldMap map, int plantEnergy) {
        this.plantEnergy = plantEnergy;

        emptySquares = new TreeSet<>((new SquareComparator(this)));
        for(int x=0; x < map.getWidth(); x++){
            for(int y=0; y < map.getHeight(); y++){
                Vector2d position = new Vector2d(x, y);
                deaths.put(position, 0);
                emptySquares.add(position);
            }
        }
    }

    @Override
    public Plant generatePlant() {
        if(emptySquares.isEmpty()) return null;
        int randomIndex;
        int preferableSquaresSize = (int)Math.floor((emptySquares.size()*0.2)+0.5); // 20% of emptySquares rounded
        preferableSquaresSize = Math.min(preferableSquaresSize, numOfNonZeroDeathsEmptySquares);
        // 80% chance that plant will grow on preferable position (around 20% of map)
        if(preferableSquaresSize > 0 && ThreadLocalRandom.current().nextInt(0, 5) != 0){
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
        if(deaths.get(plant.getPosition()) != 0)
            numOfNonZeroDeathsEmptySquares++;
    }
    @Override
    public void plantPlaced(Plant plant){
        emptySquares.remove(plant.getPosition());
        if(deaths.get(plant.getPosition()) != 0)
            numOfNonZeroDeathsEmptySquares--;
    }
    @Override
    public void animalDiedAt(Vector2d position) {
        deaths.merge(position, 1, Integer::sum);
    }
    public Integer getNumOfDeaths(Vector2d position){
        return deaths.get(position);
    }
}
