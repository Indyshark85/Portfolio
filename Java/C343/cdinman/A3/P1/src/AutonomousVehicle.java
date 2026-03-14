/**
 * Represents an autonomous vehicle that can navigate a grid world. This is in principle
 * quite similar to the code developed in lecture, but with a few modifications. The
 * vehicle can move in four directions: right, left, down, and up. The grid world is
 * represented as a square grid containing various obstacles and rewards. We would prefer
 * a path that avoids constructions, school crossings, two-lane roads, and one-way streets,
 * with different penalties for each. The goal is to reach the destination with the highest
 * reward possible.
 * <p>
 * As a small example, consider we are position (0,0) and we are
 * exploring a path (0,0), (0,1), (0,2), (0,3) and there is a construction at (0,2).
 * When we reach (0,2), we will receive a penalty of (-10). On the way back from
 * the recursive call, the reward at (0,1) will be calculated as
 * 1 + (-10) * 0.9 = -10.9. The 1 is the reward for taking one step; the 0.9 is the distance
 * penalty taking modeling the fact that estimates far in the future may not be as accurate
 * as those close by.
 **/


public class AutonomousVehicle {
    public static final double DISTANCE_PENALTY = 0.9;

    private final int gridSize;
    private final int[][] twoLanes, constructions, crossings, oneWays;
    private final int goalX, goalY;

    public AutonomousVehicle(int gridSize, int goalX, int goalY,
                             int[][] twoLanes,
                             int[][] constructions,
                             int[][] crossings,
                             int[][] oneWays) {
        this.gridSize = gridSize;
        this.twoLanes = twoLanes;
        this.constructions = constructions;
        this.crossings = crossings;
        this.oneWays = oneWays;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    public boolean outOfBounds(int x, int y) {
        return x < 0 || x >= gridSize || y < 0 || y >= gridSize;
    }

    public boolean isGoal(int x, int y) {
        return x == goalX && y == goalY;
    }

    public boolean isTwoLanes(int x, int y) {
        for (int[] lane : twoLanes) {
            if (lane[0] == x && lane[1] == y) return true;
        }
        return false;
    }

    public boolean isConstruction(int x, int y) {
        for (int[] cons : constructions) {
            if (cons[0] == x && cons[1] == y) return true;
        }
        return false;
    }

    public boolean isCrossing(int x, int y) {
        for (int[] cross : crossings) {
            if (cross[0] == x && cross[1] == y) return true;
        }
        return false;
    }

    public boolean isOneWay(int x, int y) {
        for (int[] oneWay : oneWays) {
            if (oneWay[0] == x && oneWay[1] == y) return true;
        }
        return false;
    }

    /**
     * Find the best path from the current position to the goal position. To avoid infinite
     * loops and to model that we can only plan so far in the future, we supply a maximum
     * depth to the search.
     */
    public Path findBestPath(int x, int y, int maxDepth) {
        return findBestPath(x, y, 0, maxDepth);
    }

    /**
     * Find the best path from the current position to the goal position exploring at most
     * maxDepth steps.
     */
    public Path findBestPath(int x, int y, int depth, int maxDepth) {
        /* Base cases */
        if (outOfBounds(x, y)) return new Path(RewardConstants.CRASH.value());
        if (depth == maxDepth) return new Path(RewardConstants.NO_REWARD.value());
        if (isGoal(x, y)) return new Path(RewardConstants.GOAL.value());
        if (isTwoLanes(x, y)) return new Path(RewardConstants.TWO_LANES.value());
        if (isConstruction(x, y)) return new Path(RewardConstants.CONSTRUCTION.value());
        if (isCrossing(x, y)) return new Path(RewardConstants.SCHOOL_CROSSING.value());
        if (isOneWay(x, y)) return new Path(RewardConstants.ONE_WAY.value());

        /* Recursive case */
        Path bestPath = null;


        for (Move move : Move.possibleMoves()) {
            int newX = x + move.getDx();
            int newY = y + move.getDy();

            Path candidate = findBestPath(newX, newY, depth + 1, maxDepth);

            // Add this move to the front of the returned path
            candidate.add(move);

            // Choose the path with the higher reward
            if (bestPath == null || candidate.compareTo(bestPath) > 0) {
                bestPath = candidate;
            }
        }

        return bestPath;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (isGoal(i, j)) sb.append("\uD83C\uDFC6");
                else if (isTwoLanes(i, j)) sb.append("\u2194\uFE0F");
                else if (isConstruction(i, j)) sb.append("\uD83D\uDC77");
                else if (isCrossing(i, j)) sb.append("\uD83D\uDEB8");
                else if (isOneWay(i, j)) sb.append("\uD83D\uDEAB");
                else sb.append("\u2B1C");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
