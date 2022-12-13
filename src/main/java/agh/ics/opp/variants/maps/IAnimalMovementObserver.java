package agh.ics.opp.variants.maps;

import agh.ics.opp.Vector2d;
import agh.ics.opp.elements.Animal;

public interface IAnimalMovementObserver {
    void animalMadeMove(Vector2d oldPosition, Animal animal);
}
