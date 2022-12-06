package agh.ics.opp;

public class MapUpdater {
    private final IWorldMap map;
    private final IGenomeMutator mutator;
    private final IAnimalMover mover;
    private final IPlantGrower grower;
    private final SimulationSetup setup;

    public MapUpdater(IWorldMap map, SimulationSetup setup){
        this.map = map;
        this.setup = setup;
        this.mutator = switch (setup.mutatorVariant()){
            case RandomMutator -> new RandomMutator();
            case LimitedMutator -> new LimitedMutator();
        };
        this.mover = switch (setup.moverVariant()){
            case InOrderMover -> new InOrderMover(map);
            case OutOfOrderMover -> new OutOfOrderMover();
        };
        this.grower = switch (setup.growerVariant()){
            case CentralGrower -> new CentralGrower();
            case SafeGrower -> new SafeGrower();
        };
    }

    public void initMap(){
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
            mover.makeMove(animal);
        }
    }

    private void feedAnimals(){}
    private void breedAnimals(){}
    private void reduceEnergy(){}

    private void generateAnimals(){
        int[] genome = new int[]{2,3,7,0,5};
        IMapElement animal = new Animal(new Vector2d(2, 2), 5, genome);
        map.placeMapElement(animal);
    }
    private void generatePlants(){
        IMapElement plant = new Plant(new Vector2d(0, 1));
        map.placeMapElement(plant);
    }
}
