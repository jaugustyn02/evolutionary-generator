package agh.ics.opp;

import agh.ics.opp.elements.Animal;

public interface IAnimalMovementObserver {
    void animalMadeMove(Vector2d oldPosition, Animal animal);
}
