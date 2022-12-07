package agh.ics.opp;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args){
        SimulationSetup setup = new SimulationSetup(
                false, 5, 6,
                false, 5, 2, 5,
                false, 3, 10, 5, 3, 5,
                false, 0, 0
        );
        IEngine engine = new SimulationEngine(setup);
        engine.run();
    }
}
