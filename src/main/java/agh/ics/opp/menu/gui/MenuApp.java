
package agh.ics.opp.menu.gui;

import agh.ics.opp.SetupParser;
import agh.ics.opp.simulation.gui.SimulationScene;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;


public class MenuApp extends Application {
    private int SimulationNumber = 1;
    @Override
    public void start(Stage primaryStage){

        // title
        Label title = new Label("Generator ewolucyjny");
        title.setFont(new Font(36));
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(title);
        hBox1.setAlignment(Pos.CENTER);
        // end

        // configuration and start buttons
        Button setupButton = new Button("Select configuration file");
        setupButton.setFont(new Font(12));
        Button startButton = new Button("Start");
        startButton.setFont(new Font(12));
        HBox hBox2 = new HBox(10);
        hBox2.getChildren().add(setupButton);
        hBox2.getChildren().add(startButton);
        hBox2.setAlignment(Pos.CENTER);
        // end

        // file select
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Configuration File");
        fileChooser.setInitialDirectory(new File("src/main/configurations/"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        // end

        VBox vBox = new VBox(30);
        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(hBox2);

        Scene scene = new Scene(vBox, 450, 150);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

        // buttons setOnAction
        AtomicReference<File> selectedFile = new AtomicReference<>();
        setupButton.setOnAction((event -> {
            selectedFile.set(fileChooser.showOpenDialog(primaryStage));
            if (selectedFile.get() != null){
                setupButton.setText(selectedFile.get().getName());
            }
            else {
                setupButton.setText("No file selected");
            }
        }));

        startButton.setOnAction(event -> {
            if(selectedFile.get() != null){
                try (FileReader file = new FileReader(selectedFile.get().getAbsoluteFile())){
                    BufferedReader bf = new BufferedReader(file);
                    SimulationSetup setup = (new SetupParser()).parseSetup(bf.lines().toArray(String[]::new));
                    if(setup != null) {
                        Stage newStage = new Stage();
                        String newTitle = selectedFile.get().getName();
                        newStage.setTitle("Symulacja "+SimulationNumber+" - " + newTitle.substring(0, newTitle.length() - 4));
                        (new SimulationScene()).setScene(newStage, setup);
                        newStage.setFullScreen(true);
                        newStage.show();
                        SimulationNumber++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else System.out.println("No configuration file selected");
        });

        // end program when menu window closed
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }
}

