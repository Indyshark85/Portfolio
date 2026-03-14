import java.util.ArrayList;

/**
 * Lab 12 Starter Code:
 * Euler Circuits using DFS.
 * .
 * We represent an undirected graph using an adjacency list:
 *   graph[u] is a list of neighbors v such that there is an edge (u, v).
 * For every edge (u, v) we store v in graph[u] AND u in graph[v].
 */

public class EulerCircuit {

    /**
     * Determines whether the graph has an Euler circuit.
     * .
     * High-level idea:
     *   1. Scan the vertices to find one with degree > 0 (call it 'start').
     *      - If no such vertex exists (no edges in the graph), then by convention
     *        the graph DOES have an Euler circuit, so return true.
     *   2. Run a DFS from 'start' (using dfsVisit) to mark all vertices that are
     *      reachable from 'start'. This tells us which vertices are in the same
     *      connected component as 'start'.
     *   3. Check connectivity:
     *      - For every vertex i, if graph[i] is non-empty (i has at least one
     *        incident edge) but visited[i] is false, then some edges lie in a
     *        different component, so the graph cannot have an Euler circuit.
     *   4. Check degree parity:
     *      - For every vertex i, if graph[i].size() is odd, then that vertex has
     *        odd degree, so the graph cannot have an Euler circuit.
     *   5. If the graph passes both the connectivity and even-degree checks,
     *      then it has an Euler circuit, so return true.
     */
    public static boolean hasEulerCircuit(ArrayList<Integer>[] graph) {
        int n = graph.length;

        // Find a vertex with degree > 0 to start DFS from
        int start = -1;
        for (int i = 0; i < n; i++) {
            if (!graph[i].isEmpty()) {
                start = i;
                break;
            }
        }

        // TODO: complete implementation of hasEulerCircuit

        return false; // temporary stub so starter code compiles
    }

    /**
     * DFS Visit
     * .
     * Uses recursion and a visited[] array to mark all vertices reachable from u.
     * This is analogous to your DFS for grids in Lab 11, but on a graph.
     */
    private static void dfsVisit(ArrayList<Integer>[] graph,
                                 boolean[] visited,
                                 int u) {

        // Mark current vertex as visited
        visited[u] = true;

        // Explore neighbors
        for (int v : graph[u]) {
            if (!visited[v]) {
                dfsVisit(graph, visited, v);
            }
        }
    }

    /**
     * Returns an Euler circuit starting at vertex 'start'.
     * If the graph is not Eulerian, returns an empty list.
     * .
     * High-level idea:
     *   - Create a 2D array "used" where used[u][v] says whether
     *     the edge (u, v) has already been used.
     *   - Use a recursive DFS (dfsEuler) that:
     *       * From a vertex u, for each neighbor v:
     *           - If (u, v) is not used yet, mark it used and recurse from v.
     *       * After exploring all neighbors, add u to the circuit.
     *   - The vertices are added in reverse order of the walk,
     *     so reverse the circuit at the end.
     */
    public static ArrayList<Integer> findEulerCircuit(ArrayList<Integer>[] graph, int start) {
        ArrayList<Integer> circuit = new ArrayList<>();
        int n = graph.length;

        // TODO: implement findEulerCircuit

        return circuit; // currently empty so starter compiles
    }

    /**
     * DFS used to build the Euler circuit.
     * .
     * From u:
     *   - For each neighbor v in graph[u]:
     *       * If edge (u, v) has not been used yet:
     *           - Mark used[u][v] and used[v][u] = true.
     *           - Recursively call dfsEuler from v.
     *   - After exploring all neighbors, add u to the circuit.
     * .
     * Note: Here instead of "visited cells", we track "used edges".
     */
    private static void dfsEuler(ArrayList<Integer>[] graph,
                                 boolean[][] used,
                                 int u,
                                 ArrayList<Integer> circuit) {

        // TODO: implement dfsEuler
    }
}
