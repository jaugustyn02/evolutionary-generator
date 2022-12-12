package agh.ics.opp.variants.maps;

import agh.ics.opp.*;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap, IAnimalMovementObserver, IAnimalPositionCorrector {
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final Set<Vector2d> eatingPositions = new HashSet<>();
    private final Map<Vector2d, Integer> breedingPositions = new HashMap<>();
    public final static Vector2d lowerLeft = new Vector2d(0 ,0);
    private final Vector2d upperRight;

    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    protected AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight;
    }

    abstract public void correctAnimalPosition(Animal animal);

    @Override
    public void placeMapElement(IMapElement element) {
        if(element instanceof Animal animal)
            if(animals.get(animal.getPosition()) == null) {
                animals.put(animal.getPosition(), new ArrayList<>() {{ add(animal); }});
            }
            else{
                animals.get(animal.getPosition()).add(animal);
            }
        else if (element instanceof Plant plant)
            plants.put(plant.getPosition(), plant);
    }

    @Override
    public void removeMapElement(Vector2d position, IMapElement element){
        if(element instanceof Animal animal){
            animals.get(position).remove(animal);
            if(animals.get(position).isEmpty()){
                animals.remove(position);
            }
        }
        else if (element instanceof Plant){
            plants.remove(position);
            eatingPositions.remove(position);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animals.get(position) != null){
            return animals.get(position).get(0);
        }
        return plants.get(position);
    }

    @Override
    public List<Animal> getAnimals() {
        List<Animal> animalList = new ArrayList<>();
        for (List<Animal> list: this.animals.values())
            animalList.addAll(list);
        return animalList;
    }

    @Override
    public List<Vector2d> getEatingPositions(){
        return new ArrayList<>(eatingPositions);
    }

//    @Override
//    public List<Vector2d> getBreedingPositions(){
//        return new ArrayList<>(breedingPositions);
//    }

    @Override
    public final Vector2d getLowerLeft() {
        return lowerLeft;
    }

    @Override
    public Vector2d getUpperRight(){
        return upperRight;
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }

    @Override
    public void animalMadeMove(Vector2d oldPosition, Animal animal){
        this.removeMapElement(oldPosition, animal);
        this.placeMapElement(animal);
        this.checkUpdatedAnimalPosition(animal);
    }

    @Override
    public Animal getTopAnimalAt(Vector2d position){
        if(!isAnimalAt(position)) return null;
        return animals.get(position).get(0); // Temporary
    }

    @Override
    public Animal popTopAnimalAt(Vector2d position){
        Animal animal = getTopAnimalAt(position);
        if (animal != null) removeMapElement(position, animal);
        return animal;
    }

    @Override
    public Plant getPlantAt(Vector2d position){
        return plants.get(position);
    }

    private void checkUpdatedAnimalPosition(Animal animal){
        Vector2d position = animal.getPosition();
        if(isPlantAt(position)){
            eatingPositions.add(position);
        }
        // dodać sprawdzenie czy na danym polu jest możliwe rozmnażanie
    }

    private boolean isPlantAt(Vector2d position){
        return plants.get(position) != null;
    }

    @Override
    public boolean isAnimalAt(Vector2d position){
        return animals.get(position) != null;
    }
}
