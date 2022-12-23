package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    public final List<Animal> animals;
    public final List<Plant> plants;
    private final CONFIG config;
    private AbstractMap map;
    public SimulationEngine(CONFIG config) {
        this.config = config;
        this.map = map;
        this.animals = new ArrayList<>();
        this.plants = new ArrayList<>();
    }
    public SimulationEngine(List<List<Integer>> genotypes, AbstractMap map, Vector2d[] initialPositions) {
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
    // for each animal runs sequentially its movement set
    public void runSequentially() {
        for (int i = 0; i < animals.size(); i++) {
            Animal currentAnimal = animals.get(i);
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
    // runs animals like parallel simulation - each time one move per animal
    public void runParallel(int intervals) {
        for (int m = 0; m < intervals; m++){
            int k = 0;
            while (k < CONSTANTS.DEFAULT_GENOTYPE_SIZE) {
                for (int i = 0; i < animals.size(); i++) {
                    Animal currentAnimal = animals.get(i);
                    int currentMove = currentAnimal.getGenotype().get(k);
                    currentAnimal.move(currentMove);
                    System.out.println("Animal " + i + " position: " + currentAnimal.position.toString() + " after move " + (k + 1));
                }
                k++;
            }
            System.out.println("genotype loop end");
        }
    }
}
