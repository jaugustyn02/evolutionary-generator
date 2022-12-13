package agh.ics.opp.variants.preferences;

import agh.ics.opp.Vector2d;
import agh.ics.opp.variants.maps.IWorldMap;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EquatorialLocator implements IPlantPlacementLocator {
    private final Vector2d equatorLowerLeft, equatorUpperRight;
    final private IWorldMap map;

    public EquatorialLocator(IWorldMap map) {
        this.map = map;
        // lowerLeft.y - 40% height
        this.equatorLowerLeft = new Vector2d(map.getLowerLeft().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.4));
        //upperRight.y - 60% height
        this.equatorUpperRight = new Vector2d(map.getUpperRight().x, (int)((map.getUpperRight().y-map.getLowerLeft().y)*0.6));

    }

    private Vector2d getRandomPositionFromArea(Vector2d lowerLeft, Vector2d upperRight){
        Vector2d position;
        do{
            int randomX = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x);
            int randomY = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y);
            position = new Vector2d(randomX, randomY);
        } while (map.isOccupied(position));
        return position;
    }

    public Vector2d getNewPlantPosition(){
        if (ThreadLocalRandom.current().nextInt(0, 5) == 0){ // 20% chance to get position from hole map

            return new Random().nextBoolean() ?
                    getRandomPositionFromArea(map.getLowerLeft(), new Vector2d(map.getUpperRight().x, equatorLowerLeft.y-1)) :
                    getRandomPositionFromArea(new Vector2d(map.getLowerLeft().x, equatorUpperRight.y+1), map.getUpperRight());
        }
        else {  // 80% chance to get position from equator
            return getRandomPositionFromArea(equatorLowerLeft, equatorUpperRight);
        }
    }
}

