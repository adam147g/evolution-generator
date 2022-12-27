package agh.ics.oop;


import agh.ics.oop.gui.IObserver;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    public final List<Animal> animals;
    public final List<Plant> plants;
    private int plantsPerDay = 5;
    private final CONFIG config;
    public RectangularMap map;
    private int moveDelay = 0;
    private List<IObserver> observers = new ArrayList<>();
//    public SimulationEngine(CONFIG config) {
//        this.config = config;
//        this.map = map;
//        this.animals = new ArrayList<>();
//        this.plants = new ArrayList<>();
//    }
    public SimulationEngine(List<List<Integer>> genotypes, RectangularMap map, Vector2d[] initialPositions) {
        this.config = null;
        this.map = map;
        this.animals = new ArrayList<>();
        this.plants = null;
        int i = 0;
        for (Vector2d positionToAdd : initialPositions) {
            Animal animalToAdd = new Animal(map, positionToAdd, genotypes.get(i));
            i++;
            if (this.map.place(animalToAdd))
                animals.add(animalToAdd);
        }
    }
    public SimulationEngine(List<List<Integer>> genotypes, RectangularMap map, Vector2d[] initialPositions, int initialPlants) {
        this.config = null;
        this.map = map;
        this.animals = new ArrayList<>();
        this.plants = null;
        for (int i = 0; i < initialPlants; i++) {
            this.map.spawnPlantRandomly();
        }
        int i = 0;
        for (Vector2d positionToAdd : initialPositions) {
            Animal animalToAdd = new Animal(map, positionToAdd, genotypes.get(i));
            i++;
            if (this.map.place(animalToAdd))
                animals.add(animalToAdd);
        }
    }
    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }
    public void addObserver(IObserver app) {
        this.observers.add(app);
    }
    // for each animal runs sequentially its movement set
    public void runSequentially() {
        for (int i = 0; i < animals.size(); i++) {
            Animal currentAnimal = animals.get(i);
            if (currentAnimal.isAlive()) {
                List<Integer> currentGenotype = currentAnimal.getGenotype();
                System.out.println("Animal " + i + " genotyp: " + currentAnimal.getGenotype());
                System.out.println("Animal " + i + " position: " + currentAnimal.position.toString());
                for (int j = 0; j < currentGenotype.size(); j++) {
                    currentAnimal.move(currentGenotype.get(j));
                    System.out.println("Animal " + i + " position: " + currentAnimal.position.toString() + " after move " + (j + 1));
                }
                System.out.println("\n");
            }
        }
    }
    // runs animals like parallel simulation - each time one move per animal
    public void runParallel(int intervals) {
        for (int m = 0; m < intervals; m++){
            int k = 0;
            while (k < CONSTANTS.DEFAULT_GENOTYPE_SIZE) {
                for (int i = 0; i < animals.size(); i++) {
                    Animal currentAnimal = animals.get(i);
                    if (currentAnimal.isAlive()) {
                        int currentMove = currentAnimal.getGenotype().get(k);
                        currentAnimal.move(currentMove);
                        System.out.println("Animal " + i + " position: " + currentAnimal.position.toString() + " after move " + (k + 1));
                    }
                }
                k++;
            }
            System.out.println("genotype loop end");
        }
    }
    public void runParallelPlants(int intervals, boolean plantSpawn, int initialPlants, int plantPerDay) {
        for (int m = 0; m < intervals; m++){
            int k = 0;
            while (k < CONSTANTS.DEFAULT_GENOTYPE_SIZE) {
                for (int i = 0; i < animals.size(); i++) {
                    Animal currentAnimal = animals.get(i);
                    if (currentAnimal.isAlive()) {
                        int currentMove = currentAnimal.getGenotype().get(k);
                        currentAnimal.move(currentMove);
//                        System.out.println("Animal " + i + " position: " + currentAnimal.position.toString() + " after move " + (k + 1) + " and its energy: " + currentAnimal.getEnergy());
                    }
                }
                k++;
                for (int i = 0; i < plantPerDay; i++) {
                    this.map.spawnPlantRandomly();
                }
            }
            System.out.println("genotype loop end");
        }
    }
    public void stats() {
        System.out.println("Number of animals: " + animals.size());
        for (int i = 0; i < animals.size(); i++) {
            Animal currentAnimal = animals.get(i);
            System.out.println("    Animal " + i + " position: " + currentAnimal.position.toString() + " and its energy: " + currentAnimal.getEnergy());
        }
        System.out.println("Number of plants: " + animals.size());

    }

    @Override
    public void run() {
        while (true){
            for (int i = 0; i < animals.size(); i++) {
                Animal currentAnimal = animals.get(i);
                if (currentAnimal.isAlive()) {
                    int currentMove = currentAnimal.getGenotype().get(currentAnimal.getDayOfLife() % CONSTANTS.DEFAULT_GENOTYPE_SIZE);
                    currentAnimal.move(currentMove);
                }
            }
            for (IObserver observer : observers) {
                observer.elementMoved();
            }
            try {
                System.out.println("Sleeping..");
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted -> " + ex);
            }
        }
    }
}
