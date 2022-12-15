package agh.ics.opp;

public class World {
    public static void main(String[] args){
        SimulationSetup setup = new SimulationSetup(
                true, 6, 6,
                false, 4, 5, 3,
                true, 7, 10, 4, 3, 10,
                true, 1, 5
        );
        IEngine engine = new SimulationEngine(setup);
        engine.run();
    }
}
