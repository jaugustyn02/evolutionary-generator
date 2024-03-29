package agh.ics.opp.simulation.map.animal;

import agh.ics.opp.simulation.map.IAnimalPositionCorrector;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.behaviours.IGeneSelector;
import agh.ics.opp.simulation.types.MapDirection;
import agh.ics.opp.simulation.types.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private static final int[] GENOME = {1, 2, 3, 4, 5};
    private static final int ENERGY_CONSUMPTION = 5;
    private static final int FULL_ENERGY = 10;
    private static final int INIT_ENERGY = 10;
    private static final Vector2d POSITION = new Vector2d(1, 2);
    private static final IAnimalPositionCorrector CORRECTOR = animal -> {
    };
    private static final IGeneSelector SELECTOR = currentGeneIndex -> (currentGeneIndex + 1) % GENOME.length;

    private final Animal animal = new Animal(POSITION, INIT_ENERGY, FULL_ENERGY, ENERGY_CONSUMPTION, GENOME, CORRECTOR, SELECTOR);

    @Test
    void shouldReduceEnergy() {
        animal.reduceEnergy(1);
        assertEquals(9, animal.getEnergy());
    }

    @Test
    void increaseMaxEnergy() {
        animal.increaseEnergy(1);
        assertEquals(10, animal.getEnergy());
    }

    @Test
    void shouldBeBreed() {
        assertTrue(animal.isBreedable());
        animal.reduceEnergy(1);
        assertFalse(animal.isBreedable());
    }
    @Test
    public void makeMoveTest() {

        Animal animal = new Animal(new Vector2d(2,2), 10, 20, 1, new int[] {0,0,0}, CORRECTOR, SELECTOR);
        animal.setDirection(MapDirection.NORTH);
        animal.makeMove();

        assertEquals(new Vector2d(2,3), animal.getPosition());
    }
    @Test
    public void testMakeMove() {
        Animal animal = new Animal(new Vector2d(5,5), 10, 10, 1, new int[]{1,1}, CORRECTOR,SELECTOR );
        animal.setDirection(MapDirection.NORTH);
        animal.makeMove();
        assertEquals(new Vector2d(6, 6), animal.getPosition());
        assertEquals(MapDirection.NORTH_EAST, animal.getDirection());


    }
    @Test
    public void turnBackTest() {
        Animal animal = new Animal(new Vector2d(2,2), 10, 20, 1, new int[] {0,0}, CORRECTOR, SELECTOR);
        animal.setDirection(MapDirection.SOUTH);
        animal.turnBack();
        assertEquals(MapDirection.NORTH, animal.getDirection());
    }
    @Test
    public void hasLivedDayTest() {

        Animal animal = new Animal(new Vector2d(0,0), 10, 10, 2, new int[]{0,0,0,0,0}, CORRECTOR, SELECTOR);

        animal.hasLivedDay();

        assertEquals(9, animal.getEnergy());
        assertEquals(1, animal.getAge());
    }

    @Test
    public void hasBredTest() {

        Animal animal = new Animal(new Vector2d(0,0), 10, 10, 2, new int[]{0,0,0,0,0}, CORRECTOR, SELECTOR);
        animal.hasBred();

        assertEquals(8, animal.getEnergy());
        assertEquals(1, animal.getNumOfDescendants());
    }
    @Test
    public void testHasEaten() {
        Animal animal = new Animal(new Vector2d(0, 0), 2, 10, 1, new int[]{0,0,0}, CORRECTOR, SELECTOR);

        animal.hasEaten(5);

        assertEquals(7, animal.getEnergy());

    }
}