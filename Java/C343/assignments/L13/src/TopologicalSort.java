import java.util.ArrayList;
import java.util.Collections;

/**
 * Lab 13 Starter Code:
 * Recursive DFS Applications – Topological Sort.
 * .
 * High-level idea:
 *  - Perform a DFS from every unvisited vertex.
 *  - Upon finishing a vertex (i.e., after exploring all outgoing edges),
 *    "print" it — in code, we append it to an output list.
 *  - After all DFS calls complete, reverse the list to obtain a
 *    topological ordering of the vertices.
 */
public class TopologicalSort {

    /**
     * Returns a topological ordering of a directed acyclic graph (DAG).
     *
     * @param graph adjacency list: graph[u] contains all vertices v such that u → v
     */
    public static ArrayList<Integer> topologicalSort(ArrayList<Integer>[] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        ArrayList<Integer> order = new ArrayList<>();

        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                dfsTopo(graph, v, visited, order);
            }
        }

        // We appended vertices upon completion (exit), so the list
        // is in reverse topological order.
        Collections.reverse(order);
        return order;
    }

    /**
     * DFS for topological sorting, following the enter/visit/revisit/exit template.
     */
    private static void dfsTopo(ArrayList<Integer>[] graph,
                                int u,
                                boolean[] visited,
                                ArrayList<Integer> order) {

        if (visited[u]) {
            // Vertex has been fully processed before; let revisit() decide behavior.
            revisit(u);
            return;
        }

        enter(u);
        visit(u, visited);

        for (int v : graph[u]) {
            dfsTopo(graph, v, visited, order);
        }

        exit(u, order);
    }

    // ====================== TODO HELPERS ======================

    /**
     * TODO:
     * Called the first time a vertex is encountered,
     * before marking it visited or exploring neighbors.
     * .
     * For topological sorting, no special work is required here.
     */
    private static void enter(int u) {}

    /**
     * TODO:
     * Mark the vertex as visited so it is not processed again.
     */
    private static void visit(int u, boolean[] visited) {}

    /**
     * TODO:
     * Called when the DFS encounters a vertex that has already
     * been marked as visited.
     * No special behavior is needed here for topological sorting.
     */
    private static void revisit(int u) {}

    /**
     * TODO:
     * Called after all neighbors have been explored.
     * For topological sorting, append this vertex to the ordering list.
     */
    private static void exit(int u, ArrayList<Integer> order) {}
}