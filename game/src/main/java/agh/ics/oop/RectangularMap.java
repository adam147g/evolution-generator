package agh.ics.oop;

public class RectangularMap extends AbstractMap  {
    int maxRangeX;
    int maxRangeY;
    public RectangularMap(int width, int height) {
        super(0, 0, width - 1, height - 1);
        this.maxRangeX = width;
        this.maxRangeY = height;
    }

    public void spawnPlantRandomly() {
        Vector2d randomPosition = new Vector2d(-1, -1);
        while (isOccupied(randomPosition) || !this.canMoveTo(randomPosition)) {
            int randomX = (int) (Math.random() * maxRangeX);
            int randomY = (int) (Math.random() * maxRangeY);
            randomPosition = new Vector2d(randomX, randomY);
        }
        spawnPlantAt(randomPosition);
    }
    public void spawnPlantAt(Vector2d position) {
        if (!isOccupied(position)) {
            Plant plantToAdd = new Plant(position);
            this.place(plantToAdd);
        }
    }

}
