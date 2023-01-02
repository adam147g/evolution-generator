package agh.ics.oop;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
//        // decide by amount of energy
//        int energyComparison = Integer.compare(o1.energy, o2.energy);
//        if (energyComparison == 0) {
//            return energyComparison;
//        }
//        // decide by age
//        int ageComparison = Integer.compare(o1.age, o2.age);
//        if (ageComparison == 0) {
//            return ageComparison;
//        }
//        // decide by number of descendants
//        return Integer.compare(o1.descendants, o2.descendants);
        if (o1.energy != o2.energy) {
            return o2.energy - o1.energy;
        }
        if (o1.age != o2.age) {
            return o2.age - o1.age;
        }
        return o2.descendantsNumber - o1.descendantsNumber;
    }
}
