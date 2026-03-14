import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Uses BFS to discover all "words" (strings) in a grid of letters
 * by expanding up/down/left/right from each cell.
 * Any string of length >= 2 gets added to foundWords in lowercase form.
 */
public class GridSearch {
    private final char[][] grid;
    private final int rows;
    private final int cols;
    private HashSet<String> foundWords;

    public GridSearch(char[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.foundWords = new HashSet<>();
    }

    /**
     * Start BFS from each cell and collect words of length >= 2.
     */
    public void findWords() {
        foundWords.clear();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                bfsFrom(r, c);
            }
        }
    }

    /**
     * Perform BFS from a single cell, building strings by appending characters.
     */
    private void bfsFrom(int startRow, int startCol) {
//         TODO: 1) Create a queue of Position, visited[][]
//         TODO: 2) Enqueue new Position with initial letter
//         TODO: 3) While queue not empty:
//                 - Dequeue
//                 - If the 'word' length >= 2, add to foundWords (lowercase)
//                 - For each neighbor in-bounds & not visited: mark visited, append letter, enqueue
    }

    private boolean isInBounds(int r, int c) {
        return (r >= 0 && r < rows && c >= 0 && c < cols);
    }

    public HashSet<String> getFoundWords() {
        return foundWords;
    }

    /**
     * Inner class storing row, col, and word-so-far.
     */
    private static class Position {
        int row, col;
        String word;

        Position(int r, int c, String w) {
            row = r;
            col = c;
            word = w;
        }
    }
}
