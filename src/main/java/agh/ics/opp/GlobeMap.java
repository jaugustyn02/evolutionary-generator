package agh.ics.opp;

public class GlobeMap extends AbstractWorldMap{
    private final SimulationSetup setup;
    public GlobeMap(Vector2d upperRight, SimulationSetup setup){
        super(upperRight);
        this.setup = setup;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.followsY(super.getLowerLeft()) && position.precedesY(super.getUpperRight());
    }

    public boolean circledFromRight(Vector2d position){
        return position.getCoordinate(0) > super.getUpperRight().getCoordinate(0);
    }

    public boolean circledFromLeft(Vector2d position){
        return position.getCoordinate(0) < super.getLowerLeft().getCoordinate(0);
    }

    @Override
    public void applyMoveToMapRules(Animal animal, Vector2d newPosition){
        if(canMoveTo(newPosition)){
            if(circledFromRight(newPosition))
                newPosition = animal.getPosition().subtractX(super.getUpperRight());
            else if(circledFromLeft(newPosition))
                newPosition = animal.getPosition().addX(super.getUpperRight());
            animal.moveToPosition(newPosition);
        }
        else animal.turnBack();
    }
}
