package agh.ics.opp.elements;

import java.util.Comparator;
import java.util.Objects;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if (!Objects.equals(o1.getEnergy(), o2.getEnergy()))
            return -o1.getEnergy().compareTo(o2.getEnergy());
        if (!Objects.equals(o1.getAge(), o2.getAge()))
            return -o1.getAge().compareTo(o2.getAge());
        if (!Objects.equals(o1.getNumOfDescendants(), o2.getNumOfDescendants()))
            return -o1.getNumOfDescendants().compareTo(o2.getNumOfDescendants());
        return (o1 == o2 ? 0 : -1);
    }
}
