import graphs.DirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class TraversalsTest_Student {
    // TODO
    // For each strategy, write several test cases to ensure that
    // the strategy works correctly. For example, for the BFS strategy,
    // you can create a simple graph and check if the BFS traversal
    // returns the nodes in the correct order. Try different graph structures,
    // such as trees, cycles, and disconnected graphs.

    @Test
    void testDFS () {
        DirectedGraph<String> g = SampleGraphs.g4();
        List<String> result = g.dfs("B");
        System.out.println("DFS Result: " + result);
    }

    @Test
    void testRandom() {
        DirectedGraph<String> g = SampleGraphs.g4();
        Map<String,Integer> map = g.randomWalkWithFrequency("B", 10, 100);
        System.out.println("Random Walk Result: " + map);
    }

}
