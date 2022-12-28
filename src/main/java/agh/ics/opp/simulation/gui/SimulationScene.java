package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.SimulationEngine;
import agh.ics.opp.simulation.StatisticsRunner;
import agh.ics.opp.simulation.map.GlobeMap;
import agh.ics.opp.simulation.map.HellPortalMap;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class SimulationScene {
    public void setScene(Stage primaryStage, SimulationSetup setup){
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

        // Important
        Scene scene = new Scene(vBox, 500, 500);
        primaryStage.setScene(scene); // Najważniejsza część - podpięcie sceny do primaryStage
        // Brak primaryStage.show() - metoda show() jest wywoływana w MenuApp po wykonaniu się tej metody
        // end

        try {
            IWorldMap map = (setup.mapVariant() ?
                    new HellPortalMap(setup) :  // true
                    new GlobeMap(setup)         // false
            );
            MapRenderer renderer = new MapRenderer(gridPane, map);
            StatisticsRunner stats = new StatisticsRunner(map);     // temp
            final SimulationEngine engine = new SimulationEngine(setup, map, renderer, stats);
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
