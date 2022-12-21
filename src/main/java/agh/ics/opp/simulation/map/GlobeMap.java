package agh.ics.opp.simulation.map;

import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;

public class GlobeMap extends AbstractWorldMap{
    public GlobeMap(SimulationSetup setup){
        super(new Vector2d(setup.mapWidth()-1, setup.mapHeight()-1));
    }

    @Override
    public void correctAnimalPosition(Animal animal){
        if (overHorizontalBorders(animal.getPosition())){
            animal.turnBack();
            animal.setPosition(animal.getPosition().add(animal.getDirection().toUnitVector()));
        }
        else if (overEastBorder(animal.getPosition()))
            animal.setPosition(new Vector2d(0, animal.getPosition().y));
        else if (overWestBorder(animal.getPosition()))
            animal.setPosition(new Vector2d(getUpperRight().x, animal.getPosition().y));
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
}
