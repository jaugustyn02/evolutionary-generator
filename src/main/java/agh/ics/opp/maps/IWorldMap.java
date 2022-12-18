package agh.ics.opp.maps;

import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IWorldMap {
    void placeMapElement(IMapElement element);
    void removeMapElement(IMapElement element);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
    String toString();
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    Animal getFirstAnimalAt(Vector2d position);
    Animal getLastAnimalAt(Vector2d position);
    Animal getSecondAnimalAt(Vector2d position);
    Animal popFirstAnimalAt(Vector2d position);
    Animal popLastAnimalAt(Vector2d position);
    Map<Vector2d, UpdatableTreeSet> getAnimalsMap();
    Set<Animal> getAnimalsSet();
    List<Vector2d> getAnimalsPositions();
    List<Vector2d> getPlantsPositions();
    Plant getPlantAt(Vector2d position);
    boolean isAnimalAt(Vector2d position);
    boolean isPlantAt(Vector2d position);
    int getWidth();
    int getHeight();
}

