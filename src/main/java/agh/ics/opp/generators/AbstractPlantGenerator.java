package agh.ics.opp.generators;

import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Plant;

import java.util.Iterator;
import java.util.Set;

abstract public class AbstractPlantGenerator implements IPlantGenerator, IPlantObserver{
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
