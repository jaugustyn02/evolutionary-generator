package agh.ics.opp.simulation.maps.elements.plant.generators;

import agh.ics.opp.simulation.types.Vector2d;

public interface IAnimalDeathObserver {
    void animalDiedAt (Vector2d position);
}
