package agh.ics.oop;

public class RectangularMap extends AbstractMap  {
    int maxRangeX;
    int maxRangeY;
    public RectangularMap(int width, int height) {
        super(0, 0, width - 1, height - 1);
        this.maxRangeX = width;
        this.maxRangeY = height;
    }

    public boolean spawnPlantRandomly() {
        int randomX = (int) (Math.random() * maxRangeX);
        int randomY = (int) (Math.random() * maxRangeY);
        Vector2d randomPosition = new Vector2d(randomX, randomY);
        return spawnPlantAt(randomPosition);
    }
    public boolean spawnPlantAt(Vector2d position) {
        if (!isOccupied(position)) {
            Plant plantToAdd = new Plant(position);
            mapElements.put(plantToAdd.getPosition(), plantToAdd);
            return true;
        }
        return false;
    }

}
