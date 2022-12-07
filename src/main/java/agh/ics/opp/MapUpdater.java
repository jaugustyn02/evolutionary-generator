package agh.ics.opp;

public class MapUpdater {
    private final IWorldMap map;
    private final SimulationSetup setup;

    public MapUpdater(IWorldMap map, SimulationSetup setup){
        this.map = map;
        this.setup = setup;
        this.initMap();
    }

    private void initMap(){
        generateAnimals();
        generatePlants();
    }

    public void nextDay(){
        removeCorpses();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        growPlants();
        reduceEnergy();
    }

    private void growPlants(){};
    private void removeCorpses(){};

    private void moveAnimals(){
        for (Animal animal: map.getAnimals()){
            animal.makeMove();
        }
    }

    private void feedAnimals(){}
    private void breedAnimals(){}
    private void reduceEnergy(){}

    private void generateAnimals(){
        int[] genome = new int[]{2,3,7,0,5};
        Animal animal = new Animal(map, new Vector2d(2, 2), setup.initialAnimalEnergy(), genome, setup.animalBehaviorsVariant());
        animal.addObserver((IPositionChangeObserver) map);
        map.placeMapElement(animal);
    }

    private void generatePlants(){
        IMapElement plant = new Plant(new Vector2d(0, 1));
        map.placeMapElement(plant);
    }
}
