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

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MapUpdater implements IMapUpdater, IAnimalChangeObserver{
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
        removeDeadAnimals();  // animals changed (kolejność nie zostaje zabużona)
        moveAnimals();        // animals changed (kolejność naprawiona)
        feedAnimals();        // animals changed (naprawiona kolejność)
        breedAnimals();       // animals changed (naprawiona kolejność)
        growPlants();         //
        endTheDay();          // animals changed (naprawiona kolejność)
    }

    private void removeDeadAnimals(){
        for(Animal animal: map.getAnimals()){
            if (animal.getEnergy() < 1){
                map.removeMapElement(animal);
            }
        }
    }

    private void moveAnimals(){
        for (Animal animal: map.getAnimals())
            animal.makeMove();
    }

    private void feedAnimals() {
        for (Vector2d position : map.getPlantsPositions()) {
            if (map.isAnimalAt(position)) {
                map.get1stAnimalAt(position).hasEaten(setup.plantEnergy());
                map.removeMapElement(map.getPlantAt(position));
            }
        }
    }

    private void breedAnimals(){
        List<Animal> animalsToPlaceOnMap = new ArrayList<>();
        for (Vector2d position: map.getAnimalsPositions()) {
            Animal parent2nd = map.get2ndAnimalAt(position);
            while (parent2nd != null && parent2nd.isBreedable()) {
                System.out.println("Nowe życie na "+position);

                Animal parent1st = map.popTopAnimalAt(position);
                map.popTopAnimalAt(position);
                animalsToPlaceOnMap.add(parent1st);
                animalsToPlaceOnMap.add(parent2nd);

                Animal descendant = breed2Animals(parent1st, parent2nd);
                animalsToPlaceOnMap.add(descendant);

                parent2nd = map.get2ndAnimalAt(position);
            }
        }
        for (Animal animal: animalsToPlaceOnMap){
            map.placeMapElement(animal);
        }
    }

    private void growPlants(){
        generatePlants(setup.numOfPlantsPerDay());
    }

    private void endTheDay(){
        for (Animal animal: map.getAnimals()){
            animal.hasLivedDay();
        }
    }

    // IAnimalChangeObserver
    @Override
    public void animalBeforeChange(Animal animal) {
        map.removeMapElement(animal);
    }

    @Override
    public void animalAfterChange(Animal animal) {
        map.placeMapElement(animal);
    }
    // IAnimalChangeObserver end


    // helper methods
    private void generateAnimals(){
        for (int i = 0; i < setup.initialNumOfAnimals(); i++){
            generateNewAnimal(getRandomAnimalPosition(), setup.initialAnimalEnergy(), getRandomGenome());
        }
    }

    private void generatePlants(int NumOfPlants){
        for (int i = 0; i < NumOfPlants; i++){
            Vector2d position = plantPlacementLocator.getNewPlantPosition();
            if (position != null) {
                IMapElement plant = new Plant(position);
                map.placeMapElement(plant);
            }
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

    // parent1st.energy >= parent2nd.energy
    private int[] getDescendantGenome(Animal parent1st, Animal parent2nd){
        int parent1stSide = ThreadLocalRandom.current().nextInt(0, 2);
        int parent1stNumOfGenes = (int) Math.floor((double)parent1st.getEnergy() * setup.genomeLength() / (parent1st.getEnergy()+parent2nd.getEnergy()) + 0.5);
        int parent2ndNumOfGenes = setup.genomeLength() - parent1stNumOfGenes;

        int[] descendantGenome = new int[setup.genomeLength()];
        if (parent1stSide == 0){
            System.arraycopy(parent1st.getGenome(), 0, descendantGenome, 0, parent1stNumOfGenes);
            System.arraycopy(parent2nd.getGenome(), parent1stNumOfGenes, descendantGenome, parent1stNumOfGenes, parent2ndNumOfGenes);
        }
        else {
            System.arraycopy(parent2nd.getGenome(), 0, descendantGenome, 0, parent2ndNumOfGenes);
            System.arraycopy(parent1st.getGenome(), parent2ndNumOfGenes, descendantGenome, parent2ndNumOfGenes, parent1stNumOfGenes);
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
        return generateNewAnimal(strongerParent.getPosition(), setup.animalEnergyConsumption()*2,
                genomeMutator.getMutatedGenome(getDescendantGenome(strongerParent, weakerParent)));
    }

    private Animal generateNewAnimal(Vector2d position, int initEnergy, int[] genome){
        Animal newAnimal = new Animal(position, initEnergy, setup.fullAnimalEnergy(),
                setup.animalEnergyConsumption(), genome, (IAnimalPositionCorrector) map, geneSelector);
        newAnimal.addObserver(this);
        map.placeMapElement(newAnimal);
        return newAnimal;
    }
    // helper methods end
}
