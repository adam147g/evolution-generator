package agh.ics.oop;

abstract public class AbstractMapElement implements IMapElement{
    protected Vector2d position;

    public AbstractMapElement(Vector2d pos){
        this.position = pos;
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    public Vector2d getPosition(){
        return this.position;
    }
}
