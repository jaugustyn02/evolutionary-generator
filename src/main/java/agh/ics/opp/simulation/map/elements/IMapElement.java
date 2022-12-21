package agh.ics.opp.simulation.map.elements;

import agh.ics.opp.simulation.types.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    String toString();
    String getImagePath();
    String getLabelName();
}
