package agh.ics.oop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SimulationActions {
    public SimulationEngine engine;

    public SimulationActions(SimulationEngine engine) {
        this.engine = engine;
    }
    public void cleanDeadAnimals(){
        for (int i = 0; i < this.engine.animals.size(); i++) {
            Animal currentAnimal = this.engine.animals.get(i);
            if (currentAnimal.isAlive()) {
                if (currentAnimal.getEnergy() <= 0) {
                    if (this.engine.map.plantGrowthType == 1) {
                        this.engine.map.incrementDeathStat(currentAnimal.getPosition());
                    }
                    currentAnimal.turnDead();
                    this.engine.map.updateDeathCount(1);
                    this.engine.map.removeElement(currentAnimal);
                }
            }
        }
    }
    public void moveAnimals(){
        for (int i = 0; i < this.engine.animals.size(); i++) {
            Animal currentAnimal = this.engine.animals.get(i);
            if (currentAnimal.isAlive()) {
                int genomeIndex = currentAnimal.getCurrentGenomeIndex();
                int currentMove = currentAnimal.getGenotype().get(genomeIndex);

                currentAnimal.move(currentMove);

                chooseNextGenome(currentAnimal);
            }

        }
    }
    private void chooseNextGenome(Animal currentAnimal) {
        if (this.engine.map.config.animalBehaviour == 0) {
            currentAnimal.setCurrentGenomeIndex((currentAnimal.getCurrentGenomeIndex() + 1) % this.engine.map.config.genotypeSize);
        } else if (this.engine.map.config.animalBehaviour == 1) {
            animalDifferentBehaviour(currentAnimal);
        }
    }

    private void animalDifferentBehaviour(Animal currentAnimal) {
        // 80% chance of taking next genome / 20% chance of drawing random genome
        int decide = new Random().nextInt(10);
        if (decide < 2) {
            currentAnimal.setCurrentGenomeIndex(new Random().nextInt(this.engine.map.config.genotypeSize));
        } else {
            currentAnimal.setCurrentGenomeIndex((currentAnimal.getCurrentGenomeIndex() + 1) % this.engine.map.config.genotypeSize);
        }
    }

    public void eatPlants(){
        Iterator<Plant> iterator = this.engine.map.plantElementsMap.values().iterator();
        while (iterator.hasNext()) {
            Plant plant = iterator.next();
            Vector2d plantPosition  = plant.getPosition();
            if (this.engine.map.isOccupied(plantPosition)) {
                ArrayList<Animal> winners = this.engine.map.chooseTwoWinners(plantPosition);
                winners.get(0).eat();
                iterator.remove();
            }
        }
    }
    public void breedAnimals(){
        System.out.println();
        System.out.println("BREED BEGINS");

        this.engine.map.moreThanTwoAnimals.forEach((pos) -> {
            ArrayList<Animal> winners = this.engine.map.chooseTwoWinners(pos);
            Animal parent1 = winners.get(0);
            Animal parent2 = winners.get(1);
            System.out.println("parent 1 id: " + parent1 + " parent 2 id: " + parent2);
            parent1.breed(parent2);
            if (parent1.getEnergy() >= this.engine.map.config.readyForBreedEnergy && parent2.getEnergy() >= this.engine.map.config.readyForBreedEnergy){
                Animal newBornAnimal = new Animal(this.engine.map, pos);
                if (this.engine.map.place(newBornAnimal))
                    this.engine.animals.add(newBornAnimal);
                System.out.println("New animals has been born");
            } else {
                System.out.println("Animals hasn't enough energy to breed");
            }
        });
        System.out.println();
    }
    public void growNewPlants(int amount){
        int successfulSpawnedPlants = 0;
        int jungleSpawn = 0;
        for (int i = 0; i < amount; i++) {
            int preferredPlaceChance = new Random().nextInt(10);
            if(this.engine.map.spawnPlantRandomly(preferredPlaceChance >= 2)) {
                if(preferredPlaceChance >= 2) {jungleSpawn++;}
                successfulSpawnedPlants++;
            }
        }
        System.out.println("successful spawns: " + successfulSpawnedPlants + " / " + amount);
        System.out.println("jungle spawns: " + jungleSpawn + " / " + successfulSpawnedPlants);
    }
    public void initializeAnimalsRandomly(int amount){
        for (int i = 0; i < amount; i++) {
            // random position / random genotype
            Vector2d randomPosition = new Vector2d(-1, -1);
            while (this.engine.map.isOccupied(randomPosition) || !this.engine.map.canMoveTo(randomPosition)) {
                randomPosition.randomizeOnMap(this.engine.map);
            }
            ArrayList<Integer> randomGenotype = new Inheritance(this.engine.map.config).getRandomGenotype(this.engine.map.config.genotypeSize);
            Animal animalToAdd = new Animal(this.engine.map, randomPosition, randomGenotype);
            if (this.engine.map.place(animalToAdd))
                this.engine.animals.add(animalToAdd);
        }
    }
}
