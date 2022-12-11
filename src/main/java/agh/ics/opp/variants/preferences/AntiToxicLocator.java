package agh.ics.opp.variants.preferences;

import agh.ics.opp.Vector2d;
import agh.ics.opp.variants.maps.IWorldMap;

public class AntiToxicLocator implements IPlantPlacementLocator{
    final private IWorldMap map;

    public AntiToxicLocator(IWorldMap map) {
        this.map = map;
    }

    @Override
    public Vector2d getNewPlantPosition() {
        return null;
    }
}
