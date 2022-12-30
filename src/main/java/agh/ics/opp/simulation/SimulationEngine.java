
package agh.ics.opp.simulation;
import agh.ics.opp.simulation.gui.MapRenderer;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.MapUpdater;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SimulationEngine implements IEngine, Runnable, IAnimalClickedObserver{
    private final MapRenderer renderer;
    private final MapUpdater updater;
    private final StatisticsRunner stats;
    public HBox statsPlace;
    public VBox animalStats;
    private int moveDelay = 1000;
    private int dayNum = 0;
    public Node element;
    public Node chart;
    public Node animalStatsNode;
    private final XYChart.Series<Number, Number> animalData = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> plantData = new XYChart.Series<>();
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private Animal trackedAnimal = null;
    private Integer deathDay = null;


    public SimulationEngine(SimulationSetup setup, IWorldMap map, MapRenderer renderer, StatisticsRunner stats, HBox statsPlace, VBox animalStats) {
        this.updater = new MapUpdater(map, setup);
        this.renderer = renderer;
        renderer.setAnimalObserver(this);
        this.stats = stats;
        this.statsPlace = statsPlace;
        this.animalStats = animalStats;
        this.animalData.setName("Number of animals");
        this.plantData.setName("Number of plants");
        this.renderer.render(false);
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
                } catch (InterruptedException ignored) {

                }
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {

                    }
                }
                dayNum++;
                updater.nextDay();      // simulate next day
                stats.updateStatistics(dayNum);       // updating statistics after next day passed
                this.renderer.render(false);
                updateStatsChart();
                if(trackedAnimal!= null){updateAnimalStats();}


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

    @Override
    public void animalClicked(Animal animal) {
        trackedAnimal = animal;
        deathDay = null;
        updateAnimalStats();
    }
    private void updateAnimalStats(){
        Platform.runLater(() -> {

            animalStatsNode = animalStats.lookup("#animalLabel");
            Label label = (Label)animalStatsNode;
            if (trackedAnimal.getEnergy() ==0 && deathDay== null){
                deathDay = dayNum;
            }
            label.setText(trackedAnimal.getAnimalStatistics() + (deathDay==null ? "" :
                    "\n Death day: " + deathDay));
        });
    }
    private void updateStatsChart(){
        Platform.runLater(() -> {
            element = statsPlace.lookup("#statsLabel");
            Label label = (Label)element;
            label.setText("Day: " + (dayNum) + "\n" + stats.getStatistics().toString());
            animalData.getData().add(new XYChart.Data<>(dayNum, stats.getNumOfAnimals()));
            plantData.getData().add(new XYChart.Data<>(dayNum, stats.getNumOfPlants()));

        });
    }
}