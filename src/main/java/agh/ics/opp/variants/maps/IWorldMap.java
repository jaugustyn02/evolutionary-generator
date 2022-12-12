package agh.ics.opp.variants.maps;

import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;

import java.util.List;

public interface IWorldMap {
    void placeMapElement(IMapElement element);
    void removeMapElement(Vector2d position, IMapElement element);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
    String toString();
    List<Animal> getAnimals();
    List<Vector2d> getEatingPositions();
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    Animal getTopAnimalAt(Vector2d position);
    Animal popTopAnimalAt(Vector2d position);
    Plant getPlantAt(Vector2d position);

    boolean isAnimalAt(Vector2d position);
}
