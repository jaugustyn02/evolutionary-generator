package agh.ics.opp.simulation.map.elements.animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if(o1 == o2) return 0;
        if (!Objects.equals(o1.getEnergy(), o2.getEnergy()))
            return -Integer.compare(o1.getEnergy(), o2.getEnergy());
        if (!Objects.equals(o1.getAge(), o2.getAge()))
            return -Integer.compare(o1.getAge(), o2.getAge());
        if (!Objects.equals(o1.getNumOfDescendants(), o2.getNumOfDescendants()))
            return -Integer.compare(o1.getNumOfDescendants(), o2.getNumOfDescendants());

        // OTHER NECESSARY COMPARES TO BE ABLE TO FIND EXACT OBJECT OR OBJECT THAT IS IDENTICAL
        if (!Objects.equals(o1.getNumOfEatenPlants(), o2.getNumOfEatenPlants()))
            return -Integer.compare(o1.getNumOfEatenPlants(), o2.getNumOfEatenPlants());
        if (!Objects.equals(o1.getDirection(), o2.getDirection()))
            return o1.getDirection().compareTo(o2.getDirection());
        if (!Objects.equals(o1.getNextGeneIndex(), o2.getNextGeneIndex()))
            return Integer.compare(o1.getNextGeneIndex(), o2.getNextGeneIndex());
        if (!Arrays.equals(o1.getGenome(), o2.getGenome()))
            return Arrays.compare(o1.getGenome(), o2.getGenome());
        return 0;
    }
}
