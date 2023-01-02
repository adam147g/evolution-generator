package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AnimalIntegrationTest {
//    @Test
//    void animalsOnMapTest(){
//        String[][] testArgs = {
//                {"0","0","0","0","1","2","1","2"},
//                {"4","0","0","6","0","0","6","7"}};
//        ArrayList<ArrayList<Integer>> directions = new OptionsParser().parse2D(testArgs);
//        RectangularMap map = new RectangularMap(10, 10);
//        Vector2d[] positions = {new Vector2d(2,2), new Vector2d(1,4)};
//        SimulationEngine engine = new SimulationEngine(map, positions, directions, 0);
////        engine.runParallel(1);
//
//        Vector2d[] expected = new Vector2d[]{
//                new Vector2d(3, 5),
//                new Vector2d(3, 3)
//        };
//        Assertions.assertEquals(expected[0], engine.animals.get(0).getPosition());
//        Assertions.assertEquals(expected[1], engine.animals.get(1).getPosition());
//    }
//
//    @Test
//    void animalOffTheMapTest() {
//        RectangularMap map = new RectangularMap(5,5) {
//        };
//        System.out.println("\nAnimal 1 (left-border):");
//        Animal animal1 = new Animal(map, new Vector2d(0,1));
//        System.out.println(animal1.position);
//        animal1.move(6);
//        System.out.println(animal1.position);
//
//        System.out.println("\nAnimal 2 (right-border):");
//        Animal animal2 = new Animal(map, new Vector2d(4,3));
//        System.out.println(animal2.position);
//        System.out.println(animal2.getEnergy());
//        animal2.move(2);
//        System.out.println(animal2.position);
//        System.out.println(animal2.getEnergy());
//
//        System.out.println("\nAnimal 3 (top-border):");
//        Animal animal3 = new Animal(map, new Vector2d(3,4));
//        System.out.println(animal3.position);
//        System.out.println(animal3.getEnergy());
//        animal3.move(0);
//        System.out.println(animal3.position);
//        System.out.println(animal3.getEnergy());
//    }

    // MAIN TEST
//    @Test
//    void animalsWithPlants(){
//        String[][] testArgs = {
//                {"0","0","0","0","1","2","1","2"},
//                {"4","0","0","6","0","0","6","7"}};
//        ArrayList<ArrayList<Integer>> directions = new OptionsParser().parse2D(testArgs);
//        RectangularMap map = null;
//        if (CONSTANTS.MAP_TYPE == 0) {
//            map = new RectangularMap(10, 10, 0.2);
//        } else if (CONSTANTS.MAP_TYPE == 1) {
//            map = new RectangularMap(10, 10);
//        }
//        Vector2d[] positions = {new Vector2d(2,2), new Vector2d(1,4)};
//        SimulationEngine engine = new SimulationEngine(map, positions, directions,  20);
//        System.out.println(map);
//        engine.stats();
//        engine.runParallelPlants(50);
//        System.out.println(map);
//        engine.stats();
//
//    }
}
