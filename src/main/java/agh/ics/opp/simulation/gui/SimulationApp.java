package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.SimulationEngine;
import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.HellPortalMap;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage){
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        Button pauseButton = new Button();
        pauseButton.setText("Pause");
        pauseButton.setStyle("-fx-background-color: #ff0000; ");
        pauseButton.setMinWidth(80);
        HBox hBox = new HBox(10);
        hBox.getChildren().add(pauseButton);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(3);
        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(hBox);

        Scene scene = new Scene(vBox, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            SimulationSetup setup = new SimulationSetup(
                    false, 10, 10,
                    false, 10, 5, 3,
                    false, 20, 12, 4, 3, 10,
                    false, 1, 5
            );
            IWorldMap map = (setup.mapVariant() ?
                    new HellPortalMap(setup) :  // true
                    new GlobeMap(setup)         // false
            );
            MapRenderer renderer = new MapRenderer(gridPane, map);
            final SimulationEngine engine = new SimulationEngine(setup, map, renderer);
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
                System.exit(0);
            });
        }
        catch (FileNotFoundException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
