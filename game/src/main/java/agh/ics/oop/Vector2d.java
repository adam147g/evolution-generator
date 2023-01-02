package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void randomizeOnMap(AbstractMap map){
        int randomXOfMap = this.x;
        int randomYOfMap = this.y;
        while (this.x == randomXOfMap) {
            randomXOfMap = map.leftBottomCorner.x + (int)(Math.random() * ((map.rightTopCorner.x - map.leftBottomCorner.x) + 1));
        }
        while (this.y == randomYOfMap) {
            randomYOfMap = map.leftBottomCorner.y + (int)(Math.random() * ((map.rightTopCorner.y - map.leftBottomCorner.y) + 1));
        }
        this.x = randomXOfMap;
        this.y = randomYOfMap;
    }
    public String toString(){
        return "(" + String.valueOf(this.x) + "," + String.valueOf(this.y) + ")";
    }
    public boolean precedes(Vector2d other){
        return (this.x <= other.x && this.y <= other.y);
    }
    public boolean follows(Vector2d other){ return (this.x >= other.x && this.y >= other.y); }
    public boolean includesIn(Vector2d leftBottomCorner, Vector2d rightTopCorner){ return (this.precedes(rightTopCorner) && this.follows(leftBottomCorner)); }
    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (this.x == that.x && this.y == that.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
