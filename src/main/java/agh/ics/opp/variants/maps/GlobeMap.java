package agh.ics.opp.variants.maps;

import agh.ics.opp.elements.Animal;
import agh.ics.opp.SimulationSetup;
import agh.ics.opp.Vector2d;

public class GlobeMap extends AbstractWorldMap{
    private final int width;
    private final int height;
    public GlobeMap(SimulationSetup setup){
        super(new Vector2d(setup.mapWidth(), setup.mapHeight()));
        this.currentPlantsNumber = setup.initialNumOfPlants();
        this.width = setup.mapWidth();
        this.height = setup.mapHeight();
    }

    @Override
    public void correctAnimalPosition(Animal animal){
        if (overHorizontalBorders(animal.getPosition())){
            animal.turnBack();
            animal.setPosition(animal.getPosition().add(animal.getDirection().toUnitVector()));
        }
        else {
            if (overEastBorder(animal.getPosition()))
                animal.setPosition(new Vector2d(0, animal.getPosition().y));
            else if (overWestBorder(animal.getPosition()))
                animal.setPosition(new Vector2d(getUpperRight().x, animal.getPosition().y));
        }
    }

    private boolean overHorizontalBorders(Vector2d position) {
        return position.y < super.getLowerLeft().y || position.y > super.getUpperRight().y;
    }
    private boolean overEastBorder(Vector2d position){
        return position.x > super.getUpperRight().x;
    }
    private boolean overWestBorder(Vector2d position){
        return position.x < super.getLowerLeft().x;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
