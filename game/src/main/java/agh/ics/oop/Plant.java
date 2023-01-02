package agh.ics.oop;

public class Plant extends AbstractMapElement {
    public Plant(Vector2d pos, AbstractMap map) {
        super(pos, map);
    }

    public String toString() {
//        return "*";
        return this.position.toString();
    }
    @Override
    public String getImageName() {
        return "plant.png";
    }
}
