package agh.ics.opp.variants.maps;

import agh.ics.opp.elements.Animal;
import agh.ics.opp.SimulationSetup;
import agh.ics.opp.Vector2d;

public class HellPortalMap extends AbstractWorldMap{

    public HellPortalMap(SimulationSetup setup) {
        super(new Vector2d(setup.mapWidth(), setup.mapHeight()));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public void adjustAnimalPosition(Vector2d oldPosition, Animal animal) {
        System.out.println("not this one yet");
    }

}
