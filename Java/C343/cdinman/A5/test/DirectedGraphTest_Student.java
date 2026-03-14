import graphs.DirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectedGraphTest_Student {
    @Test
    public void testDFSLinearChain() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("X", "Y");
        g.addEdge("Y", "Z");

        List<String> dfs = g.dfs("X");
        assertEquals(List.of("X", "Y", "Z"), dfs);
    }

    @Test
    public void testBFSBranching() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("C", "D");

        List<String> bfs = g.bfs("A");

        // BFS must visit A first, then B/C in any order, then D
        assertEquals("A", bfs.get(0));
        assertEquals("D", bfs.get(bfs.size() - 1));
        assertTrue(bfs.contains("B"));
        assertTrue(bfs.contains("C"));
    }

    @Test
    public void testRandomWalkDeterministicWhenSinglePath() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        List<String> walk = g.randomWalk("A", 5);

        // Only possible walk: A → B → C → (stays on C)
        assertEquals("A", walk.get(0));
        assertEquals("C", walk.get(walk.size() - 1));
    }

    @Test
    public void testFindPathExists() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("S", "A");
        g.addEdge("A", "B");
        g.addEdge("B", "T");

        List<String> path = g.findBFSPath("S", "T");

        assertEquals(List.of("S", "A", "B", "T"), path);
    }

    @Test
    public void testFindPathNoConnection() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A", "B");
        g.addEdge("C", "D");

        List<String> path = g.findBFSPath("A", "D");

        assertTrue(path.isEmpty());
    }

    @Test
    public void testCycleDetectionSimpleCycle() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("1", "2");
        g.addEdge("2", "3");
        g.addEdge("3", "1");

        List<String> cycle = g.findCycle("1");

        // Cycle could be [1,2,3,1] or rotated variants
        assertEquals(cycle.get(0), cycle.get(cycle.size() - 1));
        assertTrue(cycle.contains("1"));
        assertTrue(cycle.contains("2"));
        assertTrue(cycle.contains("3"));
    }
}
