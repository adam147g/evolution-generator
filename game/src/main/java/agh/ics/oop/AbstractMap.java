package agh.ics.oop;

import java.util.*;

abstract public class AbstractMap implements IMap, IPositionChangeObserver {
    public CONFIG config;
    protected int globalIndexElements = -1;
//    protected Map<Vector2d, List<AbstractMapElement>> mapElements = new HashMap<>();
    protected Map<Vector2d, Map<Integer, Animal>> animalElementsMap = new HashMap<>();
    protected Map<Vector2d, Plant> plantElementsMap = new HashMap<>();
    // auxiliary set for positions which keeps more than one animal element
    protected Set<Vector2d> moreThanTwoAnimals = new HashSet<>();
    protected Vector2d leftBottomCorner;
    protected Vector2d rightTopCorner;

    public AbstractMap(int xLB, int yLB, int xRT, int yRT) {
        this.leftBottomCorner = new Vector2d(xLB, yLB);
        this.rightTopCorner = new Vector2d(xRT, yRT);
    }
    public void setConfig(CONFIG config) {
        this.config = config;
    }
    public Vector2d getLeftBottomCorner() {
        return leftBottomCorner;
    }
    public Vector2d getRightTopCorner() {
        return rightTopCorner;
    }
    public Map<Vector2d, Map<Integer, Animal>> getAnimalElementsMap() {
        return animalElementsMap;
    }

    public Map<Vector2d, Plant> getPlantElementsMap() {
        return plantElementsMap;
    }

    public String toString() {
        return new MapVisualizer(this).draw(getLeftBottomCorner(), getRightTopCorner());
    }
    public void increaseGlobalIndexElements() {
        this.globalIndexElements = this.globalIndexElements + 1;
    }
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(this.rightTopCorner) && position.follows(this.leftBottomCorner);
    }
    public boolean place(Animal elementToAdd) {
        // initialize id of map element
        elementToAdd.setId(this.globalIndexElements + 1);
        System.out.println("this animal has id: " + elementToAdd.id);
        increaseGlobalIndexElements();

        Vector2d currentPosition = elementToAdd.getPosition();
        // add element to mapElement
        if (canMoveTo(currentPosition)) {
            if (this.animalElementsMap.containsKey(currentPosition)) {
                // if position contain more than 2 elements add it to set
                this.moreThanTwoAnimals.add(currentPosition);

                this.animalElementsMap.get(currentPosition).put(elementToAdd.id, elementToAdd);
            } else {
                Map<Integer, Animal> currentElementsOnPosition = new HashMap<>();
                currentElementsOnPosition.put(elementToAdd.id, elementToAdd);
                this.animalElementsMap.put(elementToAdd.getPosition(), currentElementsOnPosition);
            }
            return true;
        }
        throw new IllegalArgumentException("Animal can't be palced at " + elementToAdd.getPosition() + " position");
    }
    public boolean isOccupied(Vector2d position) {
        return animalElementsMap.containsKey(position);
    }
    public Map<Integer, Animal> objectsAt(Vector2d position) {
        return animalElementsMap.get(position);
    }

    public ArrayList<Animal> chooseTwoWinners(Vector2d position) {
        ArrayList<Animal> candidates = new ArrayList<>();
        animalElementsMap.get(position).forEach((id, animal) -> {
            candidates.add(animal);
        });
        candidates.sort(new AnimalComparator());
        // return two the best candidates
        return candidates;
    }

    @Override
    public void elementPositionChanged(Animal elementToChange, Vector2d oldPosition, Vector2d newPosition) {
        // delete from old position
        removeElement(elementToChange);
//        this.animalElementsMap.get(oldPosition).remove(elementToChange.id);

        // add to new position
        if (!this.animalElementsMap.containsKey(newPosition)) {
            this.animalElementsMap.put(newPosition, new HashMap<>());
        }
        this.animalElementsMap.get(newPosition).put(elementToChange.id, elementToChange);

    }
    @Override
    public void removeElement(AbstractMapElement elementToRemove) {
        Vector2d positionOfElement = elementToRemove.getPosition();
        int idToDelete = elementToRemove.id;
        // remove element from its position on map
        this.animalElementsMap.get(positionOfElement).remove(idToDelete);
        // if this position contain less than 2 elements remove it from set
        if (this.animalElementsMap.get(elementToRemove.getPosition()).size() < 2) {
            this.moreThanTwoAnimals.remove(elementToRemove.getPosition());
        }
        // if value of position key on map is empty then remove it too
        if(this.animalElementsMap.get(elementToRemove.getPosition()).isEmpty()) {
            this.animalElementsMap.remove(elementToRemove.getPosition());
        }
    }

//    public void removePlant(Plant plantToRemove) {
//        this.plantElementsMap.remove(plantToRemove.position);
//    }
}
