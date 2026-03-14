import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Lab12Test {

    /**
     * Test 1: 4-cycle 0-1-2-3-0.
     * All degrees are 2 → Euler circuit exists.
     */
    @Test
    public void testSimpleCycleHasEulerCircuit() {
        ArrayList<Integer>[] graph = makeGraph(4);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 3);
        addEdge(graph, 3, 0);

        assertTrue(EulerCircuit.hasEulerCircuit(graph),
                "4-cycle should have an Euler circuit");
    }

    @Test
    public void testSimpleCycleFindEulerCircuit() {
        ArrayList<Integer>[] graph = makeGraph(4);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 3);
        addEdge(graph, 3, 0);

        ArrayList<Integer> circuit = EulerCircuit.findEulerCircuit(graph, 0);

        assertNotNull(circuit, "Circuit should not be null");
        assertEquals(5, circuit.size(),
                "Euler circuit should have 5 vertices (4 edges + 1)");
        assertEquals(0, circuit.get(0), "Circuit should start at 0");
        assertEquals(0, circuit.get(circuit.size() - 1), "Circuit should end at 0");
    }

    /**
     * Test 2: Two triangles sharing vertex 0.
     * deg(0)=4, others deg=2 → Euler circuit exists.
     */
    @Test
    public void testTwoTrianglesSharingVertexHasEulerCircuit() {
        ArrayList<Integer>[] graph = makeGraph(5);

        // Triangle 0-1-2-0
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 0);

        // Triangle 0-3-4-0
        addEdge(graph, 0, 3);
        addEdge(graph, 3, 4);
        addEdge(graph, 4, 0);

        assertTrue(EulerCircuit.hasEulerCircuit(graph),
                "Two triangles sharing a vertex should have an Euler circuit");
    }

    @Test
    public void testTwoTrianglesSharingVertexFindEulerCircuit() {
        ArrayList<Integer>[] graph = makeGraph(5);

        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 0);

        addEdge(graph, 0, 3);
        addEdge(graph, 3, 4);
        addEdge(graph, 4, 0);

        ArrayList<Integer> circuit = EulerCircuit.findEulerCircuit(graph, 0);

        assertNotNull(circuit, "Circuit should not be null");
        assertEquals(7, circuit.size(),
                "Euler circuit should have 7 vertices (6 edges + 1)");
        assertEquals(0, circuit.get(0), "Circuit should start at 0");
        assertEquals(0, circuit.get(circuit.size() - 1), "Circuit should end at 0");
    }

    /**
     * Test 3: Path 0-1-2 → Not Eulerian.
     */
    @Test
    public void testPathNoEulerCircuit() {
        ArrayList<Integer>[] graph = makeGraph(3);
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);

        assertFalse(EulerCircuit.hasEulerCircuit(graph),
                "Path 0-1-2 should NOT have an Euler circuit");
    }

    /**
     * Test 4: Empty graph with 5 vertices.
     * No edges → Eulerian by convention.
     */
    @Test
    public void testEmptyGraphEulerCircuit() {
        ArrayList<Integer>[] graph = makeGraph(5);
        // no edges

        assertTrue(EulerCircuit.hasEulerCircuit(graph),
                "Empty graph should be Eulerian by convention");
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
        graph[v].add(u);
    }
}
