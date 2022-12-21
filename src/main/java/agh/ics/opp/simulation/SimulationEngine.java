package agh.ics.opp.simulation;


import agh.ics.opp.simulation.gui.MapRenderer;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.MapUpdater;
import agh.ics.opp.simulation.types.SimulationSetup;

public class SimulationEngine implements IEngine, Runnable{
    private final IWorldMap map;
    private final MapRenderer renderer;
    private final MapUpdater updater;
    private int moveDelay = 1000;
    private int dayNum = 0;

    private volatile boolean running = true;
    private volatile boolean paused = false;

    public SimulationEngine(SimulationSetup setup, IWorldMap map, MapRenderer renderer) {
        this.updater = new MapUpdater(map, setup);
        this.map = map;
        this.renderer = renderer;
        this.renderer.render();
        System.out.println(map);
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) {
                try {
                    Thread.sleep(moveDelay);
                } catch (InterruptedException e) {
                    System.out.println("Exception: The simulation has been aborted (1)");
//                    throw new RuntimeException(e);
                }
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println("Exception: The simulation has been aborted (2)");
                    }
                }
                updater.nextDay();
                dayNum++;
                System.out.println("Day " + (dayNum) + ":");
                this.renderer.render();
                System.out.println(map);
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        synchronized (this) {
            notify();
        }
    }

    public void stop() {
        running = false;
    }

    public boolean isPaused(){
        return paused;
    }

    public void setMoveDelay(int moveDelay){
        this.moveDelay = moveDelay;
    }
}
