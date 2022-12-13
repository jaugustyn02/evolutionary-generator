package agh.ics.opp;

import agh.ics.opp.variants.behaviours.ChaoticSelector;
import agh.ics.opp.variants.behaviours.IGeneSelector;
import agh.ics.opp.variants.behaviours.OrderlySelector;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;
import agh.ics.opp.variants.maps.IAnimalMovementObserver;
import agh.ics.opp.variants.maps.IAnimalPositionCorrector;
import agh.ics.opp.variants.maps.IWorldMap;
import agh.ics.opp.variants.mutations.BoundedMutator;
import agh.ics.opp.variants.mutations.IGenomeMutator;
import agh.ics.opp.variants.mutations.UnboundedMutator;
import agh.ics.opp.variants.preferences.AntiToxicLocator;
import agh.ics.opp.variants.preferences.EquatorialLocator;
import agh.ics.opp.variants.preferences.IPlantPlacementLocator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MapUpdater implements IMapUpdater{
    private final IWorldMap map;
    private final SimulationSetup setup;
    private final IGeneSelector geneSelector;
    private final IGenomeMutator genomeMutator;
    private final IPlantPlacementLocator plantPlacementLocator;

    public MapUpdater(IWorldMap map, SimulationSetup setup){
        this.map = map;
        this.setup = setup;
        this.geneSelector = (setup.animalBehaviorsVariant() ?
                new ChaoticSelector(setup.genomeLength()):  // true
                new OrderlySelector(setup.genomeLength())); // false
        this.genomeMutator = (setup.mutationsVariant() ?
                new BoundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()):       // true
                new UnboundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()));    // false
        this.plantPlacementLocator = (setup.plantPreferencesVariant() ?
                new AntiToxicLocator(map):      // true
                new EquatorialLocator(map));    // false
        this.initializeMap();
    }

    private void initializeMap(){
        generateAnimals();
        generatePlants(setup.initialNumOfPlants());
    }

    public void nextDay(){
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        growPlants();
        endOfDay();
    }

    private void removeDeadAnimals(){
        for(Animal animal: map.getAnimals()){
            if (animal.getEnergy() < 1){
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
        List<Animal> animalsToPlaceOnMap = new ArrayList<>();
        for (Vector2d position: map.getAnimalsPositions()) {
            Animal parent1 = map.popTopAnimalAt(position);
            Animal parent2 = map.popTopAnimalAt(position);
            while (parent2 != null && parent2.isBreedable()) {
                Animal descendant = breed2Animals(parent1, parent2);
                animalsToPlaceOnMap.add(parent1);
                animalsToPlaceOnMap.add(parent2);
                if(descendant != null)
                    animalsToPlaceOnMap.add(descendant);
                parent1 = map.popTopAnimalAt(position);
                parent2 = map.popTopAnimalAt(position);
            }
            if (parent1 != null)
                animalsToPlaceOnMap.add(parent1);
        }
        for (Animal animal: animalsToPlaceOnMap){
            map.placeMapElement(animal);
        }
    }

    private void growPlants(){
        generatePlants(setup.numOfPlantsPerDay());
    }

    private void endOfDay(){
        for (Animal animal: map.getAnimals()){
            animal.hasLivedDay();
        }
    }


    // helper methods
    private void generateAnimals(){
        for (int i=0; i < setup.initialNumOfAnimals(); i++){
            Animal animal = new Animal(getRandomAnimalPosition(), setup.initialAnimalEnergy(), setup.fullAnimalEnergy(),
                    setup.animalEnergyConsumption(), getRandomGenome(), (IAnimalPositionCorrector) map, geneSelector);
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

    // parent1.energy >= parent2.energy
    private int[] getDescendantGenome(Animal strongerParent, Animal weakerParent){
        int strongerSide = ThreadLocalRandom.current().nextInt(0, 2);
        int strongerNumOfGenes = (int) Math.floor((double)strongerParent.getEnergy() * setup.genomeLength() / (strongerParent.getEnergy()+weakerParent.getEnergy()) + 0.5);
        int weakerNumOfGenes = setup.genomeLength() - strongerNumOfGenes;

        int[] descendantGenome = new int[setup.genomeLength()];
        if (strongerSide == 0){
            System.arraycopy(strongerParent.getGenome(), 0, descendantGenome, 0, strongerNumOfGenes);
            System.arraycopy(weakerParent.getGenome(), strongerNumOfGenes, descendantGenome, strongerNumOfGenes, weakerNumOfGenes);
        }
        else {
            System.arraycopy(weakerParent.getGenome(), 0, descendantGenome, 0, weakerNumOfGenes);
            System.arraycopy(strongerParent.getGenome(), weakerNumOfGenes, descendantGenome, weakerNumOfGenes, strongerNumOfGenes);
        }
        return descendantGenome;
    }

    private Animal breed2Animals(Animal strongerParent, Animal weakerParent){
        if(!strongerParent.isBreedable() || !weakerParent.isBreedable()){
            System.out.println("At least one parent is not breedable");
            return null;
        }
        strongerParent.hasBred();
        weakerParent.hasBred();
        return new Animal(strongerParent.getPosition(), (setup.animalEnergyConsumption()*2), setup.fullAnimalEnergy(),
                setup.animalEnergyConsumption(), genomeMutator.getMutatedGenome(getDescendantGenome(strongerParent, weakerParent)),
                (IAnimalPositionCorrector)map, geneSelector);
    }
    // helper methods end
}
