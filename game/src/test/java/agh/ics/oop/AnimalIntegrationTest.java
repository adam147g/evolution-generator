package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnimalIntegrationTest {
    @Test
    void animalsOnMapTest(){
        String[][] testArgs = {
                {"0","0","0","0","1","2","1","2"},
                {"4","0","0","6","0","0","6","7"}};
        List<List<Integer>> directions = new OptionsParser().parse2D(testArgs);
        RectangularMap map = new RectangularMap(10, 10);
        Vector2d[] positions = {new Vector2d(2,2), new Vector2d(1,4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.runParallel(1);

        Vector2d[] expected = new Vector2d[]{
                new Vector2d(3, 5),
                new Vector2d(3, 3)
        };
        Assertions.assertEquals(expected[0], engine.animals.get(0).getPosition());
        Assertions.assertEquals(expected[1], engine.animals.get(1).getPosition());
    }

    @Test
    void animalsWithPlants(){
        String[][] testArgs = {
                {"0","0","0","0","1","2","1","2"},
                {"4","0","0","6","0","0","6","7"}};
        List<List<Integer>> directions = new OptionsParser().parse2D(testArgs);
        RectangularMap map = new RectangularMap(10, 10);
        Vector2d[] positions = {new Vector2d(2,2), new Vector2d(1,4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions, 20);
        System.out.println(map);
        engine.stats();
        engine.runParallelPlants(1, true, 5, 0);
        System.out.println(map);
        engine.stats();
//        Vector2d[] expected = new Vector2d[]{
//                new Vector2d(3, 5),
//                new Vector2d(3, 3)
//        };
//        Assertions.assertEquals(expected[0], engine.animals.get(0).getPosition());
//        Assertions.assertEquals(expected[1], engine.animals.get(1).getPosition());
    }
}
