package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.IAnimalClickedObserver;
import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.elements.IMapElement;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class GuiElementBox {
    private final VBox box = new VBox();


    public GuiElementBox(IMapElement element, int fieldGrow, IAnimalClickedObserver animalObserver, int[] mostPopularGenome) {
        box.setStyle("-fx-background-color: #99FF8A; -fx-border-color: #6DD945; -fx-border-width: 2px; -fx-border-top-width: 0px; -fx-border-left-width: 0px");
        if (element == null) {
            Label label = new Label(" ");
            box.getChildren().add(label);
        } else {
            try (FileInputStream fileInStr = new FileInputStream(element.getImagePath())){
                Image image = new Image(fileInStr);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(fieldGrow-5);
                imageView.setFitHeight(fieldGrow-5);
                if(element instanceof Animal animal) {
                    if(Arrays.equals(mostPopularGenome, animal.getGenome())){
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setBrightness(0.5); // Increase brightness by 0.5
                        imageView.setEffect(colorAdjust);
                    }
                    imageView.setFitHeight(fieldGrow-5);
                    imageView.setFitWidth(Math.round((fieldGrow-5)*0.9));
                    imageView.setOnMouseClicked(event -> Platform.runLater(() -> animalObserver.animalClicked(animal)));


                }
                box.getChildren().add(imageView);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage());
            }

            box.setAlignment(Pos.CENTER);
        }
    }
    public void renderElement(GridPane rootElement, IWorldMap map, int x, int y){
        if(y >= map.getEquatorBottom()+1&& y<= map.getEquatorTop()+1){
            box.setStyle("-fx-background-color:#239911; -fx-border-color: #6DD945; -fx-border-width: 2px; -fx-border-top-width: 0px; -fx-border-left-width: 0px");
        }
        else box.setStyle("-fx-background-color: #99FF8A; -fx-border-color: #6DD945; -fx-border-width: 2px; -fx-border-top-width: 0px; -fx-border-left-width: 0px");
        rootElement.add(box, x, y);
    }
}


