package agh.ics.opp;

import java.util.List;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);
    void adjustAnimalPosition(Vector2d position, Animal animal);
    void placeMapElement(IMapElement element);
    void removeMapElement(Vector2d position, IMapElement element);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
    String toString();
    List<Animal> getAnimals();

    List<Plant> getPlants();

    Vector2d getLowerLeft();
    Vector2d getUpperRight();
}
