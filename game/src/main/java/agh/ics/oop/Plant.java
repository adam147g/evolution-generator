package agh.ics.oop;

public class Plant extends AbstractMapElement {
    public Plant(Vector2d pos) {
        super(pos);
    }

    public String toString() {
        return "*";
    }
    @Override
    public String getImageName() {
        return "plant.png";
    }
}
