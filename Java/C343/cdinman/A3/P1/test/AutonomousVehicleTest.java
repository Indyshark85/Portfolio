import org.junit.jupiter.api.Test;

import java.util.Random;

class AutonomousVehicleTest {
    @Test
    void test1 () {
        int[][] twoLanes = new int[][]{{1, 1}};
        int[][] congestion = new int[][]{{2, 2}};
        int[][] construction = new int[][]{{2,3}};
        int[][] obstacles = new int[][]{{0, 2}};
        AutonomousVehicle av = new AutonomousVehicle(5, 4, 4,
                twoLanes, congestion, construction, obstacles);
        System.out.println(av);
        System.out.println(av.findBestPath(0, 0, 10));
    }

    @Test
    void test2 () {
        int[][] obstacles = new int[][]{{1, 1}, {2, 2}, {3, 0}, {3, 1}, {3, 2}, {3, 3}};
        AutonomousVehicle av = new AutonomousVehicle(5, 4, 4,
                new int[0][0], new int[0][0], new int[0][0], obstacles);
        System.out.println(av);
        System.out.println(av.findBestPath(0, 0, 10));
    }

    @Test
    void test3 () {
        int[][] obstacles = new int[10][2];
        for (int i = 0; i < 10; i++) obstacles[i] = new int[]{i, 1};
        AutonomousVehicle av = new AutonomousVehicle(10, 0, 2,
                new int[0][0], new int[0][0], new int[0][0], obstacles);
        System.out.println(av);
        System.out.println(av.findBestPath(0, 0, 10));
    }

    @Test
    void test4 () {
        int[][] obstacles = {{1,1}, {1,2}, {2,3} };
        AutonomousVehicle av = new AutonomousVehicle(4, 3, 3,
                new int[0][0], new int[0][0], new int[0][0], obstacles);
        System.out.println(av);
        System.out.println(av.findBestPath(0, 0, 10));
    }

    @Test
    void test5 () {
        int[][] twoLanes = new int[][]{{1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, {1, 8}, {1, 9}, {1, 10}, {1, 11}, {1, 12}, {1, 13}, {1, 14}, {1, 15}};
        int[][] construction = new int[][]{{2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8}, {2, 9}, {2, 10}, {2, 11}, {2, 12}, {2, 13}, {2, 14}, {2, 15}};
        int[][] crossings = new int[][]{{3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9}, {3, 10}, {3, 11}, {3, 12}, {3, 13}, {3, 14}, {3, 15}};
        int[][] oneWays = new int[][]{{4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}, {4, 10}, {4, 11}, {4, 12}, {4, 13}, {4, 14}, {4, 15}};
        AutonomousVehicle av = new AutonomousVehicle(15, 7, 7,
                twoLanes, construction, crossings, oneWays);
        System.out.println(av);
        System.out.println(av.findBestPath(0, 0, 15));
    }

    @Test
    void test6 () {
        int[][] twoLanes = new int[][]{{1, 1}, {1, 15}};
        int[][] construction = new int[][]{{2, 2}, {2, 14}};
        int[][] crossings = new int[][]{{3, 3}, {3, 13}};
        int[][] oneWays = new int[][]{{4, 4}, {4, 12}};
        AutonomousVehicle av = new AutonomousVehicle(15, 7, 7,
                twoLanes, construction, crossings, oneWays);
        System.out.println(av);
        System.out.println(av.findBestPath(0, 0, 15));
    }

    @Test
    void test7 () {
        int[][] twoLanes = new int[12][2];
        int[][] construction = new int[12][2];
        int[][] crossings = new int[12][2];
        int[][] oneWays = new int[12][2];
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int x = r.nextInt(11) + 1;
            int y = r.nextInt(11) + 1;
            twoLanes[x] = new int[]{x, y};
            x = r.nextInt(11) + 1;
            y = r.nextInt(11) + 1;
            construction[x] = new int[]{x, y};
            x = r.nextInt(11) + 1;
            y = r.nextInt(11) + 1;
            crossings[x] = new int[]{x, y};
            x = r.nextInt(11) + 1;
            y = r.nextInt(11) + 1;
            oneWays[x] = new int[]{x, y};
        }
        AutonomousVehicle av = new AutonomousVehicle(12, 7, 7,
                twoLanes, construction, crossings, oneWays);
        System.out.println(av);
        System.out.println(av.findBestPath(1, 1, 17));
    }
}