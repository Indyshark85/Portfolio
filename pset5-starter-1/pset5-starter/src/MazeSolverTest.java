import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeSolverTest {

    @Test
    void testMazeSolver() {
        //Simple solvable maze
        MazeSolver solver1 = new MazeSolver("maze1.in");
        char[][] solution1 = solver1.solve();
        assertAll("Test Simple Solvable Maze",
                () -> assertNotNull(solution1),
                () -> assertEquals('*', solution1[0][0]), // Starting point
                () -> assertEquals('*', solution1[4][6])  // Ending point
        );

        //Maze with no solution (blocked start or end)
        MazeSolver solver2 = new MazeSolver("maze2.in");
        char[][] solution2 = solver2.solve();
        assertNull(solution2, "Should return null for unsolvable maze");

        //Maze with a dead-end at some point
        MazeSolver solver3 = new MazeSolver("maze3.in");
        char[][] solution3 = solver3.solve();
        assertAll("Test Dead-End Maze",
                () -> assertNotNull(solution3),
                () -> assertEquals('*', solution3[0][0]),
                () -> assertEquals('*', solution3[4][6]),
                () -> assertTrue(solution3[1][2] == '*' || solution3[2][2] == '*') // Dead-end test
        );

        //Very small 2x2 maze
        MazeSolver solver4 = new MazeSolver("maze4.in");
        char[][] solution4 = solver4.solve();
        assertAll("Test 2x2 Maze",
                () -> assertNotNull(solution4),
                () -> assertEquals('*', solution4[0][0]),
                () -> assertEquals('*', solution4[1][1])
        );

        //Maze with no open path
        MazeSolver solver5 = new MazeSolver("maze5.in");
        char[][] solution5 = solver5.solve();
        assertNull(solution5, "Should return null for maze with no open path");
    }
}
