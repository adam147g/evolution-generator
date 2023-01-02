package agh.ics.oop;

import java.util.*;

public class RectangularMap extends AbstractMap  {
    public int plantGrowthType;
    public int maxRangeX;
    public int maxRangeY;
    public Vector2d leftBottomJungle;
    public Vector2d rightTopJungle;
    // 2d array filled 2-elements arrays - first element is death counter, second element is rank status - 0 means (i, j) vector hasn't the least deaths, 1 means (i, j) vector has the least deaths
    public ArrayList<ArrayList<Integer>> deathStatsMap;
    public Set<Vector2d> theLeastDeathCountLeader;
    public int deathCount;




    // plantGrowthType 0 - CONSTRUCTOR
    // jungleHeightPercentage - <0, 1>
    public RectangularMap(int width, int height, double jungleHeightPercentage) {
        super(0, 0, width - 1, height - 1);
        this.plantGrowthType = 0;
        this.maxRangeX = width;
        this.maxRangeY = height;
        int jungleHeight = (int) (height * jungleHeightPercentage);
        // make sure jungle map is perfect centered in world map
        if ((height % 2 == 0 && jungleHeight % 2 == 1) || (height % 2 == 1 && jungleHeight % 2 == 0)) {
            jungleHeight++;
        }
        int jungleLeftBottomY = (height - jungleHeight)/2;
        int jungleRightTopY = height - jungleLeftBottomY - 1;
        this.leftBottomJungle = new Vector2d(0, jungleLeftBottomY);
        this.rightTopJungle = new Vector2d(width - 1, jungleRightTopY);
//        this.jungleMap = new JungleMap(0, jungleLeftBottomY, width - 1, jungleRightTopY);
    }

    // plantGrowthType 1 - CONSTRUCTOR
    public RectangularMap(int width, int height) {
        super(0, 0, width - 1, height - 1);
        this.plantGrowthType = 1;
        this.maxRangeX = width;
        this.maxRangeY = height;
        this.deathStatsMap = new ArrayList<>(height);
        this.theLeastDeathCountLeader = new HashSet<>();
        for (int i = 0; i < height; i++) {
            deathStatsMap.add(new ArrayList<>(width));
            for (int j = 0; j < width; j++){
                deathStatsMap.get(i).add(0);
                theLeastDeathCountLeader.add(new Vector2d(j, i));
            }
        }
//        System.out.println("theLeastDeathCountLeader size: " + theLeastDeathCountLeader.size());
//        System.out.println("theLeastDeathCountLeader: " + theLeastDeathCountLeader);
    }


    public void incrementDeathStat(Vector2d position) {
        int currentDeathStats = this.deathStatsMap.get(position.y).get(position.x);
        this.deathStatsMap.get(position.y).set(position.x, currentDeathStats + 1);
        updateDeathStatRank();
    }
    public void updateDeathStatRank() {
        double minVal = Double.POSITIVE_INFINITY;
        // FIND MINIMUM VALUE OF DEATHS
        for (ArrayList<Integer> integers : this.deathStatsMap) {
            for (int j = 0; j < this.deathStatsMap.get(0).size(); j++) {
                int currentVal = integers.get(j);
                if (currentVal < minVal) {
                    minVal = currentVal;
                }
            }
        }
        // PUT VECTORS WHICH HAVE MINIMUM VALUE OF DEATHS TO LEADER SET
        for (int i = 0; i < this.deathStatsMap.size(); i++) {
            for (int j = 0; j < this.deathStatsMap.get(0).size(); j++){
                int currentVal = this.deathStatsMap.get(i).get(j);
                if (currentVal == minVal) {
                    this.theLeastDeathCountLeader.add(new Vector2d(j, i));
                } else {
                    this.theLeastDeathCountLeader.remove(new Vector2d(j, i));
                }
            }
        }
    }
    public void updateDeathCount(int value) {
        this.deathCount += value;
    }
    public boolean spawnPlantRandomly(boolean inJungle) {
        Vector2d randomPosition = new Vector2d(-1, -1);
        if (plantGrowthType == 0) {
            if (inJungle) {
                while (!canMoveTo(randomPosition) || !randomPosition.includesIn(leftBottomJungle, rightTopJungle)) {
                    randomPosition.randomizeOnMap(this);
                }
            } else {
                while (!canMoveTo(randomPosition) || randomPosition.includesIn(leftBottomJungle, rightTopJungle)) {
                    randomPosition.randomizeOnMap(this);
                }
            }
        } else if (plantGrowthType == 1) {
            if (inJungle) {
                if (theLeastDeathCountLeader.size() == 0) {
//                    System.out.println("can't place plant");
                    return false;
                }
                while (!canMoveTo(randomPosition) || !theLeastDeathCountLeader.contains(randomPosition)) {
                    randomPosition.randomizeOnMap(this);
                }
            } else {
                if (theLeastDeathCountLeader.size() == this.maxRangeX * this.maxRangeY) {
//                    System.out.println("can't place plant");
                    return false;
                }
                while (!canMoveTo(randomPosition) || theLeastDeathCountLeader.contains(randomPosition)) {
                    randomPosition.randomizeOnMap(this);
                }
            }
        }
        return spawnPlantAt(randomPosition);
    }
    public boolean spawnPlantAt(Vector2d position) {
        if (!isTherePlant(position)) {
            Plant plantToAdd = new Plant(position, this);
            this.plantElementsMap.put(position, plantToAdd);
//            this.place(plantToAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean isTherePlant(Vector2d position) {
        return this.plantElementsMap.containsKey(position);
    }
}
