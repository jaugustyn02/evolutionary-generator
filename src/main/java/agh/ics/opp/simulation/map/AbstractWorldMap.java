package agh.ics.opp.simulation.map;

import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.animal.AnimalComparator;
import agh.ics.opp.simulation.map.elements.IMapElement;
import agh.ics.opp.simulation.map.elements.plant.Plant;
import agh.ics.opp.simulation.map.elements.plant.generators.IPlantPlacementObserver;
import agh.ics.opp.simulation.types.UpdatableTreeSet;
import agh.ics.opp.simulation.types.Vector2d;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap, IAnimalPositionCorrector, IMapObserversHandler {
    private final Map<Vector2d, UpdatableTreeSet> animalsMap = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();

    public final static Vector2d lowerLeft = new Vector2d(0 ,0);
    private final Vector2d upperRight;
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final int equatorBottom, equatorTop;

    private final List<IPlantPlacementObserver> plantObservers = new LinkedList<>();

    protected AbstractWorldMap(Vector2d upperRight) {
        this.upperRight = upperRight;
        this.equatorBottom =  (int) ((upperRight.y - lowerLeft.y) * 0.4);
        this.equatorTop=  (int) ((upperRight.y - lowerLeft.y) * 0.6);
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
    public void popLastAnimalAt(Vector2d position){
        Animal animal = getLastAnimalAt(position);
        if (animal != null) removeMapElement(animal);
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
        for (IPlantPlacementObserver observer : plantObservers) {
            observer.plantRemoved(plant);
        }
    }
    private void plantPlaced(Plant plant){
        for(IPlantPlacementObserver observer: plantObservers){
            observer.plantPlaced(plant);
        }
    }
    @Override
    public void addPlantObserver(IPlantPlacementObserver observer){
        plantObservers.add(observer);
    }
    // end

    @Override
    public String toString(){
//        System.out.println(animalsMap);
        return mapVisualizer.draw(this.getLowerLeft(), this.getUpperRight());
    }
    @Override
    public int getEquatorBottom(){
        return equatorBottom;
    }
    @Override
    public int getEquatorTop(){
        return equatorTop;
    }
}