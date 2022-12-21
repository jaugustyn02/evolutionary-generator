package agh.ics.opp.simulation.types;

import agh.ics.opp.simulation.map.elements.animal.Animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class UpdatableTreeSet extends TreeSet<Animal>{
    public UpdatableTreeSet(Comparator<Animal> comparator){
        super(comparator);
    }

    public void updateOrder(){
        List<Animal> objects = new ArrayList<>(this);
        this.clear();
        this.addAll(objects);
    }
}
