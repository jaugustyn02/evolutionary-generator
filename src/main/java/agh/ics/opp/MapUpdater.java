package agh.ics.opp;

public class MapUpdater {
    private final IWorldMap map;
    private final IGenomeMutator mutations;
    private final IAnimalMover behavior;
    private final IPlantsGrower preferences;

    public MapUpdater(IWorldMap map, IGenomeMutator mutations, IAnimalMover behavior, IPlantsGrower preferences){
        this.map = map;
        this.mutations = mutations;
        this.behavior = behavior;
        this.preferences = preferences;
    }
}
