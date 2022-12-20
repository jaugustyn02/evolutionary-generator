package agh.ics.opp.simulation.maps;

import agh.ics.opp.simulation.maps.elements.plant.generators.IPlantPlacementObserver;

public interface IMapObserversHandler {
    void addPlantObserver(IPlantPlacementObserver observer);
}
