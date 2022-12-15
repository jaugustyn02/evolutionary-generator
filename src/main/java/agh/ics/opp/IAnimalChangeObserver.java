package agh.ics.opp;

import agh.ics.opp.elements.Animal;

public interface IAnimalChangeObserver {
    void animalBeforeChange(Animal animal);
    void animalAfterChange(Animal animal);
}
