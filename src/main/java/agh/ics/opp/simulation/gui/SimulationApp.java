package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.SimulationEngine;
import agh.ics.opp.simulation.maps.GlobeMap;
import agh.ics.opp.simulation.maps.HellPortalMap;
import agh.ics.opp.simulation.maps.IWorldMap;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.application.Application;
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
        HBox hBox = new HBox(10);
        hBox.getChildren().add(pauseButton);

        VBox vBox = new VBox(3);
        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(hBox);

        Scene scene = new Scene(vBox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        try {
            SimulationSetup setup = new SimulationSetup(
                    true, 6, 6,
                    true, 4, 5, 3,
                    true, 7, 10, 4, 3, 10,
                    true, 1, 5
            );
            IWorldMap map = (setup.mapVariant() ?
                    new HellPortalMap(setup) :  // true
                    new GlobeMap(setup)         // false
            );
            MapRenderer renderer = new MapRenderer(gridPane, map);
            final SimulationEngine engine = new SimulationEngine(setup, map, renderer);
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
