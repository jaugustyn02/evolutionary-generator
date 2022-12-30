
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
        this.renderer = renderer;
        this.stats = stats;
        this.statsPlace = statsPlace;
        this.animalData.setName("Number of animals");
        this.plantData.setName("Number of plants");
        this.renderer.render();
        chart = statsPlace.lookup("#chart");
        @SuppressWarnings("unchecked") LineChart<Number, Number> chart1 = (LineChart<Number, Number>)chart;
        chart1.getData().add(animalData);
        chart1.getData().add(plantData);
    }

    @Override
    public void run() {
        while (running) {
            synchronized (this) {
                try {
                    Thread.sleep(moveDelay);
                } catch (InterruptedException e) {
//                    System.out.println("Exception: The simulation has been aborted (1)");
                }
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
//                        System.out.println("Exception: The simulation has been aborted (2)");
                    }
                }
                dayNum++;
                updater.nextDay();      // simulate next day
                this.renderer.render();
                stats.updateStatistics(dayNum);       // updating statistics after next day passed

                Platform.runLater(() -> {
                    element = statsPlace.lookup("#statsLabel");
                    Label label = (Label)element;
                    label.setText("Day: " + (dayNum) + "\n" + stats.getStatistics().toString());
                    animalData.getData().add(new XYChart.Data<>(dayNum, stats.getNumOfAnimals()));
                    plantData.getData().add(new XYChart.Data<>(dayNum, stats.getNumOfPlants()));

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