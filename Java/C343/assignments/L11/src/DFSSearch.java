import java.util.ArrayList;

/**
 * This class implements a Depth-First Search on a grid of 1s and 0s,
 * returning ANY valid path (not necessarily the shortest) from 'start' to
 * 'end'.
 */
public class DFSSearch {

    /**
     * @param grid    A 2D matrix of 1s (traversable) and 0s (obstacles).
     * @param visited An ArrayList to track which cells have been visited.
     * @param start   Coordinates of our current position.
     * @param end     The destination coordinates.
     * @return A path from start to end (list of coordinates), or empty if no path
     *         found.
     */
    public static ArrayList<Pair> DFS(int[][] grid, ArrayList<Pair> visited, Pair start, Pair end) {
        ArrayList<Pair> path = new ArrayList<>();

//         TODO: 1) Check if 'start' is valid (in bounds & == 1). If not, return empty
//         TODO: 2) Check if 'visited' contains 'start'. If yes, return empty
//         TODO: 3) Mark 'start' visited, add to path
//         TODO: 4) If start == end, return path
//         TODO: 5) For each neighbor: call DFS, if subpath not empty, add to path & return it
//         TODO: 6) If no neighbor leads to a solution, remove 'start' from visited & path, return empty (given below)


        // Return empty
        return new ArrayList<>();
    }

    // Checks if a coordinate is in bounds and is 1 (traversable)
    private static boolean isValid(int[][] grid, Pair p) {
        int rows = grid.length;
        int cols = grid[0].length;
        int r = p.x();
        int c = p.y();
        return (r >= 0 && r < rows && c >= 0 && c < cols && grid[r][c] == 1);
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

// Simple record for grid coordinates (not strictly needed if BFSSearch's record
// is in the same package).
// record Pair(int x, int y) {
// }
