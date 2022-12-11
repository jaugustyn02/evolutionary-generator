package agh.ics.opp;

import agh.ics.opp.variants.behaviours.ChaoticSelector;
import agh.ics.opp.variants.behaviours.IGeneSelector;
import agh.ics.opp.variants.behaviours.OrderlySelector;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;
import agh.ics.opp.variants.maps.IAnimalPositionCorrector;
import agh.ics.opp.variants.maps.IWorldMap;
import agh.ics.opp.variants.mutations.BoundedMutator;
import agh.ics.opp.variants.mutations.IGenomeMutator;
import agh.ics.opp.variants.mutations.UnboundedMutator;
import agh.ics.opp.variants.preferences.AntiToxicLocator;
import agh.ics.opp.variants.preferences.EquatorialLocator;
import agh.ics.opp.variants.preferences.IPlantPlacementLocator;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MapUpdater {
    private final IWorldMap map;
    private final SimulationSetup setup;
    private final IGeneSelector geneSelector;
    private final IGenomeMutator genomeMutator;
    private final IPlantPlacementLocator plantPlacementLocator;

    public MapUpdater(IWorldMap map, SimulationSetup setup){
        this.map = map;
        this.setup = setup;
        this.geneSelector = (setup.animalBehaviorsVariant() ? new ChaoticSelector(setup.genomeLength()):
                                                              new OrderlySelector(setup.genomeLength()));
        this.genomeMutator = (setup.mutationsVariant() ? new BoundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()):
                                                         new UnboundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()));
        this.plantPlacementLocator = (setup.plantPreferencesVariant() ? new AntiToxicLocator(map) : new EquatorialLocator(map));
        this.initMap();
    }

    private void initMap(){
        generateAnimals();
        generatePlants(setup.initialNumOfPlants());
    }

    public void nextDay(){
        removeCorpses();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        growPlants();
        reduceEnergy();
    }

    private void growPlants(){
        generatePlants(setup.numOfPlantsPerDay());
    }

    private void removeCorpses(){
        for(Animal animal: map.getAnimals()){
            if (animal.getEnergy() == 0){
               map.removeMapElement(animal.getPosition(), animal);
            }
        }
    }

    private void moveAnimals(){
        for (Animal animal: map.getAnimals()){
            animal.makeMove();
        }
    }

    private void feedAnimals(){
        for (Vector2d position: map.getEatingPositions()){
            map.getTopAnimalAt(position).increaseEnergy(setup.plantEnergy());
            map.removeMapElement(position, map.getPlantAt(position));
        }
    }

    private void breedAnimals(){

    }

    private void reduceEnergy(){
        for (Animal animal: map.getAnimals()){
            animal.reduceEnergy(1);
        }
    }

    private void generateAnimals(){
        for (int i=0; i < setup.initialNumOfAnimals(); i++){
            Animal animal = new Animal(getRandomAnimalPosition(), setup.initialAnimalEnergy(), getRandomGenome(), (IAnimalPositionCorrector) map, geneSelector, genomeMutator);
            animal.addObserver((IAnimalMovementObserver) map);
            map.placeMapElement(animal);
        }
    }

    private void generatePlants(int NumOfPlants){
        for (int i = 0; i < NumOfPlants; i++){
            IMapElement plant = new Plant(plantPlacementLocator.getNewPlantPosition());
            map.placeMapElement(plant);
        }
    }

    private int[] getRandomGenome(){
        return Arrays.stream(new int[setup.genomeLength()]).map(a -> MapDirection.getRandom().ordinal()).toArray();
    }

    private Vector2d getRandomAnimalPosition(){
        Vector2d position;
        do{
            position = Vector2d.getRandom(map.getLowerLeft(), map.getUpperRight());
        }while (map.isAnimalAt(position));
        return position;
    }
}
