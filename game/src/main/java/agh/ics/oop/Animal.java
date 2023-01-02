package agh.ics.oop;

import java.util.ArrayList;
import java.util.Random;


public class Animal extends AbstractMapElement{
    // random direction after generating
    private MapDirection direction = MapDirection.randomDirection();
    public int energy = CONSTANTS.DEFAULT_START_ENERGY;
    public int age = 0;
    public int descendantsNumber = 0;
    private int plantsEaten = 0;
    private int currentGenomeIndex = randomGenomeIndex();
    private boolean dead = false;
    private ArrayList<Integer> genotype;
    public Animal (AbstractMap map, Vector2d initialPosition, ArrayList<Integer> initialGenotype){
        super(initialPosition, map);
//        this.energy = this.map.config.initialEnergy;
        this.genotype = initialGenotype;
    }
    public Animal (AbstractMap map, Vector2d initialPosition){
        super(initialPosition, map);
//        this.energy = this.map.config.initialEnergy;
    }
    // BREEDING CONSTRUCTOR
    public Animal (AbstractMap map, Vector2d initialPosition, Animal parent1, Animal parent2){
        super(initialPosition, map);

        Inheritance inheritance = new Inheritance();
        this.genotype = inheritance.inheritGenotype(parent1, parent2);
        this.energy = this.map.config.energyLossForBreed * 2 * (-1);
    }
    public int randomGenomeIndex() {
        return new Random().nextInt(CONSTANTS.DEFAULT_GENOTYPE_SIZE);
    }
    public String toString() {
//        return switch (this.direction) {
////            case NORTH -> "N ";
////            case NORTH_EAST -> "NE";
////            case EAST -> "E ";
////            case SOUTH_EAST -> "SE";
////            case SOUTH -> "S ";
////            case SOUTH_WEST -> "SW";
////            case WEST -> "W ";
////            case NORTH_WEST -> "NW";
//            case NORTH, NORTH_EAST, NORTH_WEST -> "N";
//            case EAST -> "E ";
//            case SOUTH_EAST, SOUTH_WEST, SOUTH -> "S";
//            case WEST -> "W";
//        };
        return Integer.toString(this.id);
    }

    public MapDirection getDirection() {
        return direction;
    }

    public ArrayList<Integer> getGenotype() {
        return genotype;
    }
    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getDescendantsNumber() {
        return descendantsNumber;
    }

    public int getCurrentGenomeIndex() {
        return currentGenomeIndex;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescendantsNumber(int descendantsNumber) {
        this.descendantsNumber = descendantsNumber;
    }

    public void setCurrentGenomeIndex(int currentGenomeIndex) {
        this.currentGenomeIndex = currentGenomeIndex;
    }

    public boolean isDead() {
        return dead;
    }
    public boolean isAlive() {
        return !dead;
    }

    public void turnDead() {
        this.dead = true;
    }

    public void breed(Animal partner) {
        this.descendantsNumber++;
        this.updateEnergy(this.map.config.energyLossForBreed);
        partner.descendantsNumber++;
        partner.updateEnergy(this.map.config.energyLossForBreed);
    }
    public void eat() {
        this.plantsEaten++;
        updateEnergy(this.map.config.eatingPlantEnergy);
    }
    public void updateEnergy(int value) {
        this.energy += value;
    }

    public void move(int turnIndex) {
        // compute direction based on Directions array and turnIndex
        this.direction = CONSTANTS.DIRECTIONS.get((this.direction.toIndex() + turnIndex) % CONSTANTS.DIRECTIONS.size());

        Vector2d oldPosition = this.position;
        Vector2d updatePosition = oldPosition.add(this.direction.toUnitVector());
        boolean canPerformMove = true;
        // IF POSITION IS OFF THE MAP THERE ARE 2 VARIANTS
        if (!map.canMoveTo(updatePosition)) {
            if (this.map.config.mapVariant == 0) {
                // DON'T MOVE OR MOVE ANIMAL TO OTHER SIDE OF MAP
                if ((updatePosition.y > this.map.rightTopCorner.y) || (updatePosition.y < this.map.leftBottomCorner.y)) {
                    canPerformMove = false;
                } else {
                    if (updatePosition.x > this.map.rightTopCorner.x) {
                        updatePosition.setX(this.map.leftBottomCorner.x);
                    } else {
                        updatePosition.setX(this.map.rightTopCorner.x);
                    }
                }
            } else if (this.map.config.mapVariant == 1) {
                // MOVE ANIMAL TO RANDOM POSITION AND DECREASE ITS ENERGY
                updateEnergy(this.map.config.energyLossForBreed);
                updatePosition.randomizeOnMap(this.map);
            }
        }
        if(canPerformMove) {
            updateEnergy(this.map.config.energyLossPerMove);
            this.map.elementPositionChanged(this, oldPosition, updatePosition);
            this.position = updatePosition;
            this.currentGenomeIndex++;
        }
        this.age++;
    }

    @Override
    public String getImageName() {
//        return switch (this.direction) {
//            case NORTH -> "north.png";
//            case NORTH_EAST -> "north_east.png";
//            case EAST -> "east.png";
//            case SOUTH_EAST -> "south_east.png";
//            case SOUTH -> "south.png";
//            case SOUTH_WEST -> "south_west.png";
//            case WEST -> "west.png";
//            case NORTH_WEST -> "north_west.png";
//        };
        float life = (float) this.energy/CONSTANTS.DEFAULT_MAX_ENERGY;
//        System.out.println("hp status: max: " + CONSTANTS.DEFAULT_MAX_ENERGY + " curr: " + this.energy + " percent: " + life);
        if (life <= 0) {
            return "animal0.png";
        } else if (life < 0.25) {
            return "animal25.png";
        } else if (life < 0.5) {
            return "animal50.png";
        } else if (life < 0.75) {
            return "animal75.png";
        } else {
            return "animal100.png";
        }
    }
}
