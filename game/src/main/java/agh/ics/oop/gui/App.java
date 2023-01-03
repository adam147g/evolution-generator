package agh.ics.oop.gui;

import agh.ics.oop.*;
import com.sun.javafx.scene.control.IntegerField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class App extends Application implements IObserver{
    private SimulationEngine engine;
    private RectangularMap map;
    private GridPane grid;
    private VBox statsBox;
    private VBox singleStatsBox;
    private HBox simulationWindow;
    private Stage stage;

    int default_width = 1600;
    int default_height = 800;


//    public void init() {
//        this.map = new RectangularMap(20,10, 0.2) {
//        };
//        this.engine = new SimulationEngine(map, 10, 10, 4);
//        this.engine.addObserver(this);
//        this.engine.setMoveDelay(500);
//        this.grid = new GridPane();
//        System.out.println();
//        this.engine.stats();
//    }

    public void drawMap(boolean initialGrid) {

        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);

        int boxSize = Math.min((int) ((default_height - 200) / this.engine.map.maxRangeY), (int) ((((2 * default_width) - 50 /3) / this.engine.map.maxRangeX))) ;


        for (int i = 0; i < map.getRightTopCorner().y - map.getLeftBottomCorner().y + 1; i++) {
            for (int j = 0; j < map.getRightTopCorner().x - map.getLeftBottomCorner().x + 1; j++) {
                GuiElementBox box = null;
                try {

                    if (this.engine.map.plantGrowthType == 0) {
                        if (new Vector2d(j, i).includesIn(this.engine.map.leftBottomJungle, this.engine.map.rightTopJungle)) {
                            box = new GuiElementBox(true, boxSize);
                        } else {
                            box = new GuiElementBox(false, boxSize);
                        }
                    } else if (this.engine.map.plantGrowthType == 1) {
                        if (this.engine.map.theLeastDeathCountLeader.contains(new Vector2d(j, i))) {
                            box = new GuiElementBox(true, boxSize);
                        } else {
                            box = new GuiElementBox(false, boxSize);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                grid.add(box.mapElementVbox(1), j, i);
            }
        }
        // render each mapElement on map
        // plants
        engine.map.getPlantElementsMap().forEach((pos, plantElement) -> {
            GuiElementBox box;
            try {
                box = new GuiElementBox(plantElement, boxSize);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            grid.add(box.mapElementVbox(0.9), pos.x, Math.abs(this.engine.map.maxRangeY - pos.y - 1));
        });
        // animals
        engine.map.getAnimalElementsMap().forEach((pos, mapId) -> mapId.forEach((id, animalElement) -> {
            GuiElementBox box;
            try {
                box = new GuiElementBox(animalElement, boxSize);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            grid.add(box.mapElementVbox(0.9), pos.x, Math.abs(this.engine.map.maxRangeY - pos.y - 1));
        }));

        if (initialGrid) {
            for (int i = 0; i <= map.getRightTopCorner().x - map.getLeftBottomCorner().x; i++)
                grid.getColumnConstraints().add(new ColumnConstraints(boxSize));
            for (int j = 0; j <= map.getRightTopCorner().y - map.getLeftBottomCorner().y; j++)
                grid.getRowConstraints().add(new RowConstraints(boxSize));
        }
    }

    public void showStats(boolean initial) {

        Label label = new Label("Statistics");
        label.setStyle("-fx-font-weight: bold");

        HBox animalNumberBox = new HBox();
        Label animalNumberLabel = new Label("Animal number: ");
        animalNumberBox.getChildren().addAll(animalNumberLabel);
        animalNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox plantNumberBox = new HBox();
        Label plantNumberLabel = new Label("Plant number: ");
        plantNumberBox.getChildren().addAll(plantNumberLabel);
        plantNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox freeSpacesNumberBox = new HBox();
        Label freeSpacesNumberLabel = new Label("Free spaces: ");
        freeSpacesNumberBox.getChildren().addAll(freeSpacesNumberLabel);
        freeSpacesNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox mostPopularGenotypeBox = new HBox();
        Label mostPopularGenotypeLabel = new Label("Most popular genotype: ");
        mostPopularGenotypeBox.getChildren().addAll(mostPopularGenotypeLabel);
        mostPopularGenotypeBox.setAlignment(Pos.BASELINE_LEFT);

        HBox averageEnergyAmongAliveAnimalsBox = new HBox();
        Label averageEnergyAmongAliveAnimalsLabel = new Label("Average energy among alive animals: ");
        averageEnergyAmongAliveAnimalsBox.getChildren().addAll(averageEnergyAmongAliveAnimalsLabel);
        averageEnergyAmongAliveAnimalsBox.setAlignment(Pos.BASELINE_LEFT);

        HBox averageAgeAmongDeadAnimalsBox = new HBox();
        Label averageAgeAmongDeadAnimalsLabel = new Label("Average age among dead animals: ");
        averageAgeAmongDeadAnimalsBox.getChildren().addAll(averageAgeAmongDeadAnimalsLabel);
        averageAgeAmongDeadAnimalsBox.setAlignment(Pos.BASELINE_LEFT);

        statsBox.setAlignment(Pos.CENTER);
        statsBox.setStyle("-fx-padding: 26;");
        statsBox.setSpacing(10);
        statsBox.getChildren().addAll(label, animalNumberBox,
                plantNumberBox,
                freeSpacesNumberBox,
                mostPopularGenotypeBox,
                averageEnergyAmongAliveAnimalsBox,
                averageAgeAmongDeadAnimalsBox);
        if (!initial) {
            int animalNumberValue = engine.animals.size() - map.deathCount;
            Label animalNumberLabelVal = new Label(Integer.toString(animalNumberValue));
            animalNumberBox.getChildren().add(animalNumberLabelVal);

            int plantNumberValue = map.getPlantElementsMap().size();
            Label plantNumberLabelVal = new Label(Integer.toString(plantNumberValue));
            plantNumberBox.getChildren().add(plantNumberLabelVal);

            Set<Vector2d> takenPlaces = new HashSet<Vector2d>(map.getAnimalElementsMap().keySet());
            takenPlaces.addAll(map.getPlantElementsMap().keySet());
            int freeSpacesNumberValue = (map.maxRangeY * map.maxRangeX) - takenPlaces.size();
            Label freeSpacesNumberLabelVal = new Label(Integer.toString(freeSpacesNumberValue));
            Label freeSpacesNumberLabelVal2 = new Label(" out of " + (map.maxRangeY * map.maxRangeX));
            freeSpacesNumberBox.getChildren().addAll(freeSpacesNumberLabelVal, freeSpacesNumberLabelVal2);

            Label mostPopularGenotypeLabelVal = new Label(engine.getBestGenotype().toString());
            mostPopularGenotypeBox.getChildren().add(mostPopularGenotypeLabelVal);

            Label averageEnergyAmongAliveAnimalsLabelVal = new Label(engine.getAvgEnergyAmongAliveAnimals());
            averageEnergyAmongAliveAnimalsBox.getChildren().add(averageEnergyAmongAliveAnimalsLabelVal);

            Label averageAgeAmongDeadAnimalsLabelVal = new Label(engine.getAvgAgeAmongDeadAnimals());
            averageAgeAmongDeadAnimalsBox.getChildren().add(averageAgeAmongDeadAnimalsLabelVal);

        }


    }






//
//    public void start1(Stage primaryStage) {
////        TextField movesInput = new TextField();
//        Button startButton = new Button("Run");
////        VBox inputBox = new VBox(movesInput, startButton);
//        VBox inputBox = new VBox(startButton);
//        VBox appBox = new VBox(this.grid, inputBox);
//        grid.setAlignment(Pos.CENTER);
//        inputBox.setAlignment(Pos.CENTER);
//        appBox.setAlignment(Pos.CENTER);
////        movesInput.setMaxWidth(100);
//
//        startButton.setOnAction(ev -> {
//            Thread engineThread = new Thread(engine);
//            engineThread.start();
//        });
//
//        drawMap(true);
//        Scene scene = new Scene(appBox, default_width, default_height);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }

    public void start(Stage primaryStage) {
        stage = primaryStage;
        VBox vBox = new VBox();
        Label label = new Label("Adjust configuration");
        label.setStyle("-fx-font-weight: bold");

        HBox mapWidthBox = new HBox();
        Label mapWidthLabel = new Label("Map width: ");
        IntegerField mapWidthInteger = new IntegerField();
        mapWidthInteger.setEditable(true);
        mapWidthInteger.setValue(10);
        mapWidthBox.getChildren().addAll(mapWidthLabel, mapWidthInteger);
        mapWidthBox.setAlignment(Pos.BASELINE_LEFT);

        HBox mapHeightBox = new HBox();
        Label mapHeightLabel = new Label("Map height: ");
        IntegerField mapHeightInteger = new IntegerField();
        mapHeightInteger.setEditable(true);
        mapHeightInteger.setValue(10);
        mapHeightBox.getChildren().addAll(mapHeightLabel, mapHeightInteger);
        mapHeightBox.setAlignment(Pos.BASELINE_LEFT);

        HBox mapVariantBox = new HBox();
        ComboBox<String> mapVariantList = new ComboBox<>();
        Label mapVariantLabel = new Label("Map variant: ");
        mapVariantList.getItems().addAll("The Globe", "Infernal Portal");
        mapVariantList.setValue("The Globe");
        mapVariantBox.getChildren().addAll(mapVariantLabel, mapVariantList);
        mapVariantBox.setAlignment(Pos.BASELINE_LEFT);

        HBox initialPlantsNumberBox = new HBox();
        Label initialPlantsNumberLabel = new Label("Initial plants number: ");
        IntegerField initialPlantsNumberInteger = new IntegerField();
        initialPlantsNumberInteger.setEditable(true);
        initialPlantsNumberInteger.setValue(5);
        initialPlantsNumberBox.getChildren().addAll(initialPlantsNumberLabel, initialPlantsNumberInteger);
        initialPlantsNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox eatingPlantEnergyBox = new HBox();
        Label eatingPlantEnergyLabel = new Label("Eating plant energy: ");
        IntegerField eatingPlantEnergyInteger = new IntegerField();
        eatingPlantEnergyInteger.setEditable(true);
        eatingPlantEnergyInteger.setValue(50);
        eatingPlantEnergyBox.getChildren().addAll(eatingPlantEnergyLabel, eatingPlantEnergyInteger);
        eatingPlantEnergyBox.setAlignment(Pos.BASELINE_LEFT);

        HBox energyLossPerMoveBox = new HBox();
        Label energyLossPerMoveLabel = new Label("Energy loss per move: ");
        IntegerField energyLossPerMoveInteger = new IntegerField();
        energyLossPerMoveInteger.setEditable(true);
        energyLossPerMoveInteger.setValue(30);
        energyLossPerMoveBox.getChildren().addAll(energyLossPerMoveLabel, energyLossPerMoveInteger);
        energyLossPerMoveBox.setAlignment(Pos.BASELINE_LEFT);

        HBox newPlantsPerDayBox = new HBox();
        Label newPlantsPerDayLabel = new Label("New plants per day number: ");
        IntegerField newPlantsPerDayInteger = new IntegerField();
        newPlantsPerDayInteger.setEditable(true);
        newPlantsPerDayInteger.setValue(5);
        newPlantsPerDayBox.getChildren().addAll(newPlantsPerDayLabel, newPlantsPerDayInteger);
        newPlantsPerDayBox.setAlignment(Pos.BASELINE_LEFT);

        HBox plantGrowthTypeBox = new HBox();
        ComboBox<String> plantGrowthTypeList = new ComboBox<>();
        Label plantGrowthTypeLabel = new Label("Plant growth type: ");
        plantGrowthTypeList.getItems().addAll("Forested Equator", "Toxic Corpses");
        plantGrowthTypeList.setValue("Forested Equator");
        plantGrowthTypeBox.getChildren().addAll(plantGrowthTypeLabel, plantGrowthTypeList);
        plantGrowthTypeBox.setAlignment(Pos.BASELINE_LEFT);

        HBox initialAnimalNumberBox = new HBox();
        Label initialAnimalNumberLabel = new Label("Initial animal number: ");
        IntegerField initialAnimalNumberInteger = new IntegerField();
        initialAnimalNumberInteger.setEditable(true);
        initialAnimalNumberInteger.setValue(5);
        initialAnimalNumberBox.getChildren().addAll(initialAnimalNumberLabel, initialAnimalNumberInteger);
        initialAnimalNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox initialEnergyBox = new HBox();
        Label initialEnergyLabel = new Label("Initial energy: ");
        IntegerField initialEnergyInteger = new IntegerField();
        initialEnergyInteger.setEditable(true);
        initialEnergyInteger.setValue(100);
        initialEnergyBox.getChildren().addAll(initialEnergyLabel, initialEnergyInteger);
        initialEnergyBox.setAlignment(Pos.BASELINE_LEFT);

        HBox readyForBreedEnergyBox = new HBox();
        Label readyForBreedEnergyLabel = new Label("Minimal energy for breeding: ");
        IntegerField readyForBreedEnergyInteger = new IntegerField();
        readyForBreedEnergyInteger.setEditable(true);
        readyForBreedEnergyInteger.setValue(60);
        readyForBreedEnergyBox.getChildren().addAll(readyForBreedEnergyLabel, readyForBreedEnergyInteger);
        readyForBreedEnergyBox.setAlignment(Pos.BASELINE_LEFT);

        HBox energyLossForBreedBox = new HBox();
        Label energyLossForBreedLabel = new Label("Energy loss for breed (double of that will be initial energy of new born animal): ");
        IntegerField energyLossForBreedInteger = new IntegerField();
        energyLossForBreedInteger.setEditable(true);
        energyLossForBreedInteger.setValue(40);
        energyLossForBreedBox.getChildren().addAll(energyLossForBreedLabel, energyLossForBreedInteger);
        energyLossForBreedBox.setAlignment(Pos.BASELINE_LEFT);

        HBox minMutationNumberBox = new HBox();
        Label minMutationNumberLabel = new Label("Minimum number of mutation: ");
        IntegerField minMutationNumberInteger = new IntegerField();
        minMutationNumberInteger.setEditable(true);
        minMutationNumberInteger.setValue(0);
        minMutationNumberBox.getChildren().addAll(minMutationNumberLabel, minMutationNumberInteger);
        minMutationNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox maxMutationNumberBox = new HBox();
        Label maxMutationNumberLabel = new Label("Maximum number of mutation: ");
        IntegerField maxMutationNumberInteger = new IntegerField();
        maxMutationNumberInteger.setEditable(true);
        maxMutationNumberInteger.setValue(0);
        maxMutationNumberBox.getChildren().addAll(maxMutationNumberLabel, maxMutationNumberInteger);
        maxMutationNumberBox.setAlignment(Pos.BASELINE_LEFT);

        HBox mutationVariantBox = new HBox();
        ComboBox<String> mutationVariantList = new ComboBox<>();
        Label mutationVariantLabel = new Label("Mutation variant: ");
        mutationVariantList.getItems().addAll("Full Randomness", "Slight Correction");
        mutationVariantList.setValue("Full Randomness");
        mutationVariantBox.getChildren().addAll(mutationVariantLabel, mutationVariantList);
        mutationVariantBox.setAlignment(Pos.BASELINE_LEFT);

        HBox genotypeSizeBox = new HBox();
        Label genotypeSizeLabel = new Label("Genotype size: ");
        IntegerField genotypeSizeInteger = new IntegerField();
        genotypeSizeInteger.setEditable(true);
        genotypeSizeInteger.setValue(8);
        genotypeSizeBox.getChildren().addAll(genotypeSizeLabel, genotypeSizeInteger);
        genotypeSizeBox.setAlignment(Pos.BASELINE_LEFT);

        HBox animalBehaviourBox = new HBox();
        ComboBox<String> animalBehaviourList = new ComboBox<>();
        Label animalBehaviourLabel = new Label("Animal behaviour type: ");
        animalBehaviourList.getItems().addAll("Full Predestination", "A Bit Of Madness");
        animalBehaviourList.setValue("Full Predestination");
        animalBehaviourBox.getChildren().addAll(animalBehaviourLabel, animalBehaviourList);
        animalBehaviourBox.setAlignment(Pos.BASELINE_LEFT);

        Button button = new Button("START");
        button.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #158a20;");

        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 26;");
        vBox.setSpacing(10);
        vBox.getChildren().addAll(label, mapWidthBox, mapHeightBox, mapVariantBox, initialPlantsNumberBox, eatingPlantEnergyBox, energyLossPerMoveBox, newPlantsPerDayBox, plantGrowthTypeBox, initialAnimalNumberBox, initialEnergyBox, readyForBreedEnergyBox, energyLossForBreedBox, minMutationNumberBox, maxMutationNumberBox, mutationVariantBox, genotypeSizeBox, animalBehaviourBox, button);


        Scene startScene = new Scene(vBox, 500, 700);
        button.setOnAction(e -> simulationScene(this.stage, mapWidthBox, mapHeightBox, mapVariantBox, initialPlantsNumberBox, eatingPlantEnergyBox, energyLossPerMoveBox, newPlantsPerDayBox, plantGrowthTypeBox, initialAnimalNumberBox, initialEnergyBox, readyForBreedEnergyBox, energyLossForBreedBox, minMutationNumberBox, maxMutationNumberBox, mutationVariantBox, genotypeSizeBox, animalBehaviourBox));

        primaryStage.setTitle("Evolution Generator: Configuration");
        primaryStage.setScene(startScene);
        primaryStage.show();

    }

    private void simulationScene(Stage stage, HBox mapWidthBox, HBox mapHeightBox, HBox mapVariantBox, HBox initialPlantsNumberBox, HBox eatingPlantEnergyBox, HBox energyLossPerMoveBox, HBox newPlantsPerDayBox, HBox plantGrowthTypeBox, HBox initialAnimalNumberBox, HBox initialEnergyBox, HBox readyForBreedEnergyBox, HBox energyLossForBreedBox, HBox minMutationNumberBox, HBox maxMutationNumberBox, HBox mutationVariantBox, HBox genotypeSizeBox, HBox animalBehaviourBox) {
        CONFIG config = new CONFIG(
                ((IntegerField) mapWidthBox.getChildren().get(1)).getValue(),
                ((IntegerField) mapHeightBox.getChildren().get(1)).getValue(),
                ((((ComboBox<String>) mapVariantBox.getChildren().get(1)).getValue() == "The Globe") ? 0 : 1),
                ((IntegerField) initialPlantsNumberBox.getChildren().get(1)).getValue(),
                ((IntegerField) eatingPlantEnergyBox.getChildren().get(1)).getValue(),

                ((IntegerField) energyLossPerMoveBox.getChildren().get(1)).getValue(),
                ((IntegerField) newPlantsPerDayBox.getChildren().get(1)).getValue(),
                ((((ComboBox<String>) plantGrowthTypeBox.getChildren().get(1)).getValue() == "Forested Equator") ? 0 : 1),
                ((IntegerField) initialAnimalNumberBox.getChildren().get(1)).getValue(),
                ((IntegerField) initialEnergyBox.getChildren().get(1)).getValue(),
                ((IntegerField) readyForBreedEnergyBox.getChildren().get(1)).getValue(),
                ((IntegerField) energyLossForBreedBox.getChildren().get(1)).getValue(),
                ((IntegerField) minMutationNumberBox.getChildren().get(1)).getValue(),
                ((IntegerField) maxMutationNumberBox.getChildren().get(1)).getValue(),
                ((((ComboBox<String>) mutationVariantBox.getChildren().get(1)).getValue() == "Full Randomness") ? 0 : 1),
                ((IntegerField) genotypeSizeBox.getChildren().get(1)).getValue(),
                ((((ComboBox<String>) animalBehaviourBox.getChildren().get(1)).getValue() == "Full Predestination") ? 0 : 1)
        );
        this.engine = new SimulationEngine(config);
        this.engine.addObserver(this);
        this.engine.setMoveDelay(500);
        this.statsBox = new VBox();
        statsBox.setPrefHeight(default_height);
        statsBox.setPrefWidth((int) (default_width/3));
        this.grid = new GridPane();
        this.map = this.engine.map;

        Label label = new Label("Simulation");
        label.setStyle("-fx-font-weight: bold");
        Button startButton = new Button("Run");
        startButton.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #158a20;");
        Button stopButton = new Button("Stop");
        stopButton.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #ff0000;");

        VBox inputBox = new VBox(startButton, stopButton);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER);
        VBox simulationBox = new VBox(label, this.grid, inputBox);
        simulationBox.setPrefHeight(default_height);
        simulationBox.setPrefWidth((int) (2*default_width/3));
        simulationBox.setAlignment(Pos.CENTER);
        simulationBox.setSpacing(10);

        HBox appBox = new HBox(statsBox, simulationBox);
        grid.setAlignment(Pos.CENTER);
//        appBox.setAlignment(Pos.CENTER);

        startButton.setOnAction(ev -> {
            Thread engineThread = new Thread(engine);
            stopButton.setOnAction(ev2 -> {
                engineThread.interrupt();
            });
            engineThread.start();

        });



        drawMap(true);
        showStats(false);
        Scene scene = new Scene(appBox, default_width, default_height);
        Stage newStage = new Stage();

        newStage.setTitle("Evolution Generator: Simulation");
        newStage.setScene(scene);
        newStage.show();
    }

    @Override
    public void elementMoved() {
        Platform.runLater(() -> {
            grid.getChildren().clear();
            drawMap(false);
            statsBox.getChildren().clear();
            showStats(false);


        });
    }
}
