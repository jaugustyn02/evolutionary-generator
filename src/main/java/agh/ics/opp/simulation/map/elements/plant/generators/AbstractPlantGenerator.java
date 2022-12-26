package agh.ics.opp.simulation.map.elements.plant.generators;

import agh.ics.opp.simulation.types.Vector2d;
import agh.ics.opp.simulation.map.elements.plant.Plant;

import java.util.Iterator;
import java.util.Set;

abstract public class AbstractPlantGenerator implements IPlantGenerator, IPlantPlacementObserver {
    @Override
    abstract public Plant generatePlant();
    @Override
    abstract public void plantRemoved(Plant plant);
    @Override
    abstract public void plantPlaced(Plant plant);

    protected Vector2d getPositionFromSet(Set<Vector2d> set, int index){
        Iterator<Vector2d> it = set.iterator();
        while(index > 0 && it.hasNext()){
            it.next();
            index--;
        }
        return it.next();
    }
}
