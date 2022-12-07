package agh.ics.opp;

public class GlobeMap extends AbstractWorldMap{
    private final SimulationSetup setup;

    public GlobeMap(SimulationSetup setup){
        super(new Vector2d(setup.mapWidth(), setup.mapHeight()));
        this.setup = setup;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.followsY(super.getLowerLeft()) && position.precedesY(super.getUpperRight());
    }

    public boolean circledFromRight(Vector2d position){
        return position.x > super.getUpperRight().x;
    }

    public boolean circledFromLeft(Vector2d position){
        return position.x < super.getLowerLeft().x;
    }

    @Override
    public void adjustAnimalPosition(Vector2d oldPosition, Animal animal){
        if (canMoveTo(animal.getPosition())){
            if (circledFromRight(animal.getPosition()))
                animal.setPosition(new Vector2d(0, animal.getPosition().y));
            else if (circledFromLeft(animal.getPosition()))
                animal.setPosition(new Vector2d(getUpperRight().x, animal.getPosition().y));
        }
        else animal.turnBack();
    }
}
