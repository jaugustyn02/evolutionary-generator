package agh.ics.opp.simulation.map.animal;

import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.AnimalComparator;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalComparatorTest {
    @Test
    public void testCompare_comparesAnimalsByEnergyAgeAndNumOfDescendants() {
        Animal animal1 = new Animal(new Vector2d(0, 0), 100, 100, 10, new int[8], null, null);
        Animal animal2 = new Animal(new Vector2d(0, 0), 50, 100, 10, new int[8], null, null);
        AnimalComparator comparator = new AnimalComparator();
        assertEquals(-1, comparator.compare(animal1, animal2));

    }
}
