package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Inheritance {
    CONFIG config;
    public Inheritance(CONFIG config) {
        this.config = config;
    }
    public ArrayList<Integer> inheritGenotype(Animal animal1, Animal animal2) {
        int animalEnergy1 = animal1.getEnergy();
        int animalEnergy2 = animal2.getEnergy();
        ArrayList<Integer> animalGenotype1 = animal1.getGenotype();
        ArrayList<Integer> animalGenotype2 = animal2.getGenotype();
        System.out.println("genotype1: " + animalGenotype1);
        System.out.println("genotype2: " + animalGenotype2);
        ArrayList<Integer> newGenotype;

        // compute genome distribution index by animal's energy ratio (operate with higher percentage)
        float energyRatio = (float) animalEnergy1/(animalEnergy1 + animalEnergy2);
        if (energyRatio < 0.5) {
            energyRatio = 1 - energyRatio;
        }
        System.out.println("energyRatio: " + energyRatio);
        int inheritanceIndex = (int) (energyRatio * this.config.genotypeSize);
        System.out.println("inheritanceIndex: " + inheritanceIndex);

        // create new genotype with drawn side
        newGenotype = createGenotype(animalGenotype1, animalGenotype2, inheritanceIndex, animalEnergy1 < animalEnergy2);

        System.out.println(newGenotype);

        // mutate genotype
        mutateVariant(newGenotype);
        System.out.println("new genotype after mutation:  " + newGenotype);

        return newGenotype;
    }

    private ArrayList<Integer> createGenotype(ArrayList<Integer> genotype1, ArrayList<Integer> genotype2, int index, boolean flag) {
        ArrayList<Integer> genotypeToCreate = new ArrayList<>();
        // draw strong individual side of genotype flag = 0 - left side / 1 - right side
        int side = (int) Math.round(Math.random());
        System.out.println("strong individual side: " + ((side == 1) ? "left side": "right side"));

        if (flag) {
            ArrayList<Integer> temp = genotype1;
            genotype1 = genotype2;
            genotype2 = temp;
        }
        if (side == 1) {
            ArrayList<Integer> temp = genotype1;
            genotype1 = genotype2;
            genotype2 = temp;
            index = this.config.genotypeSize - index;
        }
        for (int i = 0; i < this.config.genotypeSize; i++) {
            if (i < index) {
                genotypeToCreate.add(genotype1.get(i));
            } else {
                genotypeToCreate.add(genotype2.get(i));
            }
        }
        // PRINT NEW GENOTYPE WITH DIVISION INDEX
        System.out.print("new genotype before mutation: [ ");
        for (int  i = 0; i < genotypeToCreate.size(); i++) {
            System.out.print(genotypeToCreate.get(i) + ", ");
            if (i == index - 1) {
                System.out.print("| ");
            }
        }
        System.out.println("]");
        return genotypeToCreate;
    }

    private void mutateVariant(ArrayList<Integer> genotype) {
        // random number of mutated genomes
        int numberOfMutatedGenomes = Math.min((new Random().nextInt((this.config.maxMutationNumber - this.config.minMutationNumber) + 1) + this.config.minMutationNumber), this.config.genotypeSize);
        if (numberOfMutatedGenomes == 0) {
            return;
        }
        System.out.println("number of mutated genomes: " + numberOfMutatedGenomes);

        // random chosen mutated genomes
        ArrayList<Integer> randomDrawnGenomes = getRandomGenotype(this.config.genotypeSize);
        System.out.println("randomized genomes: " + randomDrawnGenomes);

        // mutating to random genome or change by one (up or down)
        for (int i = 0; i < numberOfMutatedGenomes; i++) {
            int currentGenomeIndex = randomDrawnGenomes.get(i);
            int newGenome = -1;
            if (this.config.mutationVariant == 0) {
                int randomGenome = (int) Math.round(Math.random() * (this.config.genotypeSize - 1));
                // try to draw a value different from before
                while (randomGenome == genotype.get(currentGenomeIndex)) {
                    randomGenome = (int) Math.round(Math.random() * (this.config.genotypeSize - 1));
                }
                newGenome = randomGenome;
            } else if (this.config.mutationVariant == 1) {
                // decide: 0 - go down / 1 - go up
                int decide = new Random().nextInt(2);
                if (decide == 0) {
                    newGenome = (this.config.genotypeSize + genotype.get(currentGenomeIndex) - 1) % this.config.genotypeSize;
                } else {
                    newGenome = (this.config.genotypeSize + genotype.get(currentGenomeIndex) - 1) % this.config.genotypeSize;
                }
            }
            System.out.println("change from " + genotype.get(currentGenomeIndex) + " to " + newGenome + " at index: " + currentGenomeIndex + " - VARIANT 0");
            genotype.set(currentGenomeIndex, newGenome);
        }
    }
    public ArrayList<Integer> getRandomGenotype(int length) {
        ArrayList<Integer> randomGenotype = new ArrayList<>();
        for (int i = 0; i < length; i++) randomGenotype.add(i);
        Collections.shuffle(randomGenotype);
        return randomGenotype;
    }
}
