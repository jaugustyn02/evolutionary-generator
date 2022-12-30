package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.map.IWorldMap;
import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.IMapElement;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.io.FileInputStream;
import java.io.IOException;

public class GuiElementBox {
    private final VBox box = new VBox();
    public Node labelElement;

    public GuiElementBox(IMapElement element, int fieldGrow, VBox animalStats) {
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
                if(element instanceof Animal) {
                    imageView.setFitHeight(fieldGrow-5);
                    imageView.setFitWidth(Math.round((fieldGrow-5)*0.9));
                    imageView.setOnMouseClicked(event -> Platform.runLater(() -> {
                        labelElement = animalStats.lookup("#animalLabel");
                        Label label = (Label) labelElement;
                        label.setText( ((Animal)element).getAnimalStatistics());
                    }));


                }
                box.getChildren().add(imageView);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage());
            }
//            if (element.getLabelName() != null ){
//                Label label = new Label(element.getLabelName());
//                box.getChildren().add(label);
//                label.setFont(new Font(10));
//            }
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


