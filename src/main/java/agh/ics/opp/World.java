package agh.ics.opp;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        SimulationSetup setup = new SimulationSetup(
                MapVariant.GlobeMap, 5, 6,
                GrowerVariant.CentralGrower, 5, 2, 5,
                MoverVariant.InOrderMover, 3, 10, 5, 3, 5,
                MutatorVariant.RandomMutator, 0, 0
        );
        SimulationWorld world = new SimulationWorld(setup);
        world.showMap();
        for(int i=0; i<5; i++) {
            world.startNextDay();
            world.showMap();
        }
    }
}
