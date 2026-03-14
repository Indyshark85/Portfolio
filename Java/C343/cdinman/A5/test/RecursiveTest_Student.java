import graphs.DirectedGraph;
import graphs.WeightedDirectedGraph;
import org.junit.jupiter.api.Test;
import recursive.CycleDetection;
import recursive.DFSList;
import recursive.SCC;
import recursive.TopologicalSort;
import traversals.MaximumFlow;
import util.Weight;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RecursiveTest_Student {
    @Test
    public void scc_disconnectedGraph() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        // No edges

        SCC<String> scc = new SCC<>(g);
        List<Set<String>> comps = scc.computeSCCs();

        assertEquals(3, comps.size());
        assertTrue(comps.contains(Set.of("A")));
        assertTrue(comps.contains(Set.of("B")));
        assertTrue(comps.contains(Set.of("C")));
    }

    @Test
    public void scc_twoSeparateCycles() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A", "B");
        g.addEdge("B", "A");
        g.addEdge("C", "D");
        g.addEdge("D", "C");

        SCC<String> scc = new SCC<>(g);
        List<Set<String>> comps = scc.computeSCCs();

        assertEquals(2, comps.size());
        assertTrue(comps.contains(Set.of("A", "B")));
        assertTrue(comps.contains(Set.of("C", "D")));
    }

    @Test
    public void cycleDetection_largeTree_noCycle() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        for (int i = 1; i <= 10; i++) g.addVertex(i);
        g.addEdge(1,2); g.addEdge(1,3);
        g.addEdge(2,4); g.addEdge(2,5);
        g.addEdge(3,6); g.addEdge(3,7);
        g.addEdge(4,8); g.addEdge(5,9); g.addEdge(6,10);

        CycleDetection<Integer> cd = new CycleDetection<>(g);
        cd.traverseAll(g.getVertices());
        assertFalse(cd.foundCycle());
    }

    @Test
    public void cycleDetection_simpleCycle() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("X", "Y");
        g.addEdge("Y", "Z");
        g.addEdge("Z", "X");

        CycleDetection<String> cd = new CycleDetection<>(g);
        cd.traverseAll(g.getVertices());

        assertTrue(cd.foundCycle());
    }

    @Test
    public void topoSort_chain() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,4);

        TopologicalSort<Integer> top = new TopologicalSort<>(g);
        top.traverseAll(g.getVertices());

        List<Integer> sorted = top.getSorted();
        assertEquals(List.of(1,2,3,4), sorted);
    }

    @Test
    public void topoSort_diamondShape() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A","B");
        g.addEdge("A","C");
        g.addEdge("B","D");
        g.addEdge("C","D");

        TopologicalSort<String> top = new TopologicalSort<>(g);
        top.traverseAll(g.getVertices());

        List<String> sorted = top.getSorted();
        assertEquals(4, sorted.size());
        assertTrue(sorted.indexOf("A") < sorted.indexOf("D"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("D"));
        assertTrue(sorted.indexOf("C") < sorted.indexOf("D"));
    }

    @Test
    public void dfsList_visitsInDepthOrder() {
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A","B");
        g.addEdge("A","C");
        g.addEdge("B","D");

        DFSList<String> dfs = new DFSList<>(g);
        dfs.traverse("A");

        List<String> v = dfs.getResult();

        // depth-first should visit A → B → D before exploring C
        assertEquals("A", v.get(0));
        assertEquals("B", v.get(1));
        assertEquals("D", v.get(2));
    }

    @Test
    public void maxFlow_simpleTwoPathNetwork() {
        WeightedDirectedGraph<String> g = new WeightedDirectedGraph<>();
        g.addEdge("S","A", Weight.of(3));
        g.addEdge("S","B", Weight.of(2));
        g.addEdge("A","T", Weight.of(3));
        g.addEdge("B","T", Weight.of(2));

        MaximumFlow<String> mf = new MaximumFlow<>(g, "S", "T");
        int flow = mf.runMaxFlow();

        assertEquals(5, flow);
    }

    @Test
    public void maxFlow_withBottleNeck() {
        WeightedDirectedGraph<String> g = new WeightedDirectedGraph<>();
        g.addEdge("S","A", Weight.of(10));
        g.addEdge("A","B", Weight.of(1));    // bottleneck
        g.addEdge("B","T", Weight.of(10));

        MaximumFlow<String> mf = new MaximumFlow<>(g, "S", "T");
        int flow = mf.runMaxFlow();

        assertEquals(1, flow);
    }
}
