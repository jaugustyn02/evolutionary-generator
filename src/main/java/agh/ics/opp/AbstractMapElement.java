package agh.ics.opp;

abstract public class AbstractMapElement implements IMapElement{
    abstract public Vector2d getPosition();
    abstract public String getImagePath();
    abstract public String getLabelName();
    abstract public String toString();
}
