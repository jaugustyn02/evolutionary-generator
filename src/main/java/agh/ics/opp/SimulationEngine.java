package agh.ics.opp;

//import agh.ics.oop.gui.MapRenderer;

import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private final IWorldMap map;
    private final MapUpdater updater;
    private int moveDelay = 100;
//    private final MapRenderer renderer;

    public SimulationEngine(SimulationSetup setup) {
        this.map = (setup.mapVariant() ? new HellPortalMap(setup) : new GlobeMap(setup));
        this.updater = new MapUpdater(map, setup);
        System.out.println(map);
    }

    @Override
    public void run() {
        for (int i=0; i<10; i++){
            updater.nextDay();
            System.out.println(map);
        }

//        if(renderer != null) this.renderer.render();
//        for (MoveDirection direction: this.directions) {
//            try{
//                Thread.sleep(moveDelay);
//            }catch (InterruptedException e){
//                System.out.println("Exception: The simulation has been aborted");
//                throw new RuntimeException(e);
//            }
//        }
    }

    public void setMoveDelay(int moveDelay){
        this.moveDelay = moveDelay;
    }
}
