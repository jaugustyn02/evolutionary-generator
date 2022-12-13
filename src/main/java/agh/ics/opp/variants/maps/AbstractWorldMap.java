package agh.ics.opp.variants.maps;

import agh.ics.opp.*;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.AnimalComparator;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap, IAnimalMovementObserver, IAnimalPositionCorrector {
    private final Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final Set<Vector2d> eatingPositions = new HashSet<>();
    public final static Vector2d lowerLeft = new Vector2d(0 ,0);
    private final Vector2d upperRight;

    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    protected AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight.subtract(new Vector2d(1,1));
    }

    abstract public void correctAnimalPosition(Animal animal);

    @Override
    public void placeMapElement(IMapElement element) {
        if(element instanceof Animal animal)
            if(animals.get(animal.getPosition()) == null) {
                animals.put(animal.getPosition(), new TreeSet<>(new AnimalComparator()));
                animals.get(animal.getPosition()).add(animal);
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

    // IAnimalPositionCorrector
    private void checkUpdatedAnimalPosition(Vector2d position){
        if(isPlantAt(position))
            eatingPositions.add(position);
    }
    @Override
    public void animalMadeMove(Vector2d oldPosition, Animal animal){
        this.removeMapElement(oldPosition, animal);
        this.placeMapElement(animal);
        this.checkUpdatedAnimalPosition(animal.getPosition());
    }
    // IAnimalPositionCorrector end
    @Override
    public Animal popTopAnimalAt(Vector2d position){
        Animal animal = getTopAnimalAt(position);
        if (animal != null) removeMapElement(position, animal);
        return animal;
    }
    @Override
    public Object objectAt(Vector2d position) {
        if(animals.get(position) != null){
            return animals.get(position).first();
        }
        return plants.get(position);
    }
    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }
    @Override
    public boolean isPlantAt(Vector2d position){
        return plants.get(position) != null;
    }
    @Override
    public boolean isAnimalAt(Vector2d position){
        return animals.get(position) != null;
    }
    @Override
    public Animal getTopAnimalAt(Vector2d position){
        if(!isAnimalAt(position)) return null;
        return animals.get(position).first();
    }
    @Override
    public List<Animal> getAnimals() {
        List<Animal> animalList = new ArrayList<>();
        for (TreeSet<Animal> set: this.animals.values())
            animalList.addAll(set);
        return animalList;
    }
    @Override
    public List<Vector2d> getEatingPositions(){
        return new ArrayList<>(eatingPositions);
    }
    @Override
    public List<Vector2d> getAnimalsPositions(){
        return new ArrayList<>(animals.keySet());
    }
    @Override
    public final Vector2d getLowerLeft() {
        return lowerLeft;
    }
    @Override
    public Vector2d getUpperRight(){
        return upperRight;
    }
    @Override
    public Plant getPlantAt(Vector2d position){
        return plants.get(position);
    }

    @Override
    public String toString(){
        System.out.println(animals);
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }
}
