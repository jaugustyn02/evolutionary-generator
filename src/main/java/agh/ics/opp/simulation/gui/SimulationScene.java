package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.SimulationEngine;
import agh.ics.opp.simulation.StatisticsRunner;
import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.HellPortalMap;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationScene {
    public void setScene(Stage primaryStage, SimulationSetup setup){
        //map
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(800);
        gridPane.setMaxHeight(800);
        gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(0, 20, 20, 0));

        // pause button
        Button pauseButton = new Button();
        pauseButton.setText("Pause");
        pauseButton.setStyle("-fx-background-color: #ff0000; ");
        pauseButton.setMinWidth(80);
        HBox hBox = new HBox(10);
        hBox.getChildren().add(pauseButton);
        hBox.setAlignment(Pos.CENTER);
        VBox mapBox = new VBox(3);
        mapBox.getChildren().add(gridPane);
        mapBox.getChildren().add(hBox);

        //chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Days");
        xAxis.setTickUnit(1);
        yAxis.setTickUnit(1);
        xAxis.setMinorTickVisible(false);
        yAxis.setMinorTickVisible(false);
        xAxis.setTickMarkVisible(false);
        LineChart<Number, Number> animalCount = new LineChart<>(xAxis, yAxis);
        animalCount.setId("chart");
        animalCount.setCreateSymbols(false);
        animalCount.setStyle("-fx-padding: 40 0 0 20");
        //stats
        Label statsLabel = new Label();
        statsLabel.setId("statsLabel");
        statsLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-padding: 40 40 40 20; -fx-line-spacing: 10");
        HBox statsPlace = new HBox(animalCount, statsLabel);
        //HBox top = new HBox(mapBox, statsPlace);


        //animal stats

        Label animalLabel = new Label();
        animalLabel.setId("animalLabel");
        animalLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-padding: 40 40 40 40; -fx-line-spacing: 10");
        VBox animalStats = new VBox(animalLabel);

        // all on page
        VBox right = new VBox(statsPlace, animalStats);
        right.setMinWidth(900);

        HBox root = new HBox(mapBox, right);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Scene scene = new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight());

        primaryStage.setScene(scene); // Najważniejsza część - podpięcie sceny do primaryStage
        // Brak primaryStage.show() - metoda show() jest wywoływana w MenuApp po wykonaniu się tej metody
        // end

        try {
            IWorldMap map = (setup.mapVariant() ?
                    new HellPortalMap(setup) :  // true
                    new GlobeMap(setup)         // false
            );
            MapRenderer renderer = new MapRenderer(gridPane, map, animalStats);

            StatisticsRunner stats = new StatisticsRunner(map);     // temp
            final SimulationEngine engine = new SimulationEngine(setup, map, renderer, stats , statsPlace);
            engine.setMoveDelay(500);
            Thread engineThread = new Thread(engine);
            engineThread.start();

            pauseButton.setOnAction((event) -> {
                if(engine.isPaused()){
                    pauseButton.setText("Pause");
                    pauseButton.setStyle("-fx-background-color: #ff0000; ");
                    engine.resume();
                }
                else {
                    pauseButton.setText("Resume");
                    pauseButton.setStyle("-fx-background-color: #00ff00; ");
                    engine.pause();
                    engineThread.interrupt();
                }
            });

            primaryStage.setOnCloseRequest(event -> {
                engine.stop();
                engineThread.interrupt();
                // Brak System.exit(0) - od teraz zamknięcie okna symulacji nie kończy całego programu
            });
        }
        catch (FileNotFoundException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
