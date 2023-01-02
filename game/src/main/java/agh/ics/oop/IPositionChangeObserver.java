package agh.ics.oop;

public interface IPositionChangeObserver {
    void elementPositionChanged(Animal elementToChange, Vector2d oldPosition, Vector2d newPosition);
    void removeElement(AbstractMapElement elementToRemove);
}
