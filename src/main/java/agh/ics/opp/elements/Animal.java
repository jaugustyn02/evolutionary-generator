package agh.ics.opp.elements;

import agh.ics.opp.IAnimalChangeObserver;
import agh.ics.opp.MapDirection;
import agh.ics.opp.Vector2d;
import agh.ics.opp.variants.behaviours.IGeneSelector;
import agh.ics.opp.variants.maps.IAnimalPositionCorrector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends AbstractMapElement{
    private final int[] genome;
    private final int fullEnergy;
    private final int energyConsumption;

    private Vector2d position;
    private Integer energy;
    private MapDirection direction;
    private int nextGeneIndex;

    //stats
    private int numOfDescendants = 0;
    private int numOfEatenPlants = 0;
    private int age = 0;
    //stats end

    private final IAnimalPositionCorrector corrector;
    private final IGeneSelector selector;
    private final List<IAnimalChangeObserver> observers = new ArrayList<>();

    public Animal(Vector2d position, Integer initEnergy, int fullEnergy, int energyConsumption, int[] genome,
                  IAnimalPositionCorrector corrector, IGeneSelector selector){
        this.position = position;
        this.energy = initEnergy;
        this.fullEnergy = fullEnergy;
        this.energyConsumption = energyConsumption;
        this.genome = genome;
        this.corrector = corrector;
        this.selector = selector;
        this.nextGeneIndex = ThreadLocalRandom.current().nextInt(0, genome.length);
        this.direction = MapDirection.getRandom();
    }
    
    public void makeMove(){
        beforeChange();
        this.turnBy(genome[nextGeneIndex]);
        this.position = this.position.add(this.direction.toUnitVector());
        corrector.correctAnimalPosition(this);
        this.updateNextGeneIndex();
        afterChange();
    }

    // direction
    private void turnBy(int rotation){
        this.direction = direction.rotate(rotation);
    }
    public void turnBack(){
        this.turnBy(4);
    }
    // direction end

    // position
    public void setPosition(Vector2d position){
        this.position = position;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    // position end

    // genome
    public int[] getGenome(){
        return this.genome;
    }
    // genome end

    // direction
    public MapDirection getDirection(){
        return this.direction;
    }
    // direction end

    // nextGeneIndex
    private void updateNextGeneIndex() {
        this.nextGeneIndex = selector.getNextGeneIndex(this.nextGeneIndex);
    }
    // nextGeneIndex end

    // energy
    public Integer getEnergy(){ return this.energy; }
    public void reduceEnergy(Integer energy){
        this.energy = Math.max(this.energy-energy, 0);
    }
    public void increaseEnergy(Integer energy){
        this.energy += energy;
    }
    public boolean isBreedable(){
        return this.energy >= this.fullEnergy;
    }
    // energy end

    public void hasEaten(int gainedEnergy){
        beforeChange();
        increaseEnergy(gainedEnergy);
        numOfEatenPlants++;
        afterChange();
    }
    public void hasBred(){
        reduceEnergy(energyConsumption);
        numOfDescendants++;
    }
    public void hasLivedDay(){
        beforeChange();
        reduceEnergy(1);
        age++;
        afterChange();
    }

    // Observers
    public void addObserver(IAnimalChangeObserver observer){
        observers.add(observer);
    }
    private void beforeChange(){
        for(IAnimalChangeObserver observer: observers)
            observer.animalBeforeChange(this);
    }
    private void afterChange(){
        for(IAnimalChangeObserver observer: observers)
            observer.animalAfterChange(this);
    }
    // Observers end

    public Integer getNumOfDescendants() {
        return numOfDescendants;
    }

    public Integer getAge() {
        return age;
    }

    public String toString(){
//        return direction.toString();
        return energy.toString();
//        return ((Integer)genome[0]).toString();
    }

    // gui
    public String getImagePath() {
        return null;
    }
    public String getLabelName() {
        return null;
    }
    // gui end
}
// test
