package agh.ics.oop;

public interface IMapElement {
    boolean isAt(Vector2d position);
    String toString();

    Vector2d getPosition();

    String getImageName();
}
