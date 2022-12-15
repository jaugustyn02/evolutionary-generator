package agh.ics.opp.variants.maps;

import agh.ics.opp.*;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.AnimalComparator;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap, IAnimalPositionCorrector, IMapStatisticRunner {
    private final Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();

    public final static Vector2d lowerLeft = new Vector2d(0 ,0);
    private final Vector2d upperRight;
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    protected AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight;
    }

    abstract public void correctAnimalPosition(Animal animal);

    @Override
    public void placeMapElement(IMapElement element) {
        if(element instanceof Animal animal) {
            if (animals.get(animal.getPosition()) == null)
                animals.put(animal.getPosition(), new TreeSet<>(new AnimalComparator()));
            animals.get(animal.getPosition()).add(animal);
        }
        else if (element instanceof Plant plant)
            plants.put(plant.getPosition(), plant);
    }

    @Override
    public void removeMapElement(IMapElement element){
        if (element == null){
            System.out.println("Błąd usuwania: element jest null'em");
            return;
        }
        Vector2d position = element.getPosition();
        if(element instanceof Animal animal){
            if(!isAnimalAt(position)){
                System.out.println("Nie można usunąć zwierzęcia z pustego pola: "+position);
                return;
            }
            if(!animals.get(position).contains(element)){
                System.out.println("Nie znaleziono takiego zwierzęcia na polu: "+position);
            }
            animals.get(position).remove(animal);
            if(animals.get(position).isEmpty()){
                animals.remove(position);
            }
        }
        else if (element instanceof Plant){
            plants.remove(position);
        }
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
    public Animal popTopAnimalAt(Vector2d position){
        Animal animal = get1stAnimalAt(position);
        if (animal != null) removeMapElement(animal);
        return animal;
    }
    @Override
    public Animal get1stAnimalAt(Vector2d position){
        if (!isAnimalAt(position)) return null;
        return animals.get(position).first();
    }
    @Override
    public Animal get2ndAnimalAt(Vector2d position){
        if(!isAnimalAt(position)) return null;
        Iterator<Animal> it = animals.get(position).iterator();
        if (it.hasNext()) it.next();
        return (it.hasNext() ? it.next() : null);
    }
    @Override
    public List<Animal> getAnimals() {
        List<Animal> animalList = new ArrayList<>();
        for (TreeSet<Animal> set: this.animals.values())
            animalList.addAll(set);
        return animalList;
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
    public List<Vector2d> getPlantsPositions(){
        return new ArrayList<>(plants.keySet());
    }

    @Override
    public String toString(){
        System.out.println(animals);
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }

    public int getWidth() {
        return upperRight.x - lowerLeft.x + 1;
    }
    public int getHeight() {
        return upperRight.y - lowerLeft.y + 1;
    }

    // IMapStatisticRunner
    @Override
    public int getNumOfPlants(){
        return plants.size();
    }
    // IMapStatisticRunner end
}
