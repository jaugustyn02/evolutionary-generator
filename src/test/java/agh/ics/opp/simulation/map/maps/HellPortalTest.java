package agh.ics.opp.simulation.map.maps;

import agh.ics.opp.simulation.map.HellPortalMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.behaviours.OrderlySelector;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HellPortalTest {
    @Test
    public void testEast() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        Animal animal = new Animal( new Vector2d(101, 50), 0, 0, 0, new int[] {0},map, new OrderlySelector(8));
        map.correctAnimalPosition(animal);
        assertTrue(animal.getPosition().x <= map.getUpperRight().x && animal.getPosition().x >= map.getLowerLeft().x);
        assertTrue(animal.getPosition().y <= map.getUpperRight().y && animal.getPosition().y >= map.getLowerLeft().y);
    }
    @Test
    public void testSouth() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        Animal animal = new Animal( new Vector2d(50, -1), 0, 0, 0, new int[] {0},map, new OrderlySelector(8));
        map.correctAnimalPosition(animal);
        assertTrue(animal.getPosition().x <= map.getUpperRight().x && animal.getPosition().x >= map.getLowerLeft().x);
        assertTrue(animal.getPosition().y <= map.getUpperRight().y && animal.getPosition().y >= map.getLowerLeft().y);
    }
    @Test
    public void testCorner() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        Animal animal = new Animal( new Vector2d(101, 101), 0, 0, 0, new int[] {0}, map, new OrderlySelector(8));
        map.correctAnimalPosition(animal);
        assertTrue(animal.getPosition().x <= map.getUpperRight().x && animal.getPosition().x >= map.getLowerLeft().x);
        assertTrue(animal.getPosition().y <= map.getUpperRight().y && animal.getPosition().y >= map.getLowerLeft().y);
    }
}
