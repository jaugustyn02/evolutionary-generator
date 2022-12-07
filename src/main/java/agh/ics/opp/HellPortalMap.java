package agh.ics.opp;

public class HellPortalMap extends AbstractWorldMap{

    public HellPortalMap(SimulationSetup setup) {
        super(new Vector2d(setup.mapWidth(), setup.mapHeight()));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public void adjustAnimalPosition(Vector2d oldPosition, Animal animal) {}

}
