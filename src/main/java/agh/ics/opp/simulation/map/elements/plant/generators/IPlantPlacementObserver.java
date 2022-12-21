package agh.ics.opp.simulation.map.elements.plant.generators;

import agh.ics.opp.simulation.map.elements.plant.Plant;

public interface IPlantPlacementObserver {
    void plantRemoved(Plant plant);
    void plantPlaced(Plant plant);
}
