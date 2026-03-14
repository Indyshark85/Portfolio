import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class P1StudentTest {
    @Test
    public void testSimpleGoal() {
        // 3x3 grid, goal at (2, 2)
        int[][] empty = {};
        AutonomousVehicle vehicle = new AutonomousVehicle(
                3, 2, 2,
                empty, empty, empty, empty
        );

        Path path = vehicle.findBestPath(0, 0, 4);

        assertNotNull(path, "Path should not be null");
        System.out.println(path);
    }

    @Test
    public void testAvoidConstruction() {
        // 3x3 grid, goal at (2, 2), but (1,1) has construction
        int[][] constructions = { {1, 1} };
        int[][] empty = {};

        AutonomousVehicle vehicle = new AutonomousVehicle(
                3, 2, 2,
                empty, constructions, empty, empty
        );

        Path path = vehicle.findBestPath(0, 0, 4);

        assertNotNull(path);
        System.out.println("Avoids construction path: " + path);
    }

    @Test
    public void testRewardComparison() {
        Path p1 = new Path(10);
        Path p2 = new Path(5);
        assertTrue(p1.compareTo(p2) > 0, "Higher reward should compare greater");
    }

    @Test
    public void testMoveEnumDirections() {
        assertEquals(1, Move.DOWN.getDx());
        assertEquals(0, Move.LEFT.getDx());
        assertEquals(-1, Move.UP.getDx());
        assertEquals(1, Move.RIGHT.getDy());
    }
}