package agh.ics.opp.simulation.maps.elements;

import agh.ics.opp.simulation.types.Vector2d;

abstract public class AbstractMapElement implements IMapElement{
    protected final static String resourcesPath = "src/main/resources/";
    abstract public Vector2d getPosition();
    abstract public String getImagePath();
    abstract public String getLabelName();
    abstract public String toString();
}
