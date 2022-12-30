package agh.ics.opp.simulation.map.plant.generators;
import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.elements.plant.generators.AntiToxicGenerator;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AntiToxicGeneratorTest {
    @Test
    public void testGeneratePlant() {

        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 1, false,
                0, 0, 0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        int plantEnergy = 10;
        AntiToxicGenerator generator = new AntiToxicGenerator(map, plantEnergy);
        Plant plant = generator.generatePlant();


        assertNotNull(plant);
        assertEquals(plantEnergy, plant.getEnergy());
    }

    @Test
    public void testNoPlaceForPlant() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100,
                false, 0, 0, 10, false,
                0, 0, 0, 0,
                0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);

        AntiToxicGenerator generator = new AntiToxicGenerator(map, 10);
        int before = generator.getNumOfZeroDeathsEmptySquares();
        generator.generatePlant();
        int after = generator.getNumOfZeroDeathsEmptySquares();
        assertEquals(before, after);
    }


    @Test
    public void testPlantPlaced() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        int plantEnergy = 10;
        AntiToxicGenerator generator = new AntiToxicGenerator(map, plantEnergy);
        Plant plant = new Plant(new Vector2d(1, 1), plantEnergy);
        generator.plantRemoved(plant);
        generator.plantPlaced(plant);
        assertFalse(generator.getEmptySquares().contains(plant.getPosition()));

    }

    @Test
    public void testPlantRemoved() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        AntiToxicGenerator generator = new AntiToxicGenerator(map, 10);
        Vector2d position = new Vector2d(1, 1);
        Plant plant = new Plant(position, 10);
        generator.plantRemoved(plant);
        assertTrue(generator.getEmptySquares().contains(position));
    }
    @Test
    public void testAnimalDiedAt() {
        SimulationSetup setup = new SimulationSetup(false, 100, 100, false,
                0, 0, 10, false, 0, 0,
                0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        AntiToxicGenerator generator = new AntiToxicGenerator(map, 10);
        Vector2d position = new Vector2d(1, 1);
        assertEquals(100*100, generator.getNumOfZeroDeathsEmptySquares());
        generator.animalDiedAt(position);
        assertEquals(1, (int) generator.getNumOfDeaths(position));
        assertEquals(100*100-1, generator.getNumOfZeroDeathsEmptySquares());
    }
}

