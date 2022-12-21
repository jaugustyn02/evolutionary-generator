package agh.ics.opp.simulation.map;

import agh.ics.opp.simulation.map.elements.plant.generators.IPlantPlacementObserver;

public interface IMapObserversHandler {
    void addPlantObserver(IPlantPlacementObserver observer);
}
