package agh.ics.opp.simulation.map.plant.generators;

import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlantTest {
    @Test
    public void testPositionCorrectly() {
        Vector2d position = new Vector2d(1, 2);
        Plant plant = new Plant(position, 10);
        assertEquals(position, plant.getPosition());
    }
    @Test
    public void testEnergyCorrectly() {
        Vector2d position = new Vector2d(1, 2);
        Plant plant = new Plant(position, 10);
        assertEquals(10, plant.getEnergy());
    }

}
