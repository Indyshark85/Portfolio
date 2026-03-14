import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lab13Test {

    // ---------------------------------------------------------
    // Cycle detection tests
    // ---------------------------------------------------------

    @Test
    public void testCycleDetectionOnSimpleCycle() {
        // Graph: 0 -> 1 -> 2 -> 0  (directed cycle)
        ArrayList<Integer>[] graph = makeGraph(3);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 0);

        assertTrue(CycleDetection.hasCycle(graph),
                "Graph 0->1->2->0 should have a cycle");
    }

    @Test
    public void testCycleDetectionOnDAG() {
        // DAG: 0 -> 1 -> 2, 0 -> 2
        ArrayList<Integer>[] graph = makeGraph(3);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 0, 2);

        assertFalse(CycleDetection.hasCycle(graph),
                "DAG should not have a cycle");
    }

    @Test
    public void testCycleDetectionOnDisconnectedGraphWithOneCyclicComponent() {
        // Component 1: 0 -> 1 -> 0  (cycle)
        // Component 2: 2 -> 3        (no cycle)
        ArrayList<Integer>[] graph = makeGraph(4);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 0);
        addEdge(graph, 2, 3);

        assertTrue(CycleDetection.hasCycle(graph),
                "Graph with a cyclic component should report a cycle");
    }

    // ---------------------------------------------------------
    // Topological sort tests
    // ---------------------------------------------------------

    @Test
    public void testTopoSortOnSimpleDAG() {
        // DAG: 0 -> 1 -> 2
        ArrayList<Integer>[] graph = makeGraph(3);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);

        ArrayList<Integer> order = TopologicalSort.topologicalSort(graph);
        assertEquals(3, order.size(), "Topological order should contain all 3 vertices");

        Map<Integer, Integer> pos = indexMap(order);
        assertTrue(pos.get(0) < pos.get(1), "0 should appear before 1 in topological order");
        assertTrue(pos.get(1) < pos.get(2), "1 should appear before 2 in topological order");
    }

    @Test
    public void testTopoSortOnDAGWithBranching() {
        // DAG:
        //   0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3
        ArrayList<Integer>[] graph = makeGraph(4);
        addEdge(graph, 0, 1);
        addEdge(graph, 0, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 2, 3);

        ArrayList<Integer> order = TopologicalSort.topologicalSort(graph);
        assertEquals(4, order.size(), "Topological order should contain all 4 vertices");

        Map<Integer, Integer> pos = indexMap(order);

        // For every edge u -> v, pos[u] < pos[v]
        assertTrue(pos.get(0) < pos.get(1), "0 should appear before 1");
        assertTrue(pos.get(0) < pos.get(2), "0 should appear before 2");
        assertTrue(pos.get(1) < pos.get(3), "1 should appear before 3");
        assertTrue(pos.get(2) < pos.get(3), "2 should appear before 3");
    }

    @Test
    public void testTopoSortOnGraphWithIsolatedVertex() {
        // DAG:
        //   0 -> 1
        //   2 isolated
        ArrayList<Integer>[] graph = makeGraph(3);
        addEdge(graph, 0, 1);
        // vertex 2 has no edges

        ArrayList<Integer> order = TopologicalSort.topologicalSort(graph);
        assertEquals(3, order.size(), "Topological order should contain all 3 vertices");

        Map<Integer, Integer> pos = indexMap(order);

        // Edge constraint
        assertTrue(pos.get(0) < pos.get(1), "0 should appear before 1");

        // Vertex 2 can be anywhere, so we don't constrain its position
        assertTrue(order.contains(2), "Topological order should include isolated vertex 2");
    }

    // ---------------------------------------------------------
    // Kosaraju SCC tests
    // ---------------------------------------------------------

    @Test
    public void testSCCSingleStrongComponent() {
        // Single SCC: 0 -> 1 -> 2 -> 0
        ArrayList<Integer>[] graph = makeGraph(3);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 0);

        ArrayList<ArrayList<Integer>> components =
                KosarajuSCC.stronglyConnectedComponents(graph);

        // Expect exactly one SCC containing all three vertices
        assertEquals(1, components.size(), "There should be exactly 1 SCC");
        ArrayList<Integer> scc = components.get(0);

        assertEquals(3, scc.size(), "The single SCC should contain all 3 vertices");
        assertTrue(scc.contains(0));
        assertTrue(scc.contains(1));
        assertTrue(scc.contains(2));
    }

    @Test
    public void testSCCMultipleComponents() {
        // Graph:
        //   0 -> 1, 1 -> 0        (SCC #1: {0,1})
        //   2 -> 3, 3 -> 4, 4 -> 2  (SCC #2: {2,3,4})
        //   5 alone (no edges)      (SCC #3: {5})
        ArrayList<Integer>[] graph = makeGraph(6);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 0);

        addEdge(graph, 2, 3);
        addEdge(graph, 3, 4);
        addEdge(graph, 4, 2);

        // vertex 5 has no edges

        ArrayList<ArrayList<Integer>> components =
                KosarajuSCC.stronglyConnectedComponents(graph);

        // Expect 3 SCCs (order may vary)
        assertEquals(3, components.size(), "There should be exactly 3 SCCs");

        boolean has01 = false;
        boolean has234 = false;
        boolean has5 = false;

        for (ArrayList<Integer> comp : components) {
            if (comp.size() == 2 && comp.contains(0) && comp.contains(1)) {
                has01 = true;
            } else if (comp.size() == 3 && comp.contains(2) && comp.contains(3) && comp.contains(4)) {
                has234 = true;
            } else if (comp.size() == 1 && comp.contains(5)) {
                has5 = true;
            }
        }

        assertTrue(has01, "One SCC should be {0,1}");
        assertTrue(has234, "One SCC should be {2,3,4}");
        assertTrue(has5, "One SCC should be {5}");
    }

    @Test
    public void testSCCWithChainBetweenComponents() {
        // Graph:
        //   0 <-> 1      (SCC #1)
        //   2 <-> 3      (SCC #2)
        //   1 -> 2       (edge between SCCs)
        ArrayList<Integer>[] graph = makeGraph(4);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 0);
        addEdge(graph, 2, 3);
        addEdge(graph, 3, 2);
        addEdge(graph, 1, 2);

        ArrayList<ArrayList<Integer>> components =
                KosarajuSCC.stronglyConnectedComponents(graph);

        assertEquals(2, components.size(), "There should be exactly 2 SCCs");

        boolean has01 = false;
        boolean has23 = false;

        for (ArrayList<Integer> comp : components) {
            if (comp.size() == 2 && comp.contains(0) && comp.contains(1)) {
                has01 = true;
            } else if (comp.size() == 2 && comp.contains(2) && comp.contains(3)) {
                has23 = true;
            }
        }

        assertTrue(has01, "One SCC should be {0,1}");
        assertTrue(has23, "One SCC should be {2,3}");
    }

    // ---------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------

    @SuppressWarnings("unchecked")
    private static ArrayList<Integer>[] makeGraph(int n) {
        ArrayList<Integer>[] g = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        return g;
    }

    private static void addEdge(ArrayList<Integer>[] graph, int u, int v) {
        graph[u].add(v);
    }

    private static Map<Integer, Integer> indexMap(ArrayList<Integer> order) {
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            pos.put(order.get(i), i);
        }
        return pos;
    }
}
