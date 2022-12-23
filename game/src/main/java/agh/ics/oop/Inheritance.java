package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inheritance {
    public void inheritGenotype(Animal animal1, Animal animal2) {
        int animalEnergy1 = animal1.getEnergy();
        int animalEnergy2 = animal2.getEnergy();
        List<Integer> animalGenotype1 = animal1.getGenotype();
        List<Integer> animalGenotype2 = animal2.getGenotype();
        List<Integer> newGenotype;

        // compute genome distribution index by animal's energy ratio (operate with higher percentage)
        float energyRatio = (float) animalEnergy1/(animalEnergy1 + animalEnergy2);
        if (energyRatio < 0.5) {
            energyRatio = 1 - energyRatio;
        }
        System.out.println(energyRatio);
        int inheritanceIndex = (int) (energyRatio * CONSTANTS.DEFAULT_GENOTYPE_SIZE);
        System.out.println(inheritanceIndex);

        // create new genotype with drawn side
        newGenotype = createGenotype(animalGenotype1, animalGenotype2, inheritanceIndex, animalEnergy1 < animalEnergy2);
        System.out.println("new genotype before mutation: " + newGenotype);

        // mutate genotype
        mutate(newGenotype);
        System.out.println("new genotype after mutation:  " + newGenotype);

    }

    private List<Integer> createGenotype(List<Integer> genotype1, List<Integer> genotype2, int index, boolean flag) {
        List<Integer> genotypeToCreate = new ArrayList<>();
        // draw strong individual side of genotype flag = 0 - left side / 1 - right side
        int side = (int) Math.round(Math.random());
        System.out.println(side);

        if (flag) {
            List<Integer> temp = genotype1;
            genotype1 = genotype2;
            genotype2 = temp;
        }
        if (side == 1) {
            List<Integer> temp = genotype1;
            genotype1 = genotype2;
            genotype2 = temp;
            index = CONSTANTS.DEFAULT_GENOTYPE_SIZE - index;
        }
        for (int i = 0; i < CONSTANTS.DEFAULT_GENOTYPE_SIZE; i++) {
            if (i < index) {
                genotypeToCreate.add(genotype1.get(i));
            } else {
                genotypeToCreate.add(genotype2.get(i));
            }
        }
        return genotypeToCreate;
    }

    private void mutate(List<Integer> genotype) {
        // random number of mutated genomes
        int numberOfMutatedGenomes = (int) Math.round(Math.random() * (CONSTANTS.DEFAULT_GENOTYPE_SIZE - 1));
        System.out.println("number of mutated genomes: " + numberOfMutatedGenomes);

        // random chosen mutated genomes
        List<Integer> randomDrawnGenomes = new ArrayList<>();
        for (int i = 1; i < CONSTANTS.DEFAULT_GENOTYPE_SIZE; i++) randomDrawnGenomes.add(i);
        Collections.shuffle(randomDrawnGenomes);
        System.out.println("randomized genomes: " + randomDrawnGenomes);

        // mutating to random genome
        for (int i = 0; i < numberOfMutatedGenomes; i++) {
            int currentGenomeIndex = randomDrawnGenomes.get(i);
            int randomGenome = (int) Math.round(Math.random() * (CONSTANTS.DEFAULT_GENOTYPE_SIZE - 1));
            // try to draw a value different from before
            while (randomGenome == genotype.get(currentGenomeIndex)) {
                randomGenome = (int) Math.round(Math.random() * (CONSTANTS.DEFAULT_GENOTYPE_SIZE - 1));
            }
            System.out.println("change from " + genotype.get(currentGenomeIndex) + " to " + randomGenome + " at index: " + currentGenomeIndex);
            genotype.set(currentGenomeIndex, randomGenome);
        }
    }
}
