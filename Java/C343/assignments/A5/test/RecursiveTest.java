import graphs.DirectedGraph;
import graphs.WeightedDirectedGraph;
import traversals.*;
import recursive.*;
import org.junit.jupiter.api.Test;
import util.Weight;
import util.Edge;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class RecursiveTest {

    @Test
    public void test1() {
        WeightedDirectedGraph<String> g = SampleGraphs.g5();
        MaximumFlow<String> flow = new MaximumFlow<>(g, "A", "E");
        int mf = flow.runMaxFlow();
        assertEquals(2, mf);
    }

    @Test
    public void test2() {
        DirectedGraph<String> g = SampleGraphs.g2();
        SCC<String> scc = new SCC<>(g);
        List<Set<String>> components = scc.computeSCCs();
        Set<Set<String>> expected = Set.of(
                Set.of("A"),
                Set.of("C"),
                Set.of("B", "D", "E")
        );
        assertEquals(expected.size(), components.size());
        assertTrue(components.containsAll(expected));
    }

    @Test
    public void test3() {
        DirectedGraph<String> g = SampleGraphs.g2();
        CycleDetection<String> cd = new CycleDetection<>(g);
        cd.traverseAll(g.getVertices());
        assertTrue(cd.foundCycle());
    }

    @Test
    public void test4() {
        DirectedGraph<String> g = SampleGraphs.g1();
        CycleDetection<String> cd = new CycleDetection<>(g);
        cd.traverseAll(g.getVertices());
        assertFalse(cd.foundCycle());
    }

    @Test
    public void test5() {
        DirectedGraph<String> g = SampleGraphs.g1();
        TopologicalSort<String> top = new TopologicalSort<>(g);
        top.traverseAll(g.getVertices());
        List<String> sorted = top.getSorted();
        assertEquals(4, sorted.size());
        assertTrue(sorted.indexOf("A") < sorted.indexOf("B"));
        assertTrue(sorted.indexOf("A") < sorted.indexOf("C"));
        assertTrue(sorted.indexOf("B") < sorted.indexOf("D"));
        assertTrue(sorted.indexOf("C") < sorted.indexOf("D"));
    }

    @Test
    public void test6() {
        DirectedGraph<String> g = SampleGraphs.g4();
        DFSList<String> dfs = new DFSList<>(g);
        dfs.traverseAll(g.getVertices());
        List<String> visited = dfs.getResult();
        assertTrue(visited.containsAll(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")));
        assertEquals(10, visited.size());
    }

    @Test
    public void test7() {
        WeightedDirectedGraph<String> g = SampleGraphs.g5();
        var edge = new Edge<>("B", "D", Weight.of(5));
        g.subtractEdgeWeight(edge, Weight.of(2));
        assertEquals(Weight.of(3), g.getWeightedNeighbors("B").get("D"));
        g.addEdge(edge.flip());
        assertTrue(g.getWeightedNeighbors("D").containsKey("B"));
        g.subtractEdgeWeight(edge, Weight.of(100));
        assertFalse(g.getWeightedNeighbors("B").containsKey("D"));
    }
}
