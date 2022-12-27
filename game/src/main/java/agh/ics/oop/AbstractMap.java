package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractMap implements IMap, IPositionChangeObserver {
    protected Map<Vector2d, AbstractMapElement> mapElements = new HashMap<>();
    protected Vector2d leftBottomCorner;
    protected Vector2d rightTopCorner;
    public Vector2d getLeftBottomCorner() {
        return leftBottomCorner;
    }
    public Vector2d getRightTopCorner() {
        return rightTopCorner;
    }
    public AbstractMap(int xLB, int yLB, int xRT, int yRT) {
        this.leftBottomCorner = new Vector2d(xLB, yLB);
        this.rightTopCorner = new Vector2d(xRT, yRT);
    }
    public Map<Vector2d, AbstractMapElement> getMapElements() {
        return mapElements;
    }
    public String toString() {
        return new MapVisualizer(this).draw(getLeftBottomCorner(), getRightTopCorner());
    }
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(rightTopCorner) && position.follows(leftBottomCorner);
    }
    public boolean place(AbstractMapElement elementToAdd) {
        if (canMoveTo(elementToAdd.getPosition())) {
            mapElements.put(elementToAdd.getPosition(), elementToAdd);
            return true;
        }
        throw new IllegalArgumentException("Animal can't be palced at " + elementToAdd.getPosition() + " position");
    }
    public boolean isOccupied(Vector2d position) {
        return mapElements.containsKey(position);
    }
    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        AbstractMapElement elementToChange = this.mapElements.get(oldPosition);
        this.mapElements.remove(oldPosition);
        this.mapElements.put(newPosition, elementToChange);
    }
    @Override
    public void removeElement(Vector2d position) {
        this.mapElements.remove(position);
    }

}
