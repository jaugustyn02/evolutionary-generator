package agh.ics.opp.simulation.maps.elements;

import agh.ics.opp.simulation.types.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    String toString();
    String getImagePath();
    String getLabelName();
}
