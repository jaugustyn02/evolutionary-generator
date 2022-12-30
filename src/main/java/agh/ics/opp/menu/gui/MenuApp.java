
package agh.ics.opp.menu.gui;

import agh.ics.opp.SetupParser;
import agh.ics.opp.simulation.gui.SimulationScene;
import agh.ics.opp.simulation.types.SimulationSetup;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.*;
import java.util.concurrent.atomic.AtomicReference;



public class MenuApp extends Application {
    private int simulationNum = 1;
    @Override
    public void start(Stage primaryStage){

        // title
        Label title = new Label("Generator ewolucyjny");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(title);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPadding(new Insets(10, 0, 0, 0));
        // end

        // configuration and start buttons
        String cssLayout = ("""
                -fx-background-color:#4646cb;
                    -fx-background-radius: 30;
                    -fx-background-insets: 0,1,2,3,0;
                    -fx-text-fill: white;
                    -fx-font-weight: bold;
                    -fx-font-size: 14px;
                    -fx-padding: 10 20 10 20;""");
        Button setupButton = new Button("Select configuration file");
        setupButton.setStyle(cssLayout);
        setupButton.setFont(new Font(14));
        Button startButton = new Button("Start");
        startButton.setStyle(cssLayout);
        startButton.setFont(new Font(14));
        HBox hBox2 = new HBox(20);
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

        // csv file checkbox
        CheckBox csvCheckBox = new CheckBox("Save simulation statistics in CSV file");

        HBox hBox3 = new HBox();
        hBox3.getChildren().add(csvCheckBox);
        hBox3.setAlignment(Pos.CENTER);
        // end

        // authors label
        Label authors = new Label("Authors:  Laura Wiktor,  Jan Augustyn");
        authors.setFont(new Font(12));
        HBox hBox4 = new HBox();
        hBox4.getChildren().add(authors);
        hBox4.setAlignment(Pos.CENTER_LEFT);
        hBox4.setPadding(new Insets(60, 5, 4, 5));
        // end

        VBox vBox = new VBox(40);
        vBox.getChildren().add(hBox1);
        vBox.getChildren().add(hBox2);
        vBox.getChildren().add(hBox3);
        vBox.getChildren().add(hBox4);

        Scene scene = new Scene(vBox, 500, vBox.getPrefHeight());
        vBox.setStyle("-fx-background-color: white");
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
                setupButton.setText("No configuration file selected");

            }
        }));

        startButton.setOnAction(event -> {
            if(selectedFile.get() != null){
                try (FileReader file = new FileReader(selectedFile.get().getAbsoluteFile())){
                    BufferedReader bf = new BufferedReader(file);
                    SimulationSetup setup = (new SetupParser()).parseSetup(bf.lines().toArray(String[]::new));
                    if(setup != null) {
                        if(!setup.isValid()){
                            System.out.println("Selected configuration is not valid");
                            return;
                        }

                        Stage newStage = new Stage();
                        String setupFileName = selectedFile.get().getName();
                        String setupRawFileName = setupFileName.substring(0, setupFileName.length() - 4);
                        newStage.setTitle("Symulacja "+ simulationNum +" - " + setupRawFileName);
                        String csvFileName = (csvCheckBox.isSelected() ?
                                "s"+simulationNum+"_"+setupRawFileName+"_stats.csv" : null);
                        (new SimulationScene()).setScene(newStage, setup, csvFileName);
                        if (csvFileName != null){
                            System.out.println("Simulation statistics filename is: "+csvFileName);
                        }
                        newStage.setFullScreen(true);
                        newStage.show();
                        simulationNum++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else System.out.println("No configuration file selected");
        });
        // end

        // end program when menu window closed
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }
}
