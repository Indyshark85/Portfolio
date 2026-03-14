import java.util.ArrayList;

/**
 * Lab 13 Starter Code:
 * Recursive DFS Applications - Cycle Detection.
 * .
 * High-level idea for dfsCycle:
 *  - If we revisit a GRAY vertex, a cycle exists (back edge).
 *  - If we revisit a BLACK vertex, that path is already fully explored.
 *  - For a WHITE vertex:
 *      * mark it GRAY on entry (we are exploring it),
 *      * recursively explore all neighbors,
 *      * mark it BLACK on exit (finished).
 */
public class CycleDetection {

    private static final int WHITE = 0;
    private static final int GRAY  = 1;
    private static final int BLACK = 2;

    public static boolean hasCycle(ArrayList<Integer>[] graph) {
        int n = graph.length;
        int[] color = new int[n]; // all WHITE

        for (int v = 0; v < n; v++) {
            if (color[v] == WHITE) {
                if (dfsCycle(graph, v, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsCycle(ArrayList<Integer>[] graph, int u, int[] color) {
        if (color[u] != WHITE) {
            // Already visited in some form – let revisit() decide
            return revisit(u, color);
        }

        enter(u, color);
        visit(u);

        for (int v : graph[u]) {
            if (dfsCycle(graph, v, color)) {
                return true;
            }
        }

        exit(u, color);
        return false;
    }

    // ================== TODO HELPERS ==================

    /**
     * TODO:
     * Mark this vertex as being currently explored (in the recursion stack).
     * This corresponds to changing its color away from WHITE.
     */
    private static void enter(int u, int[] color) {}

    /**
     * TODO:
     * For cycle detection, there is no extra work needed on visit.
     * You can leave this empty.
     */
    private static void visit(int u) {}

    /**
     * TODO:
     * This is called whenever dfsCycle reaches a vertex whose color is not WHITE.
     * If the vertex is in the middle of being explored (the "in stack" state),
     * then we have found a cycle and should report that.
     * If it is already finished, there is no new cycle here.
     */
    private static boolean revisit(int u, int[] color) { return false; }

    /**
     * TODO:
     * Mark this vertex as completely explored so that future revisits
     * do not report a cycle.
     */
    private static void exit(int u, int[] color) {}
}
