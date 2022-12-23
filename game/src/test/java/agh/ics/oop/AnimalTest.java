package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalTest {
    @Test
    void inheritGenotypeTest() {
        AbstractMap map = new RectangularMap(3,3) {
        };
        String[] genotype1 = {"0", "1", "2", "3", "4", "5", "6", "7"};
        String[] genotype2 = {"7","6","5","4","3","2","1","0"};
        Animal animal1 = new Animal(map, new Vector2d(1,1), new OptionsParser().parse1D(genotype1));
        Animal animal2 = new Animal(map, new Vector2d(2,2), new OptionsParser().parse1D(genotype2));
        // ratio 1/3 = (200-100)/( (200-100) + (200-0) )
        animal1.updateEnergy(-100);
        animal2.updateEnergy(0);
        Inheritance inheritance = new Inheritance();
        inheritance.inheritGenotype(animal1, animal2);

    }

    @Test
    void eatingTest() {
        RectangularMap map = new RectangularMap(5,5) {
        };
        String[] genotype1 = {"0", "1", "2", "3", "4", "5", "6", "7"};
        Animal animal1 = new Animal(map, new Vector2d(0,0), new OptionsParser().parse1D(genotype1));
        map.spawnPlantAt(new Vector2d(1,1));
        System.out.println(animal1.getEnergy());
        animal1.move(1);
        System.out.println(animal1.getEnergy());

    }
}
