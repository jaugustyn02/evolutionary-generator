package agh.ics.opp.variants.maps;

import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Animal;
import agh.ics.opp.elements.IMapElement;
import agh.ics.opp.elements.Plant;

import java.util.List;

public interface IWorldMap {
    void placeMapElement(IMapElement element);
    void removeMapElement(IMapElement element);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
    String toString();
    Vector2d getLowerLeft();
    Vector2d getUpperRight();
    Animal get1stAnimalAt(Vector2d position);
    Animal get2ndAnimalAt(Vector2d position);
    Animal popTopAnimalAt(Vector2d position);
    List<Animal> getAnimals();
    List<Vector2d> getAnimalsPositions();
//    List<Vector2d> getEatingPositions();
    List<Vector2d> getPlantsPositions();
    Plant getPlantAt(Vector2d position);

    boolean isAnimalAt(Vector2d position);
    boolean isPlantAt(Vector2d position);
    int getWidth();
    int getHeight();
}

