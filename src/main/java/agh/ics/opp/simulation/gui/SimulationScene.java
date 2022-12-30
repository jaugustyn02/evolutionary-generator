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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationScene {
    public void setScene(Stage primaryStage, SimulationSetup setup, String csvFileName){
        //map
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(800);
        gridPane.setMaxHeight(800);
        gridPane.setGridLinesVisible(true);
//        gridPane.setPadding(new Insets(0, 0, 20, 0)); // 0 20 20 0

        // pause button
        Button pauseButton = new Button();
        pauseButton.setText("Pause");
        pauseButton.setFont(new Font(20));
//        pauseButton.setStyle("-fx-background-color: #ff0000; ");
        String cssLayoutPause = ("""
                -fx-background-color:rgba(238,18,18,0.74);
                    -fx-background-radius: 30;
                    -fx-background-insets: 0,1,2,3,0;
                    -fx-text-fill: white;
                    -fx-font-weight: bold;
                    -fx-font-size: 16px;
                    -fx-padding: 10 20 10 20;""");
        String cssLayoutResume = ("""
                -fx-background-color:rgba(18,238,47,0.74);
                    -fx-background-radius: 30;
                    -fx-background-insets: 0,1,2,3,0;
                    -fx-text-fill: white;
                    -fx-font-weight: bold;
                    -fx-font-size: 16px;
                    -fx-padding: 10 20 10 20;""");
        pauseButton.setStyle(cssLayoutPause);
        pauseButton.setMinWidth(800);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(pauseButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox mapBox = new VBox(40);
        mapBox.getChildren().add(gridPane);
        mapBox.getChildren().add(buttonBox);

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
//        animalCount.setStyle("-fx-padding: 0 0 0 20");
        //stats
        Label statsLabel = new Label();
        statsLabel.setId("statsLabel");
        statsLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-padding: 20 20 20 20; -fx-line-spacing: 16");
        VBox statsBox = new VBox();
        statsBox.getChildren().add(statsLabel);
        String cssLayout = """
                -fx-border-color: black;
                -fx-border-insets: 5;
                -fx-border-width: 2;
                -fx-border-style: dashed;
                """;
        statsBox.setStyle(cssLayout);

        HBox statsPlace = new HBox(animalCount, statsBox);
        statsPlace.setSpacing(20);
        //HBox top = new HBox(mapBox, statsPlace);


        //animal stats
        Label animalLabel = new Label();
        animalLabel.setId("animalLabel");
        animalLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 14pt; -fx-padding: 40 40 40 20; -fx-line-spacing: 10");
        VBox animalStats = new VBox(animalLabel);

        animalStats.setStyle(cssLayout);


        // all on page
        VBox right = new VBox(statsPlace, animalStats);
        right.setMinWidth(900);

        HBox root = new HBox(mapBox, right);
        root.setSpacing(20);
        root.setPadding(new Insets(40, 0, 0, 40));
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Scene scene = new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);

        try {
            IWorldMap map = (setup.mapVariant() ?
                    new HellPortalMap(setup) :  // true
                    new GlobeMap(setup)         // false
            );


            StatisticsRunner stats = new StatisticsRunner(map, csvFileName);
            MapRenderer renderer = new MapRenderer(gridPane, map, stats);
            final SimulationEngine engine = new SimulationEngine(setup, map, renderer, stats , statsPlace, animalStats);
            engine.setMoveDelay(500);
            Thread engineThread = new Thread(engine);
            engineThread.start();

            pauseButton.setOnAction((event) -> {
                if(engine.isPaused()){
                    pauseButton.setText("Pause");
//                    pauseButton.setStyle("-fx-background-color: #ff0000; ");
                    pauseButton.setStyle(cssLayoutPause);
                    engine.resume();
                }
                else {
                    pauseButton.setText("Resume");
                    renderer.render(true);
//                    pauseButton.setStyle("-fx-background-color: #00ff00; ");
                    pauseButton.setStyle(cssLayoutResume);
                    engine.pause();
                    engineThread.interrupt();
                }
            });

            primaryStage.setOnCloseRequest(event -> {
                engine.stop();
                engineThread.interrupt();
            });
        }
        catch (FileNotFoundException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
