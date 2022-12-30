package agh.ics.opp.simulation.map.elements.plant.generators;

import agh.ics.opp.simulation.types.Vector2d;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.IWorldMap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class EquatorialGenerator extends AbstractPlantGenerator {
    private final Set<Vector2d> emptySquaresAround;
    private final Set<Vector2d> emptySquaresEquator;

    private final Vector2d equatorLowerLeft, equatorUpperRight;
    private final int plantEnergy;

    public EquatorialGenerator(IWorldMap map, int plantEnergy) {
        this.plantEnergy = plantEnergy;
        // lowerLeft.y - 40% height
        this.equatorLowerLeft = new Vector2d(map.getLowerLeft().x, (int) ((map.getUpperRight().y - map.getLowerLeft().y) * 0.4));
        //upperRight.y - 60% height
        this.equatorUpperRight = new Vector2d(map.getUpperRight().x, (int) ((map.getUpperRight().y - map.getLowerLeft().y) * 0.6));

        emptySquaresAround = new HashSet<>();
        emptySquaresEquator = new HashSet<>();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < equatorLowerLeft.y; y++)
                emptySquaresAround.add(new Vector2d(x, y));
            for (int y = equatorLowerLeft.y; y <= equatorUpperRight.y; y++)
                emptySquaresEquator.add(new Vector2d(x, y));
            for (int y = equatorUpperRight.y + 1; y < map.getHeight(); y++)
                emptySquaresAround.add(new Vector2d(x, y));
        }
    }

    @Override
    public Plant generatePlant() {
        if (mapIsFull()) return null;
        Vector2d position;
        if (emptySquaresEquator.isEmpty() || ThreadLocalRandom.current().nextInt(0, 5) == 0) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, emptySquaresAround.size());
            position = getPositionFromSet(emptySquaresAround, randomIndex);
        } else {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, emptySquaresEquator.size());
            position = getPositionFromSet(emptySquaresEquator, randomIndex);
        }
        return new Plant(position, plantEnergy);
    }

    private boolean mapIsFull() {
        return emptySquaresAround.isEmpty() && emptySquaresEquator.isEmpty();
    }

    @Override
    public void plantRemoved(Plant plant) {
        Vector2d position = plant.getPosition();
        if (position.follows(equatorLowerLeft) && position.precedes(equatorUpperRight)) {
            emptySquaresEquator.add(position);
        } else {
            emptySquaresAround.add(position);
        }
    }

    @Override
    public void plantPlaced(Plant plant) {
        Vector2d position = plant.getPosition();
        if (position.follows(equatorLowerLeft) && position.precedes(equatorUpperRight)) {
            emptySquaresEquator.remove(position);
        } else {
            emptySquaresAround.remove(position);
        }
    }

    public Set<Vector2d> getEmptySquaresEquator() {
        return emptySquaresEquator;
    }

    public Set<Vector2d> getEmptySquaresAround() {
        return emptySquaresAround;
    }

}