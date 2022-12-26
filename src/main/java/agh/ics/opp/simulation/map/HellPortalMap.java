package agh.ics.opp.simulation.map;

import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.types.SimulationSetup;
import agh.ics.opp.simulation.types.Vector2d;

public class HellPortalMap extends AbstractWorldMap{
    private final int portalEnergyConsumption;
    
    public HellPortalMap(SimulationSetup setup) {
        super(new Vector2d(setup.mapWidth()-1, setup.mapHeight()-1));
        this.portalEnergyConsumption = setup.animalEnergyConsumption();
    }

    @Override
    public void correctAnimalPosition(Animal animal) {
        if(overBorder(animal.getPosition())) {
            animal.setPosition(Vector2d.getRandom(super.getLowerLeft(), super.getUpperRight()));
            animal.reduceEnergy(portalEnergyConsumption);
        }
    }

    private boolean overBorder(Vector2d position){
        return position.x > super.getUpperRight().x || position.x < super.getLowerLeft().x ||
                position.y > super.getUpperRight().y || position.y < super.getLowerLeft().y;
    }

}
