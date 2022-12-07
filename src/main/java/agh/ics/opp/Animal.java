package agh.ics.opp;


import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractMapElement{
    private Vector2d position;
    private final int[] genome;
    private Integer energy;
    private MapDirection direction;
    private int nextGeneIndex;
    private boolean moveBehavior;

    private final IWorldMap map;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map, Vector2d position, Integer initialEnergy, int[] genome, boolean behavior){
        this.map = map;
        this.position = position;
        this.energy = initialEnergy;
        this.genome = genome;
        this.nextGeneIndex = 0; // zmienić na losową wartość indeksu
        this.direction = MapDirection.NORTH;
        this.moveBehavior = behavior;
    }

//    public void makeTurn(){
//        this.turnBy(genome[nextGeneIndex]);
//        this.nextGeneIndex();
//        this.makeMove();
//    }

    public void makeMove(){
        this.turnBy(genome[nextGeneIndex]);
        this.nextGeneIndex();
        Vector2d oldPosition = this.position;
        this.position = this.position.add(this.direction.toUnitVector());
        this.positionChanged(oldPosition);
    }

    public void turnBy(int rotation){
        this.direction = direction.rotate(rotation);
    }

    public void turnBack(){
        this.turnBy(4);
    }

    public void setPosition(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() { return this.position; }

    public int[] getGenome(){ return this.genome; }

    public MapDirection getDirection(){ return this.direction; }

    public int getNextGeneIndex(){ return this.nextGeneIndex; }

    public void setNextGeneIndex(int index) {
        if(0 <= index && index < getGenomeLength())
            this.nextGeneIndex = index;
        else System.out.println(index+" - to niepoprawny indeks genu");
    }

    // ustawia następną wartość indeksu genu, powinien obejmować dwa warianty
    public void nextGeneIndex() {
        this.setNextGeneIndex((this.nextGeneIndex+1) % getGenomeLength());
    }

    public int getEnergy(){ return this.energy; }

    public void decrementEnergy(){ this.energy--; }

    public void decreaseEnergy(Integer energy){ this.energy -= energy; }

    public void increaseEnergy(Integer energy){ this.energy += energy; }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    protected void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition){
        for(IPositionChangeObserver observer: observers)
            observer.positionChanged(oldPosition, this);
    }

    public int getGenomeLength(){
        return this.genome.length;
    }

    public String getImagePath() {
        return null;
    }

    public String getLabelName() {
        return null;
    }

    public String toString(){
        return direction.toString();
//        return energy.toString();
    }
}
