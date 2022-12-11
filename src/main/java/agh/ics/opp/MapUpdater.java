package agh.ics.opp;

import agh.ics.opp.variants.behaviours.ChaoticSelector;
import agh.ics.opp.variants.behaviours.IGeneSelector;
import agh.ics.opp.variants.behaviours.OrderlySelector;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;
import agh.ics.opp.variants.maps.IWorldMap;
import agh.ics.opp.variants.mutations.BoundedMutator;
import agh.ics.opp.variants.mutations.IGenomeMutator;
import agh.ics.opp.variants.mutations.UnboundedMutator;
import agh.ics.opp.variants.preferences.AntiToxicLocator;
import agh.ics.opp.variants.preferences.EquatorialLocator;
import agh.ics.opp.variants.preferences.IPlantPlacementLocator;

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

    private void removeCorpses(){}

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

    private void reduceEnergy(){}

    private void generateAnimals(){
        int[] genome = new int[]{2,3,7,0,5};
        Animal animal = new Animal(map, new Vector2d(2, 2), setup.initialAnimalEnergy(), genome, geneSelector, genomeMutator);
        animal.addObserver((IAnimalMovementObserver) map);
        map.placeMapElement(animal);

        int[] genome2 = new int[]{0,2,0,7,5};
        Animal animal2 = new Animal(map, new Vector2d(4, 4), setup.initialAnimalEnergy(), genome2, geneSelector, genomeMutator);
        animal2.addObserver((IAnimalMovementObserver) map);
        map.placeMapElement(animal2);
    }

    private void generatePlants(int NumOfPlants){
        for (int i = 0; i < NumOfPlants; i++){
            IMapElement plant = new Plant(plantPlacementLocator.getNewPlantPosition());
            map.placeMapElement(plant);
        }
    }
}
