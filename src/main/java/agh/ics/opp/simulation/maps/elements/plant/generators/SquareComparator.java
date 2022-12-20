package agh.ics.opp.simulation.maps.elements.plant.generators;

import agh.ics.opp.simulation.types.Vector2d;

import java.util.Comparator;

public class SquareComparator implements Comparator<Vector2d> {
    private final AntiToxicGenerator generator;
    public SquareComparator(AntiToxicGenerator generator){
        this.generator = generator;
    }
    @Override
    public int compare(Vector2d o1, Vector2d o2) {
        if (generator.getNumOfDeaths(o1).equals(generator.getNumOfDeaths(o2)))
            return o1.compareTo(o2);
        return generator.getNumOfDeaths(o1).compareTo(generator.getNumOfDeaths(o2));
    }
}
