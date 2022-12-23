package agh.ics.oop;

import java.util.List;

public class Animal extends AbstractMapElement{
    private AbstractMap map;
    private MapDirection direction = MapDirection.NORTH;
    // random direction after generating
//    private MapDirection direction = CONSTANTS.DIRECTIONS.get((int) Math.round(Math.random() * (CONSTANTS.DEFAULT_GENOTYPE_SIZE - 1)));
    private int energy = 200;
    private int dayOfLife = 1;
    private int descendants = 0;
    private int plantsEaten = 0;
    private int currentGenome = 0;
    private boolean alive = true;
    private List<Integer> genotype;
    public Animal (AbstractMap map, Vector2d initialPosition, List<Integer> initialGenotype){
        super(initialPosition);
        this.map = map;
        this.genotype = initialGenotype;
    }
    public Animal (AbstractMap map, Vector2d initialPosition){
        super(initialPosition);
        this.map = map;
    }
    public String toString() {
        return switch (this.direction) {
//            case NORTH -> "N ";
//            case NORTH_EAST -> "NE";
//            case EAST -> "E ";
//            case SOUTH_EAST -> "SE";
//            case SOUTH -> "S ";
//            case SOUTH_WEST -> "SW";
//            case WEST -> "W ";
//            case NORTH_WEST -> "NW";
            case NORTH, NORTH_EAST, NORTH_WEST -> "N";
            case EAST -> "E ";
            case SOUTH_EAST, SOUTH_WEST, SOUTH -> "S";
            case WEST -> "W";
        };
    }
    public List<Integer> getGenotype() {
        return genotype;
    }
    public int getEnergy() {
        return energy;
    }

    public boolean isAlive() {
        return alive;
    }

    public void breed(Animal animal1, Animal animal2) {
        this.descendants++;
        //
    }
    public void eat(Vector2d plantPosition) {
        this.plantsEaten++;
        updateEnergy(CONSTANTS.ENERGY_FROM_PLANT);
        this.map.removeElement(plantPosition);
    }
    public void updateEnergy(int value) {
        this.energy += value;
    }
    public void death() {
        this.alive = false;
        this.map.removeElement(this.position);

    }
    public void move(int turnIndex) {
        this.direction = CONSTANTS.DIRECTIONS.get((this.direction.toIndex() + turnIndex) % CONSTANTS.DIRECTIONS.size());
        Vector2d oldPosition = this.position;
        Vector2d updatePosition = oldPosition.add(this.direction.toUnitVector());
        if (map.canMoveTo(updatePosition)) {
            // BREED
            if (map.isOccupied(updatePosition) && map.objectAt(updatePosition) instanceof Animal) {
                breed(this, (Animal) map.objectAt(updatePosition));
            }
            // EAT PLANT
            else if (map.isOccupied(updatePosition) && map.objectAt(updatePosition) instanceof Plant) {
                eat(updatePosition);
            }
            // UPDATE POSITION
            updateEnergy(CONSTANTS.ENERGY_LOSS_PER_MOVE);
            this.position = updatePosition;
            this.dayOfLife++;
            this.map.positionChanged(oldPosition, updatePosition);
            // DEATH
            if (this.energy <= 0) {
                death();
            }
        }
    }

    @Override
    public String getImageName() {
        return switch (this.direction) {
            case NORTH -> "north.png";
            case NORTH_EAST -> "north_east.png";
            case EAST -> "east.png";
            case SOUTH_EAST -> "south_east.png";
            case SOUTH -> "south.png";
            case SOUTH_WEST -> "south_west.png";
            case WEST -> "west.png";
            case NORTH_WEST -> "north_west.png";
        };
    }
}
