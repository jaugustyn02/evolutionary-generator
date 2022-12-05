package agh.ics.opp;

import java.util.List;

public class Animal extends AbstractMapElement{
    private Vector2d position;
    private List<Integer> genome;
    private MapDirection direction = MapDirection.NORTH;

    public Animal(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public List<Integer> getGenome(){
        return this.genome;
    }

    public String getImagePath() {
        return null;
    }

    public String getLabelName() {
        return null;
    }

    public String toString(){
        return direction.toString();
    }
}
