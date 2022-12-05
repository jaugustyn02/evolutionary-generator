package agh.ics.opp;

public class GlobeMap extends AbstractWorldMap{
    private final MapUpdater mapUpdater;
    public GlobeMap(Vector2d upperRight, IGenomeMutator mutator, IAnimalMover mover, IPlantsGrower grower){
        super(upperRight);
        this.mapUpdater = new MapUpdater(this, mutator, mover, grower);
        super.mapUpdater = this.mapUpdater;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.followsX(super.lowerLeft()) && position.precedesX(this.upperRight);
    }
}
