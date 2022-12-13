package agh.ics.opp;

public class World {
    public static void main(String[] args){
        SimulationSetup setup = new SimulationSetup(
                false, 5, 5,
                false, 5, 2, 3,
                false, 6, 10, 5, 3, 5,
                false, 0, 0
        );
        IEngine engine = new SimulationEngine(setup);
        engine.run();
    }
}
