package agh.ics.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsParser {
    public ArrayList<Integer> parse1D(String[] arguments) {
        String[] turns = {"0","1","2","3","4","5","6","7"};
        ArrayList<Integer> newDirections = new ArrayList<>();
        for (String a : arguments) {
            if (Arrays.asList(turns).contains(a)) {
                newDirections.add(Integer.parseInt(a));
            }
        }
        return newDirections;
    }
    public ArrayList<ArrayList<Integer>> parse2D(String[][] arguments) {
        String[] turns = {"0","1","2","3","4","5","6","7"};
        ArrayList<ArrayList<Integer>> newDirections = new ArrayList<>();
        for (String[] subArrayArg: arguments) {
            ArrayList<Integer> newDirectionsRow = new ArrayList<>();
            for (String arg: subArrayArg){
                if (Arrays.asList(turns).contains(arg)) {
                    newDirectionsRow.add(Integer.parseInt(arg));
                }
            }
            newDirections.add(newDirectionsRow);
        }
        return newDirections;
    }
}
