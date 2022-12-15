package agh.ics.opp.variants.maps;

import agh.ics.opp.elements.Animal;
import agh.ics.opp.SimulationSetup;
import agh.ics.opp.Vector2d;

public class HellPortalMap extends AbstractWorldMap{
    private final int portalEnergyConsumption;
    
    public HellPortalMap(SimulationSetup setup) {
        super(new Vector2d(setup.mapWidth()-1, setup.mapHeight()-1));
        this.portalEnergyConsumption = setup.animalEnergyConsumption();
    }

    @Override
    public void correctAnimalPosition(Animal animal) {
        if(overBorder(animal.getPosition())) {
            Vector2d oldPosition = animal.getPosition(); // for sout
            animal.setPosition(Vector2d.getRandom(super.getLowerLeft(), super.getUpperRight()));
            animal.reduceEnergy(portalEnergyConsumption);
            System.out.println("Portal przeteleportował zwierzę z "+oldPosition+" na "+animal.getPosition()); // to show that works
        }
    }

    private boolean overBorder(Vector2d position){
        return position.x > super.getUpperRight().x || position.x < super.getLowerLeft().x ||
                position.y > super.getUpperRight().y || position.y < super.getLowerLeft().y;
    }

}
