import graphs.DirectedGraph;
import org.junit.jupiter.api.Test;
import traversals.CycleDetectStrategy;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @Test
    public void testCycleDetectedCorrectly() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(8, 6);  // the cycle

        CycleDetectStrategy<Integer> strat = new CycleDetectStrategy<>();

        g.traverse(5, strat);

        List<Integer> cycle = strat.getCycle();

        // cycle should be 6 → 7 → 8 → 6
        assertEquals(List.of(6,7,8,6), cycle);
    }

    @Test
    public void cycleDetectStrategy_returnsNoNulls() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,1);  // cycle

        CycleDetectStrategy<Integer> strat = new CycleDetectStrategy<>();

        g.traverse(1, strat);

        List<Integer> cycle = strat.getCycle();
        assertNotNull(cycle);      // passes
    }


    @Test
    public void cycleDetectStrategy_noCycleGivesEmptyList() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        g.addEdge(1,2);
        g.addEdge(2,3);

        CycleDetectStrategy<Integer> strat = new CycleDetectStrategy<>();
        g.traverse(1, strat);

        assertEquals(List.of(), strat.getCycle());
    }
    @Test
    public void cycleDetectStrategy_stopsAfterCycle() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,2);   // cycle

        CycleDetectStrategy<Integer> strat = new CycleDetectStrategy<>();

        g.traverse(1, strat);

        assertEquals(List.of(2,3,2), strat.getCycle());
    }


}
