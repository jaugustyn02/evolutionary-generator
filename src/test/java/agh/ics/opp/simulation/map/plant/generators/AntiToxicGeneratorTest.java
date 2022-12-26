package agh.ics.opp.simulation.map.plant.generators;

import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.elements.plant.generators.AntiToxicGenerator;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AntiToxicGeneratorTest {
    @Test
    public void testGeneratePlant() {

        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        int plantEnergy = 10;
        AntiToxicGenerator generator = new AntiToxicGenerator(map, plantEnergy);


        Plant plant = generator.generatePlant();


        assertNotNull(plant);
        assertEquals(plantEnergy, plant.getEnergy());
    }
//    @Test
//    public void testPlantRemoved() {
//
//        int mapH = 10;
//        int mapW = 10;
//        SimulationSetup setup = new SimulationSetup(false, mapH, mapW, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
//        GlobeMap map = new GlobeMap(setup);
//        int plantEnergy = 10;
//        AntiToxicGenerator generator = new AntiToxicGenerator(map, plantEnergy);
//        Plant plant = new Plant(new Vector2d(1, 1), plantEnergy);
//
//        generator.plantRemoved(plant);
//
//        assertTrue(generator.getEmptySquares().contains(plant.getPosition()));
//        assertEquals(1,mapH*mapW - generator.getNumOfZeroDeathsEmptySquares());
//    }

//    @Test
//    public void testPlantPlaced() {
//        // given
//        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
//        GlobeMap map = new GlobeMap(setup);
//        int plantEnergy = 10;
//        AntiToxicGenerator generator = new AntiToxicGenerator(map, plantEnergy);
//        Plant plant = new Plant(new Vector2d(1, 1), plantEnergy);
//        generator.plantRemoved(plant);
//
//        // when
//        generator.plantPlaced(plant);
//
//        // then
//        assertFalse(generator.getEmptySquares().contains(plant.getPosition()));
//        assertEquals(0, generator.getNumOfZeroDeathsEmptySquares());
//    }

//    @Test
//    public void testAnimalDiedAt() {
//        // given
//        SimulationSetup setup = new SimulationSetup(false, 100, 100, false, 0, 0, 0, false, 0, 0, 0, 0, 0, false, 0, 0);
//        GlobeMap map = new GlobeMap(setup);;
//        int plantEnergy = 10;
//        AntiToxicGenerator generator = new AntiToxicGenerator(map, plantEnergy);
//        Vector2d position = new Vector2d(1, 1);
//        generator.plantRemoved(new Plant(position, plantEnergy));
//
//        // when
//        generator.animalDiedAt(position);
//
//        // then
//        //assertEquals(1, generator.getNumOfDeaths().get(position).intValue());
//        assertEquals(0, generator.getNumOfZeroDeathsEmptySquares());
//    }

}
