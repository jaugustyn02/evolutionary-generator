package agh.ics.opp.variants.maps;

import agh.ics.opp.elements.Animal;
import agh.ics.opp.SimulationSetup;
import agh.ics.opp.Vector2d;

public class HellPortalMap extends AbstractWorldMap{
    
    private final int portalEnergyConsumption;
    
    public HellPortalMap(SimulationSetup setup) {
        super(new Vector2d(setup.mapWidth(), setup.mapHeight()));
        this.portalEnergyConsumption = setup.animalEnergyConsumption();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return  (! (objectAt(position) instanceof Animal));
    }

    @Override
    public void correctAnimalPosition(Vector2d oldPosition, Animal animal) {
        if( overBorder(animal.getPosition())) {
                animal.setPosition(Vector2d.getRandom(super.getLowerLeft(), super.getUpperRight()));
                animal.reduceEnergy( portalEnergyConsumption);
        }
        else{
            animal.setPosition(oldPosition);
        }
    }
    private boolean overBorder(Vector2d position){
        return position.x > super.getUpperRight().x || position.x < super.getLowerLeft().x ||
                position.y > super.getUpperRight().y || position.y < super.getLowerLeft().y;
    }


}
