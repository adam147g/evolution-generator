package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OptionsParserTest {
    @Test
    public void parserTest() {
        String[] testArgs = {"f", "X"};
        try{
            List<Integer> directions = new OptionsParser().parse1D(testArgs);
            Assertions.fail("Arguments are invalid");
        }catch(IllegalArgumentException ex){
            Assertions.assertTrue(true);
        }
    }
}
