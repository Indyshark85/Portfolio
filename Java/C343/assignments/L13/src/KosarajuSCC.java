import java.util.ArrayList;
import java.util.Stack;

/**
 * Lab 13 Starter Code:
 * Recursive DFS Applications – Strongly Connected Components (Kosaraju's Algorithm).
 * .
 * High-level idea:
 *   .
 *   We work on a directed graph G with vertices 0..n-1.
 *   .
 *   PASS 1 (on original graph):
 *     - Run DFS from every vertex that has not yet been visited.
 *     - When a vertex finishes (we exit its DFS call), we push it onto a stack.
 *   .
 *   PASS 2 (on the transpose graph G^T):
 *     - Process vertices in the reverse order of their finish times
 *       (by popping from the stack).
 *     - For each vertex popped that has not yet been visited in this pass,
 *       run a DFS in the transpose graph; all vertices reached in this DFS
 *       form one strongly connected component.
 */
public class KosarajuSCC {

    private static final int WHITE = 0; // unvisited
    private static final int GRAY  = 1; // in current DFS
    private static final int BLACK = 2; // finished

    /**
     * Computes all strongly connected components of the graph using Kosaraju's algorithm.
     *
     * @param graph adjacency list for a directed graph G; graph[u] contains all v with edge u → v
     * @return a list of components, where each component is a list of vertex indices
     */
    public static ArrayList<ArrayList<Integer>> stronglyConnectedComponents(ArrayList<Integer>[] graph) {
        int n = graph.length;
        int[] color = new int[n];   // all vertices start as WHITE
        Stack<Integer> exitStack = new Stack<>();

        // ---------- PASS 1: DFS on original graph ----------

        // TODO:
        //  For each vertex index v from 0 to n-1:
        //    If v is still WHITE, run dfs1 on v so that its reachable
        //    vertices are explored and v is eventually pushed onto exitStack
        //    when it finishes.

        // ---------- Build transpose graph ----------
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] revGraph = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            revGraph[i] = new ArrayList<>();
        }

        for (int u = 0; u < n; u++) {
            for (int w : graph[u]) {
                // Reverse edge u → w to w → u in the transpose.
                revGraph[w].add(u);
            }
        }

        // Reset colors for PASS 2
        for (int i = 0; i < n; i++) {
            color[i] = WHITE;
        }

        ArrayList<ArrayList<Integer>> components = new ArrayList<>();

        // ---------- PASS 2: DFS on transpose graph ----------

        // TODO:
        //  While the stack is not empty:
        //    - Pop a vertex v from the stack.
        //    - If v is still WHITE, create a new list to hold one SCC.
        //    - Run dfs2 from v on the transpose graph, filling this SCC list.
        //    - Add the filled SCC list to the 'components' list.

        return components;
    }

    // ====================== PASS 1: dfs1 ======================

    /**
     * DFS on the original graph.
     * Records vertices on a stack in order of completion time.
     */
    private static void dfs1(ArrayList<Integer>[] graph,
                             int u,
                             int[] color,
                             Stack<Integer> exitStack) {

        enter1(u, color);
        visit1(u);

        for (int w : graph[u]) {
            if (color[w] == WHITE) {
                dfs1(graph, w, color, exitStack);
            }
        }

        exit1(u, color, exitStack);
    }

    // ---------- TODO HELPERS FOR PASS 1 ----------

    /**
     * TODO:
     * This method is called when we first arrive at vertex u in PASS 1,
     * before exploring its neighbors.
     * It should mark u as being in the process of exploration.
     */
    private static void enter1(int u, int[] color) {}

    /**
     * TODO:
     * This method is called after enter1, but before exploring neighbors.
     * For this pass of Kosaraju’s algorithm, no additional work is required here.
     */
    private static void visit1(int u) {}

    /**
     * TODO:
     * This method is called after all neighbors of u have been explored in PASS 1.
     * It should:
     *   - mark u as completely finished, and
     *   - record u's finishing time by pushing it onto the stack.
     */
    private static void exit1(int u, int[] color, Stack<Integer> exitStack) {}

    // ====================== PASS 2: dfs2 ======================

    /**
     * DFS on the transpose graph.
     * Collects all vertices reachable from u into one strongly connected component.
     */
    private static void dfs2(ArrayList<Integer>[] revGraph,
                             int u,
                             int[] color,
                             ArrayList<Integer> currentSCC) {

        enter2(u, color, currentSCC);
        visit2(u);

        for (int w : revGraph[u]) {
            if (color[w] == WHITE) {
                dfs2(revGraph, w, color, currentSCC);
            }
        }

        exit2(u, color);
    }

    // ---------- TODO HELPERS FOR PASS 2 ----------

    /**
     * TODO:
     * This method is called when we first arrive at vertex u in PASS 2.
     * It should:
     *   - mark u as being explored, and
     *   - add u to the current strongly connected component list.
     */
    private static void enter2(int u, int[] color, ArrayList<Integer> currentSCC) {}

    /**
     * TODO:
     * This method is called after enter2, but before exploring neighbors.
     * For this pass of Kosaraju’s algorithm, no additional work is required here.
     */
    private static void visit2(int u) {}

    /**
     * TODO:
     * This method is called after all neighbors of u have been explored in PASS 2.
     * It should mark u as completely finished.
     */
    private static void exit2(int u, int[] color) {}
}
