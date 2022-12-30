package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.IAnimalClickedObserver;
import agh.ics.opp.simulation.StatisticsRunner;
import agh.ics.opp.simulation.map.elements.IMapElement;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.types.Vector2d;
import javafx.application.Platform;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.io.FileNotFoundException;


public class MapRenderer{
    private final GridPane rootPane;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private final IWorldMap map;
    public int fieldGrow;
    private IAnimalClickedObserver animalObserver;
    private StatisticsRunner stats;

    public MapRenderer(GridPane rootPane, IWorldMap map, StatisticsRunner stats) throws FileNotFoundException {
        this.rootPane = rootPane;
        this.lowerLeft = map.getLowerLeft();
        this.upperRight = map.getUpperRight();
        this.map = map;
        this.fieldGrow = 800/(Math.max(map.getHeight(), map.getWidth()));
        this.stats = stats;

    }

    public void setAnimalObserver(IAnimalClickedObserver animalObserver) {
        this.animalObserver = animalObserver;
    }

    public void render(boolean simulationStopped){
        int[] mostPopularGenome = (simulationStopped ? stats.getStatistics().mostPopularGenome() : null);
        Platform.runLater(() -> {
            rootPane.getColumnConstraints().clear();
            rootPane.getRowConstraints().clear();
            rootPane.getChildren().clear();
            rootPane.setGridLinesVisible(true);

            addColumnConstraints();

            for (int row = upperRight.y-lowerLeft.y; row >= 0; row--) {
                renderRow(row, mostPopularGenome);
            }
        });
    }

    private void addColumnConstraints(){
        for (int col = 0; col <= upperRight.x-lowerLeft.x; col++) {
            rootPane.getColumnConstraints().add(new ColumnConstraints(fieldGrow));
        }
    }

    private void renderRow(int row, int[] mostPopularGenome){
        rootPane.getRowConstraints().add(new RowConstraints(this.fieldGrow));
        for (int col = 0; col <= upperRight.x-lowerLeft.x; col++){
            GuiElementBox elementBox = new GuiElementBox((IMapElement) this.map.objectAt(new Vector2d(col, row)), fieldGrow,  animalObserver, mostPopularGenome);
            elementBox.renderElement(rootPane, map, col - lowerLeft.x, upperRight.y - row);
        }
    }




//    public void render(){
//        Platform.runLater(() -> {
//            rootPane.getColumnConstraints().clear();
//            rootPane.getRowConstraints().clear();
//            rootPane.getChildren().clear();
//            rootPane.setGridLinesVisible(true);
//
//            rootPane.getColumnConstraints().add(new ColumnConstraints(fieldGrow));
//            renderHeader();
//
//            for (int row = upperRight.y; row >= lowerLeft.y; row--) {
//                renderRow(row);
//            }
//        });
//    }
//
//    private void renderHeader(){
//        rootPane.getRowConstraints().add(new RowConstraints(this.fieldGrow));
//        for (int col = lowerLeft.x; col <= upperRight.x; col++) {
//            rootPane.getColumnConstraints().add(new ColumnConstraints(fieldGrow));
//        }
//    }
//
//    private void renderRow(int row){
//        rootPane.getRowConstraints().add(new RowConstraints(this.fieldGrow));
//        for (int col = lowerLeft.x; col <= upperRight.x; col++){
//            GuiElementBox elementBox = new GuiElementBox((IMapElement) this.map.objectAt(new Vector2d(col, row)), fieldGrow);
//            elementBox.renderElement(rootPane, map, col - lowerLeft.x + 1, upperRight.y - row + 1);
//        }
//    }
}