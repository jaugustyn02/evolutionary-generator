package agh.ics.opp;

public class HellPortalMap extends AbstractWorldMap{
    private final MapUpdater mapUpdater;

    public HellPortalMap(Vector2d upperRight,  IGenomeMutator mutator, IAnimalMover mover, IPlantsGrower grower) {
        super(upperRight);
        this.mapUpdater = new MapUpdater(this, mutator, mover, grower);
        super.mapUpdater = this.mapUpdater;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }
}
