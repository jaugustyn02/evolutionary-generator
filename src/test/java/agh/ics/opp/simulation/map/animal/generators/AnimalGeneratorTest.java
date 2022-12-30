package agh.ics.opp.simulation.map.animal.generators;

import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.behaviours.ChaoticSelector;
import agh.ics.opp.simulation.map.elements.animal.genome.generators.AnimalGenerator;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnimalGeneratorTest {
    @Test
    public void testGenerateRandomGenome() {
        Vector2d mapLowerLeft = new Vector2d(0, 0);
        Vector2d mapUpperRight = new Vector2d(10, 10);

        SimulationSetup setup = new SimulationSetup(false, 11, 11,
                false, 0, 0, 0, false,
                0, 10, 20, 1, 2,
                false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        AnimalGenerator animalGenerator = new AnimalGenerator(mapLowerLeft, mapUpperRight, map, setup);

        Animal adultAnimal = animalGenerator.generateAdultAnimal();
        assertNotNull(adultAnimal.getGenome());
    }
    @Test
    public void testChild(){
        SimulationSetup setup = new SimulationSetup(false, 11, 11,
                false, 0, 0, 0, false,
                0, 10, 20, 1, 2,
                false, 0, 0);
        GlobeMap map = new GlobeMap(setup);
        ChaoticSelector selector = new ChaoticSelector(10);
        Vector2d mapLowerLeft = new Vector2d(0, 0);
        Vector2d mapUpperRight = new Vector2d(10, 10);
        AnimalGenerator animalGenerator = new AnimalGenerator(mapLowerLeft, mapUpperRight, map, setup);
        Animal parent1 = new Animal(new Vector2d(0, 0), 50, 100, 1, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, map, selector);
        Animal parent2 = new Animal(new Vector2d(0, 0), 50, 100, 1, new int[] {11, 12, 13, 14, 15, 16, 17, 18, 19, 20}, map, selector);

        Animal descendantAnimal = animalGenerator.generateDescendantAnimal(parent1, parent2);
        assertNotNull(descendantAnimal.getGenome());
        assertNotEquals(parent1.getGenome(), descendantAnimal.getGenome());
        assertNotEquals(parent2.getGenome(), descendantAnimal.getGenome());
    }

}
