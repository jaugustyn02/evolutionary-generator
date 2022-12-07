package agh.ics.opp;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Animal animal);
}
