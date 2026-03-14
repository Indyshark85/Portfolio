import java.util.*;

/**
 * This class implements a Breadth-First Search on a grid of 1s and 0s,
 * returning the shortest path from 'start' to 'end' (both inclusive).
 */
public class BFSSearch {

    public static ArrayList<Pair> BFS(int[][] grid, Pair start, Pair end) {
        ArrayList<Pair> path = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;

        // Quick check: if start/end is invalid, return empty immediately
        if (!isValid(grid, start) || !isValid(grid, end)) {
            return path;
        }

//         TODO: Initialize queue, visited, and parentMap
//         TODO: Enqueue start, mark visited, set parent of start to null
//         TODO: While queue not empty:
//                 - Dequeue current
//                 - If current == end: reconstruct path and return
//                 - Otherwise, for each neighbor: if valid & not visited, enqueue
//         TODO: Return path if found, otherwise empty

        // If no path found, return empty
        return path;
    }

    // Checks if a coordinate is in bounds and is 1 (traversable)
    private static boolean isValid(int[][] grid, Pair p) {
        int r = p.x();
        int c = p.y();
        return (r >= 0 && r < grid.length &&
                c >= 0 && c < grid[0].length &&
                grid[r][c] == 1);
    }

    // Return up/down/left/right neighbors
    private static Pair[] getNeighbors(Pair p) {
        return new Pair[] {
                new Pair(p.x() - 1, p.y()), // up
                new Pair(p.x() + 1, p.y()), // down
                new Pair(p.x(), p.y() - 1), // left
                new Pair(p.x(), p.y() + 1) // right
        };
    }
}

// Simple record for grid coordinates.
record Pair(int x, int y) {
}
