package agh.ics.opp.simulation.maps.elements.plant.generators;

import agh.ics.opp.simulation.maps.elements.plant.Plant;

public interface IPlantPlacementObserver {
    void plantRemoved(Plant plant);
    void plantPlaced(Plant plant);
}
