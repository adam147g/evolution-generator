package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;


public class App extends Application implements IObserver{
    private SimulationEngine engine;
    private RectangularMap map;
    private GridPane grid;


    public void init() {
        String[][] testArgs = {
                {"0","0","0","0","1","2","1","2"},
                {"4","0","0","6","0","0","6","7"}};
        List<List<Integer>> directions = new OptionsParser().parse2D(testArgs);
        this.map = new RectangularMap(15,15);
        Vector2d[] positions = {new Vector2d(2,2), new Vector2d(1,4)};
        this.engine = new SimulationEngine(directions, map, positions, 20);
        this.engine.addObserver(this);
        engine.setMoveDelay(300);
        grid = new GridPane();
    }

    public void drawMap(boolean initialGrid) {

        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);

        int boxSize = 40;


        for (int i = 0; i < map.getRightTopCorner().y - map.getLeftBottomCorner().y + 2; i++) {
            for (int j = 0; j < map.getRightTopCorner().x - map.getLeftBottomCorner().x + 2; j++) {
                Label label;
                if (i == 0 && j == 0) {
                    label = new Label("y/x");
                } else if (i == 0) {
                    label = new Label(Integer.toString(map.getLeftBottomCorner().x + j - 1));
                } else if (j == 0) {
                    label = new Label(Integer.toString(map.getLeftBottomCorner().y + i - 1));
                } else {
                    label = new Label("");
                }

                grid.add(label, j, i);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
        // render each mapElement on map
        engine.map.getMapElements().forEach((pos, element) -> {
            GuiElementBox box;
            try {
                box = new GuiElementBox(element);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            grid.add(box.mapElementVbox(), pos.x + 1 + Math.abs(map.getLeftBottomCorner().x), pos.y + 1 + Math.abs(map.getLeftBottomCorner().y));
        });
        if (initialGrid) {
            for (int i = 0; i <= map.getRightTopCorner().x - map.getLeftBottomCorner().x + 1; i++)
                grid.getColumnConstraints().add(new ColumnConstraints(boxSize));
            for (int j = 0; j <= map.getRightTopCorner().y - map.getLeftBottomCorner().y + 1; j++)
                grid.getRowConstraints().add(new RowConstraints(boxSize));
        }
    }

    public void start(Stage primaryStage) {
//        TextField movesInput = new TextField();
        Button startButton = new Button("Run");
//        VBox inputBox = new VBox(movesInput, startButton);
        VBox inputBox = new VBox(startButton);
        VBox appBox = new VBox(this.grid, inputBox);
        grid.setAlignment(Pos.CENTER);
        inputBox.setAlignment(Pos.CENTER);
        appBox.setAlignment(Pos.CENTER);
//        movesInput.setMaxWidth(100);

        startButton.setOnAction(ev -> {
//            String[] args = movesInput.getText().split("");
//            List<Integer> directions = new OptionsParser().parse1D(args);
//            engine.setMoves(directions);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });


        drawMap(true);
        Scene scene = new Scene(appBox, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void elementMoved() {
        Platform.runLater(() -> {
            grid.getChildren().clear();
            drawMap(false);
        });
    }
}
