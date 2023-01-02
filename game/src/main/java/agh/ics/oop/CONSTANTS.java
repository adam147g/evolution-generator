package agh.ics.oop;

import java.util.Map;

import static java.util.Map.entry;

// Change to config when project finished
public class CONSTANTS {
    static int DEFAULT_MAX_ENERGY = 200;
    static int DEFAULT_START_ENERGY = 180;
    static int DEFAULT_GENOTYPE_SIZE = 8;
    static int MAP_TYPE = 0;
    static int MUTATION_VARIANT = 0;
    static int ENERGY_FROM_PLANT = 50;
    static int energyLossPerMove = -30;
    static int animalBehaviour = 0;
    static int energyLossForBreed = -40;
    static int readyForBreedEnergy = 60;


    // mapped genome with its corresponding map direction
    static Map<Integer,MapDirection> DIRECTIONS = Map.ofEntries(
            entry(0, MapDirection.NORTH),
            entry(1, MapDirection.NORTH_EAST),
            entry(2, MapDirection.EAST),
            entry(3, MapDirection.SOUTH_EAST),
            entry(4, MapDirection.SOUTH),
            entry(5, MapDirection.SOUTH_WEST),
            entry(6, MapDirection.WEST),
            entry(7, MapDirection.NORTH_WEST)
    );
}
