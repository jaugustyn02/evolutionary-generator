package agh.ics.opp;

public class World {
    public static void main(String[] args){
        IGenomeMutator mutator = new RandomMutator();
        IAnimalMover mover = new InOrderMover();
        IPlantsGrower grower = new CentralGrower();
        Vector2d upperRight = new Vector2d(5, 5);
        IWorldMap map = new GlobeMap(upperRight, mutator, mover, grower);
        IMapElement animal = new Animal(new Vector2d(2, 2));
        IMapElement plant = new Plant(new Vector2d(0, 1));
        map.placeMapElement(animal);
        map.placeMapElement(plant);
        System.out.println(map);
    }
}
