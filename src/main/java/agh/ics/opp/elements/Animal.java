package agh.ics.opp.elements;

import agh.ics.opp.variants.maps.IAnimalMovementObserver;
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
    private int age = 0;
    //stats end

    private final IAnimalPositionCorrector corrector;
    private final IGeneSelector selector;
    private final List<IAnimalMovementObserver> observers = new ArrayList<>();

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
        this.turnBy(genome[nextGeneIndex]);
        Vector2d oldPosition = this.position;
        this.position = this.position.add(this.direction.toUnitVector());
        corrector.correctAnimalPosition(this);
        this.newMovement(oldPosition);
        this.updateNextGeneIndex();
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
        this.energy -= energy;
    }
    public void increaseEnergy(Integer energy){
        this.energy += energy;
    }
    public boolean isBreedable(){
        return this.energy >= this.fullEnergy;
    }
    // energy end

    public void hasBred(){
        reduceEnergy(energyConsumption);
        numOfDescendants++;
    }
    public void hasLivedDay(){
        reduceEnergy(1);
        age++;
    }

    // observers
    public void addObserver(IAnimalMovementObserver observer){
        observers.add(observer);
    }
    private void newMovement(Vector2d oldPosition){
        for(IAnimalMovementObserver observer: observers)
            observer.animalMadeMove(oldPosition, this);
    }
    // observers end

    public Integer getNumOfDescendants() {
        return numOfDescendants;
    }

    public Integer getAge() {
        return age;
    }

    public String toString(){
//        return direction.toString();
        return energy.toString();
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
