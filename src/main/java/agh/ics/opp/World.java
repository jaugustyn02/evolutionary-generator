package agh.ics.opp;

public class World {
    public static void main(String[] args){
        SimulationSetup setup = new SimulationSetup(
                true, 5, 7,
                false, 5, 1, 3,
                false, 3, 5, 5, 3, 5,
                false, 0, 0
        );
        IEngine engine = new SimulationEngine(setup);
        engine.run();
    }
}
