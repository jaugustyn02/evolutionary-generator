package agh.ics.opp;


public class InOrderMover implements IAnimalMover{
    private final IWorldMap map;
    public InOrderMover(IWorldMap map){
        this.map = map;
    }

    @Override
    public void makeMove(Animal animal) {
        int geneIndex = animal.getNextGeneIndex();
        animal.turnBy(animal.getGenome()[geneIndex]);
        Vector2d newPosition = animal.getPosition().add(animal.getDirection().toUnitVector());
        map.removeMapElement(animal);
        map.applyMoveToMapRules(animal, newPosition);
        map.placeMapElement(animal);
        animal.incrementGeneIndex();
    }
}
