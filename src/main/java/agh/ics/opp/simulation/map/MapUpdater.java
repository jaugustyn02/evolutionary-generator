package agh.ics.opp.simulation.map;

import agh.ics.opp.simulation.map.elements.animal.AnimalBreeder;
import agh.ics.opp.simulation.map.elements.animal.genome.generators.AnimalGenerator;
import agh.ics.opp.simulation.map.elements.plant.generators.*;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.types.UpdatableTreeSet;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;

import java.util.*;

public class MapUpdater implements IMapUpdater {
    private final IWorldMap map;
    private final SimulationSetup setup;
    private final IPlantGenerator plantGenerator;
    private final AnimalGenerator animalGenerator;
    private final AnimalBreeder animalBreeder;
    private final List<IAnimalDeathObserver> animalDeathObservers = new LinkedList<>();

    public MapUpdater(IWorldMap map, SimulationSetup setup) {
        this.map = map;
        this.setup = setup;
        this.plantGenerator = (setup.plantPreferencesVariant() ?
                new AntiToxicGenerator(map, setup.plantEnergy()) :      // true
                new EquatorialGenerator(map, setup.plantEnergy()));    // false
        if (setup.plantPreferencesVariant()) this.addAnimalDeathObserver((IAnimalDeathObserver) plantGenerator);
        this.animalGenerator = new AnimalGenerator(map.getLowerLeft(), map.getUpperRight(), (IAnimalPositionCorrector) map, setup);
        ((IMapObserversHandler) map).addPlantObserver((IPlantPlacementObserver) plantGenerator);
        this.animalBreeder = new AnimalBreeder(animalGenerator);
        this.initializeMap();
    }

    public void initializeMap() {
        generateAnimals(setup.initialNumOfAnimals());
        generatePlants(setup.initialNumOfPlants());
    }

    public void nextDay() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        growPlants();
        endTheDay();
    }

    private void removeDeadAnimals() {
        for (Vector2d position : map.getAnimalsPositions()) {
            Animal animal = map.getLastAnimalAt(position);
            while (animal != null && animal.getEnergy() <= 0) {
                map.popLastAnimalAt(position);
                animalDiedAt(position);
                animal = map.getLastAnimalAt(position);
            }
        }
    }

    private void moveAnimals() {
        for (Animal animal : new ArrayList<>(map.getAnimalsSet())) {
            map.removeMapElement(animal);
            animal.makeMove();
            map.placeMapElement(animal);
        }
    }

    private void feedAnimals() {
        for (Vector2d position : map.getPlantsPositions()) {
            if (map.isAnimalAt(position)) {
                Animal animal = map.popFirstAnimalAt(position);
                animal.hasEaten(map.getPlantAt(position).getEnergy());
                map.placeMapElement(animal);
                map.removeMapElement(map.getPlantAt(position));
            }
        }
    }

    private void breedAnimals() {
        List<Animal> animalsToPlaceOnMap = new LinkedList<>();
        for (Vector2d position : map.getAnimalsPositions()) {
            Animal parent2 = map.getSecondAnimalAt(position);
            while (parent2 != null && parent2.isBreedable()) {
                Animal parent1 = map.popFirstAnimalAt(position);
                map.popFirstAnimalAt(position);
                Animal descendant = animalBreeder.breed2Animals(parent1, parent2);

                animalsToPlaceOnMap.add(parent2);
                animalsToPlaceOnMap.add(parent1);
                animalsToPlaceOnMap.add(descendant);

                parent2 = map.getSecondAnimalAt(position);
            }
        }
        for (Animal animal : animalsToPlaceOnMap) {
            map.placeMapElement(animal);
        }
    }

    private void growPlants() {
        generatePlants(setup.numOfPlantsPerDay());
    }

    private void endTheDay() {
        for (UpdatableTreeSet animalsSet : map.getAnimalsMap().values()) {
            for (Animal animal : animalsSet) {
                animal.hasLivedDay();
            }
            animalsSet.updateOrder();
        }
    }

    // IAnimalDeathObserver
    private void animalDiedAt(Vector2d position) {
        for (IAnimalDeathObserver observer : animalDeathObservers) {
            observer.animalDiedAt(position);
        }
    }

    public void addAnimalDeathObserver(IAnimalDeathObserver observer) {
        animalDeathObservers.add(observer);
    }
    // end

    // helper methods
    private void generateAnimals(int numOfAnimals) {
        for (int i = 0; i < numOfAnimals; i++) {
            map.placeMapElement(animalGenerator.generateAdultAnimal());
        }
    }

    private void generatePlants(int NumOfPlants) {
        for (int i = 0; i < NumOfPlants; i++) {
            Plant plant = plantGenerator.generatePlant();
            if (plant == null) break;
            map.placeMapElement(plant);
        }
    }
    // end
}
