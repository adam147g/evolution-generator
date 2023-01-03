package agh.ics.oop;


import agh.ics.oop.gui.IObserver;

import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable {
    public final ArrayList<Animal> animals;
//    public final ArrayList<Plant> plants;
    public SimulationActions actions = new SimulationActions(this);
    private final CONFIG config;

    public RectangularMap map;
    private int moveDelay = 0;
    private ArrayList<IObserver> observers = new ArrayList<>();
    public SimulationEngine(CONFIG config) {
        this.config = config;
        if (config.plantGrowthType == 0) {
            this.map = new RectangularMap(config.mapWidth, config.mapHeight, 0.2);
        } else if (config.plantGrowthType == 1) {
            this.map = new RectangularMap(config.mapWidth, config.mapHeight);
        }
        this.map.setConfig(config);
        this.animals = new ArrayList<>();
        actions.initializeAnimalsRandomly(this.config.initialAnimalNumber);
        actions.growNewPlants(this.config.initialPlantsNumber);

    }

    public SimulationEngine(RectangularMap map, int initialAnimalsNumber, int initialPlantsNumber, int plantsPerDay) {
        this.config = new CONFIG();
        this.map = map;
        this.animals = new ArrayList<>();
        actions.initializeAnimalsRandomly(initialAnimalsNumber);
        actions.growNewPlants(initialPlantsNumber);
    }
    public SimulationEngine(RectangularMap map, Vector2d[] initialPositions, ArrayList<ArrayList<Integer>> genotypes, int initialPlants) {
        this.config = new CONFIG();
        this.map = map;
        this.animals = new ArrayList<>();
//        this.plants = null;
//        for (int i = 0; i < initialPlants; i++) {
////            this.map.spawnPlantRandomly();
//        }
        int i = 0;
        for (Vector2d positionToAdd : initialPositions) {
            Animal animalToAdd = new Animal(map, positionToAdd, genotypes.get(i));
            i++;
            if (this.map.place(animalToAdd))
                animals.add(animalToAdd);
        }
    }
    public SimulationEngine(RectangularMap map, Vector2d[] initialPositions)  {
        this.config = new CONFIG();
        this.map = map;
        this.animals = new ArrayList<>();
//        this.plants = null;
        for (Vector2d position : initialPositions) {
            initializeAnimal(position);
        }
    }

    public void initializeAnimal(Vector2d position) {
        Animal animalToAdd = new Animal(map, position);
        if (this.map.place(animalToAdd))
            animals.add(animalToAdd);
    }
    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }
    public void addObserver(IObserver app) {
        this.observers.add(app);
    }
    // for each animal runs sequentially its movement set
    /*
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
    */


    // MAIN RUN
    public void runParallelPlants(int days) {
        int k = 0;
        while (k < days && !this.map.animalElementsMap.isEmpty()) {
            System.out.println();
            System.out.println(k + " day");
            stats();
            actions.cleanDeadAnimals();
            actions.moveAnimals();
            actions.eatPlants();
//            actions.breedAnimals();
            actions.growNewPlants(this.config.newPlantsPerDay);
            k++;
        }
    }
    public void stats() {
        System.out.println(map);
        System.out.println("STATISTICS");
        System.out.println("Number of all animals: " + animals.size());
        System.out.println("Number of alive animals: " + (animals.size() - this.map.deathCount));
//        for (Animal currentAnimal : animals) {
//            System.out.println("    Animal id " + currentAnimal.id + ((currentAnimal.isDead()) ? " - dead" : " - alive") + " - position: " + currentAnimal.position.toString());
////            System.out.println("        direction: " + currentAnimal.getDirection().toString() + " - genotype: " + currentAnimal.getGenotype());
//            //
////            System.out.println(currentAnimal.getCurrentGenomeIndex());
//            //
////            System.out.println("        curr index: " + currentAnimal.getCurrentGenomeIndex() + " - curr genome: " + currentAnimal.getGenotype().get(currentAnimal.getCurrentGenomeIndex()));
//            System.out.println("        energy: " + currentAnimal.getEnergy() + " | age: " + currentAnimal.getAge() + " | descendants: " + currentAnimal.getDescendantsNumber() + " | plants eaten: " + currentAnimal.getPlantsEaten());
//        }
        System.out.println(this.map.animalElementsMap.keySet());
        System.out.println(this.map.animalElementsMap.values());
        System.out.println("Number of plants: " + this.map.plantElementsMap.size());
        int i = 0;
//        for (Vector2d plantPosition : this.map.plantElementsMap.keySet()) {
//            System.out.println("    Plant " + i + " position: " + plantPosition);
//            i++;
//        }
    }

    public ArrayList<Integer> getBestGenotype(){
        int bestIndex = 0;
        int maxDescendant = 0;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getDescendantsNumber() > maxDescendant) {
                maxDescendant = animals.get(i).getDescendantsNumber();
                bestIndex = i;
            }
        }
        return animals.get(bestIndex).getGenotype();
    }

    public String getAvgEnergyAmongAliveAnimals(){
        int aliveAnimalTotalEnergy = 0;
        int aliveAnimalNumber = 0;
        for (Animal animal : animals) {
            if (animal.isAlive()) {
                aliveAnimalTotalEnergy += animal.getEnergy();
                aliveAnimalNumber += 1;
            }
        }
        if (aliveAnimalNumber == 0) {
            return "no live animals";
        }
        return Integer.toString(aliveAnimalTotalEnergy / aliveAnimalNumber);
    }

    public String getAvgAgeAmongDeadAnimals(){
        int deadAnimalsTotalAge = 0;
        int deadAnimalNumber = 0;
        for (Animal animal : animals) {
            if (animal.isDead()) {
                deadAnimalsTotalAge += animal.getAge();
                deadAnimalNumber += 1;
            }
        }
        if (deadAnimalNumber == 0) {
            return "no dead animals";
        }
        return Integer.toString(deadAnimalsTotalAge / deadAnimalNumber);
    }

    @Override
    public void run() {
        int k = 0;
        int days = 15;
        while (!this.map.animalElementsMap.isEmpty()){
//            System.out.println();
//            System.out.println(k + " day");
//            stats();
            actions.cleanDeadAnimals();
            actions.moveAnimals();
            actions.eatPlants();
            actions.breedAnimals();
            actions.growNewPlants(this.config.newPlantsPerDay);
            k++;

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
        stats();
    }
}
