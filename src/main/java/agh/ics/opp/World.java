package agh.ics.opp;

public class World {
    public static void main(String[] args){
        SimulationSetup setup = new SimulationSetup(
                false, 7, 7,
                false, 4, 2, 3,
                false, 5, 10, 5, 3, 10,
                false, 0, 0
        );
        IEngine engine = new SimulationEngine(setup);
        engine.run();
    }
}
