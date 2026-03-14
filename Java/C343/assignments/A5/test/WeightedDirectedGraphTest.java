import graphs.WeightedBiDirectedGraph;
import graphs.WeightedDirectedGraph;
import util.Edge;
import util.EdgePath;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.Optional;

public class WeightedDirectedGraphTest {
    @Test
    public void test1 () {
        WeightedDirectedGraph<String> g = SampleGraphs.g5();
        Optional<EdgePath<String>> path = g.shortestWeightedPath("A", "E");
        System.out.println(path);
    }

    @Test
    public void test2 () {
        WeightedBiDirectedGraph<String> g = SampleGraphs.g6();
        Collection<Edge<String>> edges = g.minimumSpanningTree("A");
        System.out.println(edges);
    }

    @Test
    public void test3() {
        WeightedBiDirectedGraph<String> g = SampleGraphs.g7();

        Optional<EdgePath<String>> path = g.shortestWeightedPath("A", "D");
        System.out.println("Shortest path from A to D: " + path);

        Collection<Edge<String>> edges = g.minimumSpanningTree("A");
        System.out.println("MST edges: " + edges);
    }
}
