import graphs.WeightedBiDirectedGraph;
import graphs.WeightedDirectedGraph;
import org.junit.jupiter.api.Test;
import util.Edge;
import util.EdgePath;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightedDirectedGraphTest_Student {
    @Test
    public void testShortestPathCustomGraph1() {
        WeightedDirectedGraph<String> g = new WeightedDirectedGraph<>();
        g.addEdge("X", "Y", 3);
        g.addEdge("X", "Z", 10);
        g.addEdge("Y", "Z", 2);
        g.addEdge("Z", "W", 1);

        // Shortest path from X to W
        Optional<EdgePath<String>> path = g.shortestWeightedPath("X", "W");
        assertTrue(path.isPresent(), "Expected path from X to W");

        int totalWeight = path.get().edges().stream().mapToInt(e -> e.weight().asInt()).sum();
        assertEquals(6, totalWeight, "Expected shortest path weight X->Y->Z->W");
    }

    @Test
    public void testShortestPathCustomGraph2() {
        WeightedDirectedGraph<String> g = new WeightedDirectedGraph<>();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "A", 4);  // cycle
        g.addEdge("B", "D", 1);
        g.addEdge("D", "E", 5);

        Optional<EdgePath<String>> path = g.shortestWeightedPath("A", "E");
        assertTrue(path.isPresent(), "Expected path from A to E");

        int totalWeight = path.get().edges().stream().mapToInt(e -> e.weight().asInt()).sum();
        assertEquals(7, totalWeight, "Expected shortest path weight A->B->D->E");
    }

    @Test
    public void testMSTCustomGraph1() {
        WeightedBiDirectedGraph<String> g = new WeightedBiDirectedGraph<>();
        g.addEdge("P", "Q", 5);
        g.addEdge("P", "R", 3);
        g.addEdge("Q", "R", 2);
        g.addEdge("R", "S", 4);

        Collection<Edge<String>> mst = g.minimumSpanningTree("P");
        assertEquals(g.getVertices().size() - 1, mst.size(), "MST should have V-1 edges");

        int totalWeight = mst.stream().mapToInt(e -> e.weight().asInt()).sum();
        assertEquals(9, totalWeight, "Expected MST weight");
    }


}
