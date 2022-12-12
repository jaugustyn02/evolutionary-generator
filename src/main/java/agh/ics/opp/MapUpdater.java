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
        this.geneSelector = (setup.animalBehaviorsVariant() ?
                new ChaoticSelector(setup.genomeLength()):  // true
                new OrderlySelector(setup.genomeLength())); // false
        this.genomeMutator = (setup.mutationsVariant() ?
                new BoundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()):       // true
                new UnboundedMutator(setup.minNumOfMutations(), setup.maxNumOfMutations()));    // false
        this.plantPlacementLocator = (setup.plantPreferencesVariant() ?
                new AntiToxicLocator(map):      // true
                new EquatorialLocator(map));    // false
        this.initMap();
    }

    private void initMap(){
        generateAnimals();
        generatePlants(setup.initialNumOfPlants());
    }

    public void nextDay(){
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        growPlants();
        consumeDailyEnergy();
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
        // tu dokończyć
    }

    private void growPlants(){
        generatePlants(setup.numOfPlantsPerDay());
    }

    private void consumeDailyEnergy(){
        for (Animal animal: map.getAnimals()){
            animal.reduceEnergy(1);
        }
    }

    // helper methods
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

    private int[] getDescendantGenome(Animal parent1, Animal parent2){
        if(parent1.getEnergy() < parent2.getEnergy()){
            Animal temp = parent1;
            parent1 = parent2;
            parent2 = temp;
        }
        int parent1Side = ThreadLocalRandom.current().nextInt(0, 2);
        int numOfParent1Genes = (int) Math.floor((double)parent1.getEnergy() * setup.genomeLength() / (parent1.getEnergy()+parent2.getEnergy()) + 0.5);
        int numOfParent2Genes = setup.genomeLength() - numOfParent1Genes;
        int[] descendantGenome = new int[setup.genomeLength()];
        System.out.println(parent1Side);
        if (parent1Side == 0){
            System.arraycopy(parent1.getGenome(), 0, descendantGenome, 0, numOfParent1Genes);
            System.arraycopy(parent2.getGenome(), numOfParent1Genes, descendantGenome, numOfParent1Genes, numOfParent2Genes);
        }
        else {
            System.arraycopy(parent2.getGenome(), 0, descendantGenome, 0, numOfParent2Genes);
            System.arraycopy(parent1.getGenome(), numOfParent2Genes, descendantGenome, numOfParent2Genes, numOfParent1Genes);
        }
        return descendantGenome;
    }
    private Animal breed2Animals(Animal parent1, Animal parent2){
        Animal descendant = new Animal(getRandomAnimalPosition(), setup.initialAnimalEnergy(),
                getDescendantGenome(parent1, parent2), (IAnimalPositionCorrector)map, geneSelector, genomeMutator);
        descendant.mutate();
        return descendant;
    }
    // helper methods end
}
