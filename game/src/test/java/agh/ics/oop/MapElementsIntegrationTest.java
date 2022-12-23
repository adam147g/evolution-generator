package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MapElementsIntegrationTest {
    @Test
    void worldTest(){
        String[][] testArgs = {
                {"0","0","0","0","1","2","1","2"},
                {"4","0","0","6","0","0","6","7"}};
        List<List<Integer>> directions = new OptionsParser().parse2D(testArgs);
        AbstractMap map = new RectangularMap(10, 10);
        Vector2d[] positions = {new Vector2d(2,2), new Vector2d(1,4)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.runParallel(1);

//        Vector2d[] expected = new Vector2d[]{
//                new Vector2d(3, 5),
//                new Vector2d(3, 3)
//        };
//        Assertions.assertEquals(expected[0], engine.animals.get(0).getPosition());
//        Assertions.assertEquals(expected[1], engine.animals.get(1).getPosition());
    }
}
