package agh.ics.oop.gui;

import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    Image elementImage;
    AbstractMapElement mapElement;
    int boxSize;

    public GuiElementBox(AbstractMapElement mapElement, int boxSize) throws FileNotFoundException {
        this.boxSize = boxSize;
        this.mapElement = mapElement;
        String URL = "src/main/resources/" + mapElement.getImageName();
        try {
            this.elementImage = new Image(new FileInputStream(URL));
        } catch (FileNotFoundException ex) {
            System.out.println("Files not found -> " + ex);
        }
    }

    public GuiElementBox(boolean jungle, int boxSize) throws FileNotFoundException {
        this.boxSize = boxSize;
        String URL = "src/main/resources/" + ((jungle) ? "jungle.png" : "grass.png");
        try {
            this.elementImage = new Image(new FileInputStream(URL));
        } catch (FileNotFoundException ex) {
            System.out.println("Files not found -> " + ex);
        }
    }

    public VBox mapElementVbox(double scale) {
        ImageView mapElementImage = new ImageView(elementImage);
        mapElementImage.setFitWidth(boxSize * scale);
        mapElementImage.setFitHeight(boxSize * scale);
//        mapElementLabel.setFont(new Font(10));
        VBox elementVBox = new VBox();
        elementVBox.getChildren().addAll(mapElementImage);
        elementVBox.setAlignment(Pos.CENTER);

        return elementVBox;

    }
}
