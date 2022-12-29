package agh.ics.opp.simulation.gui;

import agh.ics.opp.simulation.map.elements.animal.Animal;
import agh.ics.opp.simulation.map.elements.IMapElement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.io.FileInputStream;
import java.io.IOException;

public class GuiElementBox {
    private final VBox box = new VBox();

    public GuiElementBox(IMapElement element) {
        box.setStyle("-fx-background-color: #99FF8A; -fx-border-color: #6DD945; -fx-border-width: 2px; -fx-border-top-width: 0px; -fx-border-left-width: 0px");
        if (element == null) {
            Label label = new Label(" ");
            box.getChildren().add(label);
        } else {
            try (FileInputStream fileInStr = new FileInputStream(element.getImagePath())){
                Image image = new Image(fileInStr);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                if(element instanceof Animal) {
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(25);
                    box.setPadding(new Insets(5,0,0,0));
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
    public void renderElement(GridPane rootElement, int x, int y){
        rootElement.add(box, x, y);
    }
}

