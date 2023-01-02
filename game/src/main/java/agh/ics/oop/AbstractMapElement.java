package agh.ics.oop;

abstract public class AbstractMapElement implements IMapElement{
    protected AbstractMap map;
    protected Vector2d position;
    protected int id;

    public AbstractMapElement(Vector2d pos, AbstractMap map){
        this.position = pos;
        this.map = map;
//        this.id = this.map.globalIndexElements + 1;
//        this.map.increaseGlobalIndexElements();
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setId(int id) {
        this.id = id;
    }
}
