
package agh.ics.opp.simulation;
import agh.ics.opp.simulation.gui.MapRenderer;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.MapUpdater;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class SimulationEngine implements IEngine, Runnable{
    //    private final IWorldMap map;
    private final MapRenderer renderer;
    private final MapUpdater updater;
    private final StatisticsRunner stats;
    public HBox statsPlace;
    private int moveDelay = 1000;
    private int dayNum = 0;
    public Node element;
    public Node chart;
    private final XYChart.Series<Number, Number> animalData = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> plantData = new XYChart.Series<>();
    private volatile boolean running = true;
    private volatile boolean paused = false;

    public SimulationEngine(SimulationSetup setup, IWorldMap map, MapRenderer renderer, StatisticsRunner stats, HBox statsPlace) {
        this.updater = new MapUpdater(map, setup);
//        this.map = map;
        this.renderer = renderer;
        this.stats = stats;
        this.statsPlace = statsPlace;
        this.renderer.render();
        this.animalData.setName("Number Of Animals");
        this.plantData.setName("Number Of Plants");
//        System.out.println(map);
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
                dayNum++;
                updater.nextDay();
                this.renderer.render();
                stats.updateStatistics();
                System.out.println("Day " + (dayNum) + ":");
//                System.out.println(map);
                System.out.println(stats.getStatistics());
                Platform.runLater(() -> {
                    element = statsPlace.lookup("#statsLabel");
                    Label label = (Label)element;
                    label.setText("Day " + (dayNum) + stats.getStatistics().toString());

                    chart = statsPlace.lookup("#chart");
                    LineChart<Number, Number> chart1 = (LineChart<Number, Number>)chart;

                    animalData.getData().add(new XYChart.Data<>(dayNum, stats.getNumOfAnimals()));
                    chart1.getData().add(animalData);
                    plantData.getData().add(new XYChart.Data<>(dayNum, stats.getNumOfPlants()));
                    chart1.getData().add(plantData);
                });


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