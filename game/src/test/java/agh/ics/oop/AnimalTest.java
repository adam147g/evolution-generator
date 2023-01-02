package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalTest {
//    @Test
//    void inheritGenotypeTest() {
//        AbstractMap map = new RectangularMap(3,3);
//        String[] genotype1 = {"0", "1", "2", "3", "4", "5", "6", "7"};
//        String[] genotype2 = {"7","6","5","4","3","2","1","0"};
//        Animal animal1 = new Animal(map, new Vector2d(1,1), new OptionsParser().parse1D(genotype1));
//        Animal animal2 = new Animal(map, new Vector2d(2,2), new OptionsParser().parse1D(genotype2));
//        // ratio 1/3 = (200-100)/( (200-100) + (200-0) )
//        animal1.setEnergy(200);
//        animal2.setEnergy(200);
//        animal1.updateEnergy(-100);
//        animal2.updateEnergy(0);
//        Inheritance inheritance = new Inheritance();
//        inheritance.inheritGenotype(animal1, animal2);
//
//    }
//
//    @Test
//    void eatingSoloTest() {
//        RectangularMap map = new RectangularMap(5,5) {
//        };
//        Vector2d[] positions = {new Vector2d(0,0)};
//        SimulationEngine engine = new SimulationEngine(map, positions);
//        SimulationActions actions = new SimulationActions(engine);
//        System.out.println(map.animalElementsMap.values());
//        map.spawnPlantAt(new Vector2d(0,0));
//        System.out.println("before eating");
//        System.out.println(engine.animals.get(0).getEnergy());
//        actions.eatPlants();
//        System.out.println("after eating");
//        System.out.println(engine.animals.get(0).getEnergy());
//
//    }
//
//    @Test
//    void eatingConflictAndPickingWinnerTest() {
//        RectangularMap map = new RectangularMap(5,5) {
//        };
//        Vector2d[] positions = {new Vector2d(0,0), new Vector2d(0,0), new Vector2d(0,0), new Vector2d(0,0)};
//        SimulationEngine engine = new SimulationEngine(map, positions);
//        SimulationActions actions = new SimulationActions(engine);
//        engine.animals.get(0).setEnergy(200);
//        engine.animals.get(1).setEnergy(100);
//        engine.animals.get(2).setEnergy(300);
//        engine.animals.get(3).setEnergy(100);
//        map.spawnPlantAt(new Vector2d(0,0));
//        System.out.println("before eating");
//        System.out.println(engine.map.plantElementsMap.values());
//        System.out.println("animal1 energy: " + engine.animals.get(0).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(1).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(2).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(3).getEnergy());
//        actions.eatPlants();
//        System.out.println("after eating");
//        System.out.println(engine.map.plantElementsMap.values());
//        System.out.println("animal1 energy: " + engine.animals.get(0).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(1).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(2).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(3).getEnergy());
//
//    }
//
//    @Test
//    void breedTest() {
//        RectangularMap map = new RectangularMap(5,5) {
//        };
//        Vector2d[] positions = {new Vector2d(0,0), new Vector2d(0,0), new Vector2d(0,0), new Vector2d(0,0)};
//        String[][] testArgs = {
//                {"0","1","2","3","4","5","6","7"},
//                {"0","0","0","0","0","0","0","0"},
//                {"0","0","0","0","0","0","0","0"},
//                {"7","6","5","4","3","2","1","0"},
//        };
//        ArrayList<ArrayList<Integer>> genotypes = new OptionsParser().parse2D(testArgs);
//        SimulationEngine engine = new SimulationEngine(map, positions, genotypes, 20);
//        SimulationActions actions = new SimulationActions(engine);
//        engine.animals.get(0).setEnergy(200);
//        engine.animals.get(1).setEnergy(100);
//        engine.animals.get(2).setEnergy(100);
//        engine.animals.get(3).setEnergy(300);
//        System.out.println();
//        System.out.println("meeting");
//        System.out.println(engine.map.animalElementsMap.values());
//        System.out.println("animal1 energy: " + engine.animals.get(0).getEnergy());
//        System.out.println("animal2 energy: " + engine.animals.get(1).getEnergy());
//        System.out.println("animal3 energy: " + engine.animals.get(2).getEnergy());
//        System.out.println("animal4 energy: " + engine.animals.get(3).getEnergy());
//        System.out.println();
//        System.out.println("winners");
//        actions.breedAnimals();
//        System.out.println("animal1 descendants: " + engine.animals.get(0).getDescendantsNumber());
//        System.out.println("animal2 descendants: " + engine.animals.get(1).getDescendantsNumber());
//        System.out.println("animal3 descendants: " + engine.animals.get(2).getDescendantsNumber());
//        System.out.println("animal4 descendants: " + engine.animals.get(3).getDescendantsNumber());
//        System.out.println(engine.map.animalElementsMap.values());
//    }


}
