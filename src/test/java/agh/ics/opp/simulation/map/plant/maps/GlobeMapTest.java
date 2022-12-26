package agh.ics.opp.simulation.map.plant.maps;
import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.behaviours.ChaoticSelector;
import agh.ics.opp.simulation.map.elements.animal.behaviours.OrderlySelector;
import agh.ics.opp.simulation.types.MapDirection;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobeMapTest {
    @Test
    public void testTurnBack() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 10, 20, 1, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        Animal animal = new Animal( new Vector2d(50, 100), 10, 20, 1, new int[] {0}, map, new OrderlySelector(7));
        animal.setDirection(MapDirection.SOUTH);
        map.correctAnimalPosition(animal);
        assertEquals(MapDirection.NORTH, animal.getDirection());
    }
    @Test
    public void testWestWrapAround() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        Animal animal = new Animal( new Vector2d(-1, 50), 0, 0, 0, new int[] {0}, map, new OrderlySelector(7));
        animal.setDirection(MapDirection.WEST);
        map.correctAnimalPosition(animal);
        assertEquals(new Vector2d(99, 50), animal.getPosition());
    }
    @Test
    public void testEastWrapAround() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        Animal animal = new Animal( new Vector2d(100, 40), 0, 0, 0, new int[] {0}, map, new ChaoticSelector(6));
        animal.setDirection(MapDirection.NORTH_EAST);
        map.correctAnimalPosition(animal);
        assertEquals(0, animal.getPosition().x);
    }

}
