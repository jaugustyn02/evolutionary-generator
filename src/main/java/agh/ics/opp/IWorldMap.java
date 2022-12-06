package agh.ics.opp;

import java.util.List;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);
    void placeMapElement(IMapElement element);
    void removeMapElement(IMapElement element);
    boolean isOccupied(Vector2d position);
    void applyMoveToMapRules(Animal animal, Vector2d newPosition);
    Object objectAt(Vector2d position);
    String toString();
    List<Animal> getAnimals();

    List<Plant> getPlants();

    Vector2d getLowerLeft();
    Vector2d getUpperRight();
}
