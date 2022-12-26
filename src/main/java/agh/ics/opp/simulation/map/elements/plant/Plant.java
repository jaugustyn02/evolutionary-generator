package agh.ics.opp.simulation.map.elements.plant;

import agh.ics.opp.simulation.map.elements.AbstractMapElement;
import agh.ics.opp.simulation.types.Vector2d;

public class Plant extends AbstractMapElement {
    private final Vector2d position;
    private final int energy;

    public Plant(Vector2d position, int energy){
        this.position = position;
        this.energy = energy;
    }

    public int getEnergy(){
        return this.energy;
    }

    @Override
    public Vector2d getPosition(){
        return position;
    }

    @Override
    public String getImagePath() {
        return resourcesPath + "plant.png";
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
