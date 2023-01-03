package agh.ics.oop;

public class CONFIG {

    public int mapWidth = 10;
    //
    public int mapHeight = 10;
    /*
    * mapVariant = 0 - the globe - the left and right edges of the map loop (if the animal goes over the left edge, it will appear on the right - and if over the right, then on the left); the upper and lower edges of the map are poles - you can't go there (if a pet tries to go beyond these edges of the map, it stays on the field it was on and its direction changes to the opposite);
    * mapVariant = 1 - infernal portal - if the pet goes beyond the edge of the map, it goes to a magical portal; its energy decreases by a certain amount (same as in the case of descendant generation), and then it is teleported to a new, random location on the map.*/
    //
    public int mapVariant = 0;
    //
    public int initialPlantsNumber = 5;
    //
    public int eatingPlantEnergy = 50;
    //
    public int energyLossPerMove = -30;
    //
    public int newPlantsPerDay = 5;
    //
    /**
     * In the case of plant growth, certain fields are strongly favored, according to the Pareto principle. There is an 80% chance that a new plant will grow on a preferred tile, and only a 20% chance that it will grow on a secondary tile. About 20% of all places on the map are preferred, 80% of places are considered unattractive. We implement the following variants:
     * plantGrowth = 0 - forested equator - a horizontal strip of fields in the central part of the map is preferred by plants (imitating the equator and its surroundings);
     * plantGrowth = 1 - toxic corpses - plants prefer those fields where animals die the least - they grow on those fields where the fewest animals died during the simulation.
     */
    public int plantGrowthType = 0;
    //
    public int initialAnimalNumber = 2;
    //
    public int initialEnergy = 100;
    //
    public int readyForBreedEnergy = 100;
    //
    public int energyLossForBreed = -40;
    //
    public int minMutationNumber = 0;
    //
    public int maxMutationNumber = 0;
    //
    /**
     * mutationVariant = 0 - full randomness - mutation changes genome to random one;
     * mutationVariant = 1 - slight correction - mutation changes genome one up or one down (e.g. genome 3 can be changed into 2 or 4 and genome 0 - into 1 or 7).
     */
    public int mutationVariant = 0;
    //
    public int genotypeSize = 8;
    /**
     * animalBehaviour = 0 - full predestination - animal perform genome sequentially, one after the other;
     * animalBehaviour = 1 - a bit of madness - there is 80% chance animal after performing genome performs next one, other 20% chance - animal perform random genome
     */
    public int animalBehaviour = 0;
    //

    // DEFAULT CONFIG
    public CONFIG(){}

    public CONFIG(
            int mapWidth,
            int mapHeight,
            int mapVariant,
            int initialPlantsNumber,
            int eatingPlantEnergy,
            int energyLossPerMove,
            int newPlantsPerDay,
            int plantGrowthType,
            int initialAnimalNumber,
            int initialEnergy,
            int readyForBreedEnergy,
            int energyLossForBreed,
            int minMutationNumber,
            int maxMutationNumber,
            int mutationVariant,
            int genotypeSize,
            int animalBehaviour
    ){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapVariant = mapVariant;
        this.initialPlantsNumber = initialPlantsNumber;
        this.eatingPlantEnergy = eatingPlantEnergy;
        // positive value from input to negative value field
        this.energyLossPerMove = -energyLossPerMove;
        this.newPlantsPerDay = newPlantsPerDay;
        this.plantGrowthType = plantGrowthType;
        this.initialAnimalNumber = initialAnimalNumber;
        this.initialEnergy = initialEnergy;
        this.readyForBreedEnergy = readyForBreedEnergy;
        // positive value from input to negative value field
        this.energyLossForBreed = -energyLossForBreed;
        this.minMutationNumber = minMutationNumber;
        this.maxMutationNumber = maxMutationNumber;
        this.mutationVariant = mutationVariant;
        this.genotypeSize = genotypeSize;
        this.animalBehaviour = animalBehaviour;
    }

    public void printConfig() {
        System.out.println("mapWidth: " + mapWidth);
        System.out.println("mapHeight: " + mapHeight);
        System.out.println("mapVariant: " + mapVariant);
        System.out.println("initialPlantsNumber: " + initialPlantsNumber);
        System.out.println("eatingPlantEnergy: " + eatingPlantEnergy);
        System.out.println("energyLossPerMove: " + energyLossPerMove);
        System.out.println("newPlantsPerDay: " + newPlantsPerDay);
        System.out.println("plantGrowthType: " + plantGrowthType);
        System.out.println("initialAnimalNumber: " + initialAnimalNumber);
        System.out.println("initialEnergy: " + initialEnergy);
        System.out.println("readyForBreedEnergy: " + readyForBreedEnergy);
        System.out.println("energyLossForBreed: " + energyLossForBreed);
        System.out.println("minMutationNumber: " + minMutationNumber);
        System.out.println("maxMutationNumber: " + maxMutationNumber);
        System.out.println("mutationVariant: " + mutationVariant);
        System.out.println("genotypeSize: " + genotypeSize);
        System.out.println("animalBehaviour: " + animalBehaviour);
    }
}
