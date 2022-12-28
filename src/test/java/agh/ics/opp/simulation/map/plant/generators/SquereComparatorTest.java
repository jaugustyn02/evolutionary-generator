package agh.ics.opp.simulation.map.plant.generators;

import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.elements.plant.generators.AntiToxicGenerator;
import agh.ics.opp.simulation.map.elements.plant.generators.SquareComparator;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SquereComparatorTest {

    SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 10, 20, 1, 0, false, 0, 0);
    GlobeMap map = new GlobeMap(setup);
    @Test
    public void testCompareSameCoordinates() {

        AntiToxicGenerator generator = new AntiToxicGenerator(map, 10);
        SquareComparator comparator = new SquareComparator(generator);
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        assertEquals(0, comparator.compare(v1, v2));
    }
    @Test
    public void testCompareDeathsLess() {

        AntiToxicGenerator generator = new AntiToxicGenerator(map, 10);
        SquareComparator comparator = new SquareComparator(generator);
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(3, 4);
        generator.animalDiedAt(v2);
        assertTrue(comparator.compare(v1, v2) < 0);
    }
    @Test
    public void testCompareDeathsGreater() {

        AntiToxicGenerator generator = new AntiToxicGenerator(map, 10);
        SquareComparator comparator = new SquareComparator(generator);
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(3, 4);
        generator.animalDiedAt(v1);
        generator.animalDiedAt(v1);
        generator.animalDiedAt(v1);
        assertTrue(comparator.compare(v1, v2) > 0);
    }

}
