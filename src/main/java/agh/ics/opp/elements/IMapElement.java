package agh.ics.opp.elements;

import agh.ics.opp.Vector2d;

public interface IMapElement {
    Vector2d getPosition();
    String toString();
    String getImagePath();
    String getLabelName();
}
