package agh.ics.opp.variants.maps;

import agh.ics.opp.elements.Animal;
import agh.ics.opp.SimulationSetup;
import agh.ics.opp.Vector2d;

public class HellPortalMap extends AbstractWorldMap{
    private final int portalEnergyConsumption;
    private final int width;
    private final int height;
    
    public HellPortalMap(SimulationSetup setup) {
        super(new Vector2d(setup.mapWidth(), setup.mapHeight()));
        this.portalEnergyConsumption = setup.animalEnergyConsumption();
        this.currentPlantsNumber = setup.initialNumOfPlants();
        this.width = setup.mapWidth();
        this.height = setup.mapHeight();
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

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

}
