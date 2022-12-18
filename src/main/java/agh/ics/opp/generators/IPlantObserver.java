package agh.ics.opp.generators;

import agh.ics.opp.elements.Plant;

public interface IPlantObserver {
    void plantRemoved(Plant plant);
    void plantPlaced(Plant plant);
}
