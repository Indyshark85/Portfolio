import java.util.ArrayList;
import java.util.HashSet;

/**
 * A single test file that tests:
 * 1) GridSearch (BFS for words),
 * 2) DFSSearch (DFS for path in grid),
 * 3) BFSSearch (BFS for shortest path in grid).
 */
public class Lab11Test {
        public static void main(String[] args) {
                testGridSearch(); // BFS-based word finder
                testDFSSearch(); // DFS path
                testBFSSearch(); // BFS path
        }

        // ---------------------------------------------------------------
        // 1) Test BFS-based word search (GridSearch)
        static void testGridSearch() {
                System.out.println("=== TESTING GridSearch (BFS for words) ===");

                // Test #1: Simple 2x2
                char[][] letters1 = {
                                { 'C', 'A' },
                                { 'T', 'O' }
                };
                GridSearch gs1 = new GridSearch(letters1);
                gs1.findWords();
                HashSet<String> result1 = gs1.getFoundWords();
                System.out.println("2x2 Letters => " + result1);
                // Expect to see sequences of length >= 2: "ca", "cat", "at", "ato", "to", etc.
                // BFS expansions can vary, but typically you'll see at least "ca", "ta", etc.

                // Test #2: Slightly bigger 3x3
                char[][] letters2 = {
                                { 'C', 'A', 'T' },
                                { 'A', 'T', 'O' },
                                { 'T', 'O', 'P' }
                };
                GridSearch gs2 = new GridSearch(letters2);
                gs2.findWords();
                HashSet<String> result2 = gs2.getFoundWords();
                System.out.println("3x3 Letters => " + result2);

                // Implementation detail: The BFS approach might gather lots of combos, e.g.
                // "ca", "cat", "at", "ato", "to", "top", "tap", ...
                // The main check is that we get a bunch of 2+ letter sequences, not empty.

                System.out.println("-----------------------------------------\n");
        }

        // ---------------------------------------------------------------
        // 2) Test DFS path in grid of 1s (traversable) and 0s (obstacles)
        static void testDFSSearch() {
                System.out.println("=== TESTING DFSSearch (DFS for path) ===");

                // Test #1: 4x4 from the example
                int[][] grid1 = {
                                { 1, 0, 1, 1 },
                                { 1, 1, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 1, 1, 1, 1 }
                };
                Pair start1 = new Pair(0, 0);
                Pair end1 = new Pair(3, 3);
                ArrayList<Pair> visited1 = new ArrayList<>();
                ArrayList<Pair> path1 = DFSSearch.DFS(grid1, visited1, start1, end1);
                System.out.println("DFS Path from " + start1 + " to " + end1 + ": " + path1);
                // Might be: [(0,0), (1,0), (1,1), (2,1), (3,1), (3,2), (3,3)]
                // or a different valid path.

                // Test #2: Another 3x3
                int[][] grid2 = {
                                { 1, 1, 0 },
                                { 0, 1, 1 },
                                { 1, 1, 1 }
                };
                Pair start2 = new Pair(0, 0);
                Pair end2 = new Pair(2, 2);
                ArrayList<Pair> visited2 = new ArrayList<>();
                ArrayList<Pair> path2 = DFSSearch.DFS(grid2, visited2, start2, end2);
                System.out.println("DFS Path from " + start2 + " to " + end2 + ": " + path2);
                // If there's a valid route, you'll see it listed. Otherwise, empty.

                System.out.println("-----------------------------------------\n");
        }

        // ---------------------------------------------------------------
        // 3) Test BFS shortest path in same kind of grid
        static void testBFSSearch() {
                System.out.println("=== TESTING BFSSearch (BFS for path) ===");

                // Test #1: same 4x4 grid as DFS
                int[][] grid1 = {
                                { 1, 0, 1, 1 },
                                { 1, 1, 0, 1 },
                                { 0, 1, 1, 0 },
                                { 1, 1, 1, 1 }
                };
                Pair start1 = new Pair(0, 0);
                Pair end1 = new Pair(3, 3);
                ArrayList<Pair> path1 = BFSSearch.BFS(grid1, start1, end1);
                System.out.println("BFS Path from " + start1 + " to " + end1 + ": " + path1);
                // Expect shortest path. Possibly
                // [(0,0), (1,0), (1,1), (2,1), (3,1), (3,2), (3,3)].

                // Test #2: The same 3x3 as above
                int[][] grid2 = {
                                { 1, 1, 0 },
                                { 0, 1, 1 },
                                { 1, 1, 1 }
                };
                Pair start2 = new Pair(0, 0);
                Pair end2 = new Pair(2, 2);
                ArrayList<Pair> path2 = BFSSearch.BFS(grid2, start2, end2);
                System.out.println("BFS Path from " + start2 + " to " + end2 + ": " + path2);

                System.out.println("-----------------------------------------\n");
        }
}
