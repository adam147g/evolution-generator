package agh.ics.oop;

import java.util.Map;

import static java.util.Map.entry;

// Change to config when project finished
public class CONSTANTS {
    static int DEFAULT_GENOTYPE_SIZE = 8;
    static int ENERGY_FROM_PLANT = 50;
    static int ENERGY_LOSS_PER_MOVE = -10;
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
