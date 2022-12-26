package agh.ics.opp.simulation.map.plant.generators;

import agh.ics.opp.simulation.map.HellPortalMap;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.elements.plant.generators.EquatorialGenerator;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquatorialGeneratorTest {
    @Test
    public void testPlantRemovedToAround() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        EquatorialGenerator generator = new EquatorialGenerator(map, 10);
        Vector2d position = new Vector2d(map.getLowerLeft().x + 1, map.getLowerLeft().y - 1);
        Plant plant = new Plant(position, 10);
        generator.plantRemoved(plant);
        assertTrue(generator.getEmptySquaresAround().contains(position));
    }
    @Test
    public void testPlantPlacedFromAround() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        EquatorialGenerator generator = new EquatorialGenerator(map, 10);
        Vector2d position = new Vector2d(map.getLowerLeft().x + 1, map.getLowerLeft().y - 1);
        Plant plant = new Plant(position, 10);
        generator.plantRemoved(plant);
        generator.plantPlaced(plant);
        assertFalse(generator.getEmptySquaresAround().contains(position));
    }
    @Test
    public void testPlantPlacedFromEquator() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        EquatorialGenerator generator = new EquatorialGenerator(map, 10);
        Vector2d position = new Vector2d(map.getLowerLeft().x + 1, map.getLowerLeft().y + 1);
        Plant plant = new Plant(position, 10);
        generator.plantRemoved(plant);
        generator.plantPlaced(plant);
        assertFalse(generator.getEmptySquaresEquator().contains(position));
    }
    @Test
    public void testGeneratePlantFromEquator() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        EquatorialGenerator generator = new EquatorialGenerator(map, 10);
        Plant plant = generator.generatePlant();
        assertTrue(generator.getEmptySquaresEquator().contains(plant.getPosition()));
    }
    @Test
    public void testGeneratePlantNull() {
        SimulationSetup setup = new SimulationSetup(true, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        HellPortalMap map = new HellPortalMap(setup);
        EquatorialGenerator generator = new EquatorialGenerator(map, 10);
        generator.getEmptySquaresAround().clear();
        generator.getEmptySquaresEquator().clear();
        Plant plant = generator.generatePlant();
        assertNull(plant);
    }
}
