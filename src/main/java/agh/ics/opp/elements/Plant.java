package agh.ics.opp.elements;

import agh.ics.opp.Vector2d;

public class Plant extends AbstractMapElement{
    private final Vector2d position;

    public Plant(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition(){
        return position;
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public String getLabelName() {
        return null;
    }

    @Override
    public String toString() {
        return "φ";
    }
}
