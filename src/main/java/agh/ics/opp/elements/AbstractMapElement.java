package agh.ics.opp.elements;

import agh.ics.opp.Vector2d;

abstract public class AbstractMapElement implements IMapElement{
    abstract public Vector2d getPosition();
    abstract public String getImagePath();
    abstract public String getLabelName();
    abstract public String toString();
}
