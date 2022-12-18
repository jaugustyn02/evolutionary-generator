package agh.ics.opp.maps;

import agh.ics.opp.*;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.AnimalComparator;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;
import agh.ics.opp.generators.IPlantObserver;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap, IAnimalPositionCorrector, IMapObserversHandler {
    private final Map<Vector2d, UpdatableTreeSet> animalsMap = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();

    public final static Vector2d lowerLeft = new Vector2d(0 ,0);
    private final Vector2d upperRight;
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    private final List<IPlantObserver> plantObservers = new LinkedList<>();

    protected AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight;
    }

    abstract public void correctAnimalPosition(Animal animal);

    @Override
    public void placeMapElement(IMapElement element) {
        if(element instanceof Animal animal) {
            if (animalsMap.get(animal.getPosition()) == null)
                animalsMap.put(animal.getPosition(), new UpdatableTreeSet(new AnimalComparator()));
            animalsMap.get(animal.getPosition()).add(animal);
        }
        else if (element instanceof Plant plant) {
            plants.put(plant.getPosition(), plant);
            plantPlaced(plant);
        }
    }

    @Override
    public void removeMapElement(IMapElement element){
        Vector2d position = element.getPosition();
        if(element instanceof Animal animal){
            animalsMap.get(position).remove(animal);
            if(animalsMap.get(position).isEmpty()){
                animalsMap.remove(position);
            }
        }
        else if (element instanceof Plant plant){
            plants.remove(position);
            plantRemoved(plant);
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animalsMap.get(position) != null){
            return animalsMap.get(position).first();
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
        return animalsMap.get(position) != null;
    }
    @Override
    public Animal popFirstAnimalAt(Vector2d position){
        Animal animal = getFirstAnimalAt(position);
        if (animal != null) removeMapElement(animal);
        return animal;
    }
    @Override
    public Animal popLastAnimalAt(Vector2d position){
        Animal animal = getLastAnimalAt(position);
        if (animal != null) removeMapElement(animal);
        return animal;
    }
    @Override
    public Animal getFirstAnimalAt(Vector2d position){
        if (!isAnimalAt(position)) return null;
        return animalsMap.get(position).first();
    }
    @Override
    public Animal getLastAnimalAt(Vector2d position){
        if (!isAnimalAt(position)) return null;
        return animalsMap.get(position).last();
    }
    @Override
    public Animal getSecondAnimalAt(Vector2d position){
        if(!isAnimalAt(position)) return null;
        Iterator<Animal> it = animalsMap.get(position).iterator();
        if (it.hasNext()) it.next();
        return (it.hasNext() ? it.next() : null);
    }
    @Override
    public Map<Vector2d, UpdatableTreeSet> getAnimalsMap() {
        return animalsMap;
    }
    @Override
    public Set<Animal> getAnimalsSet(){
        Set<Animal> animalsSet = new HashSet<>();
        for (UpdatableTreeSet set: animalsMap.values())
            animalsSet.addAll(set);
        return animalsSet;
    }
    @Override
    public List<Vector2d> getAnimalsPositions(){
        return new ArrayList<>(animalsMap.keySet());
    }
    @Override
    public List<Vector2d> getPlantsPositions(){
        return new ArrayList<>(plants.keySet());
    }
    @Override
    public Plant getPlantAt(Vector2d position){
        return plants.get(position);
    }
    @Override
    public final Vector2d getLowerLeft() {
        return lowerLeft;
    }
    @Override
    public Vector2d getUpperRight(){
        return upperRight;
    }
    public int getWidth() {
        return upperRight.x - lowerLeft.x + 1;
    }
    public int getHeight() {
        return upperRight.y - lowerLeft.y + 1;
    }

    // Observers
    private void plantRemoved(Plant plant) {
        for (IPlantObserver observer : plantObservers) {
            observer.plantRemoved(plant);
        }
    }
    private void plantPlaced(Plant plant){
        for(IPlantObserver observer: plantObservers){
            observer.plantPlaced(plant);
        }
    }
    @Override
    public void addPlantObserver(IPlantObserver observer){
        plantObservers.add(observer);
    }
    // end

    @Override
    public String toString(){
        System.out.println(animalsMap);
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }
}
