import graphs.DirectedGraph;
import org.junit.jupiter.api.Test;

public class DirectedGraphTest {

    @Test
    public void testDFS() {
        System.out.printf("-----------------%nDFS%n-----------------%n");
        System.out.printf("g1(A) DFS: %s%n", SampleGraphs.g1().dfs("A"));
        System.out.printf("g2(A) DFS: %s%n", SampleGraphs.g2().dfs("A"));
        System.out.printf("g3(A) DFS: %s%n", SampleGraphs.g3().dfs("A"));
        System.out.printf("g4(A) DFS: %s%n", SampleGraphs.g4().dfs("A"));
    }

    @Test
    public void testBFS() {
        System.out.printf("-----------------%nBFS%n-----------------%n");
        System.out.printf("g1(A) BFS: %s%n", SampleGraphs.g1().bfs("A"));
        System.out.printf("g2(A) BFS: %s%n", SampleGraphs.g2().bfs("A"));
        System.out.printf("g3(A) BFS: %s%n", SampleGraphs.g3().bfs("A"));
        System.out.printf("g4(A) BFS: %s%n", SampleGraphs.g4().bfs("A"));
    }

    @Test
    public void testRandomWalk() {
        System.out.printf("-----------------%nWalk%n-----------------%n");
        System.out.printf("g1(A) walk(10): %s%n", SampleGraphs.g1().randomWalk("A",10));
        System.out.printf("g2(A) walk(10): %s%n", SampleGraphs.g2().randomWalk("A", 10));
        System.out.printf("g3(A) walk(10): %s%n", SampleGraphs.g3().randomWalk("A", 10));
        System.out.printf("g4(A) walk(10): %s%n", SampleGraphs.g4().randomWalk("A", 10));
    }

    @Test
    public void testRandomWalkWithFrequencies() {
        System.out.printf("-----------------%nWalk with Frequency%n-----------------%n");
        System.out.printf("g1(A) walk(5,10): %s%n", SampleGraphs.g1().randomWalkWithFrequency("A",5,10));
        System.out.printf("g2(A) walk(5,10): %s%n", SampleGraphs.g2().randomWalkWithFrequency("A", 5,10));
        System.out.printf("g3(A) walk(5,10): %s%n", SampleGraphs.g3().randomWalkWithFrequency("A", 5,10));
        System.out.printf("g4(A) walk(5,10): %s%n", SampleGraphs.g4().randomWalkWithFrequency("A", 5,10));
    }

    @Test
    public void testFindPath() {
        System.out.printf("-----------------%nPath%n-----------------%n");
        System.out.printf("g1(A,  B) path: %s%n", SampleGraphs.g1().findBFSPath("A","B"));
        System.out.printf("g2(A,  E) path: %s%n", SampleGraphs.g2().findBFSPath("A", "E"));
        System.out.printf("g3(A,A10) path: %s%n", SampleGraphs.g3().findBFSPath("A", "A10"));
        System.out.printf("g4(A,  F) path: %s%n", SampleGraphs.g4().findBFSPath("A", "F"));
    }

    @Test
    public void testCycleDetection() {
        System.out.printf("-----------------%nCycle%n-----------------%n");
        DirectedGraph<String> g = new DirectedGraph<>();
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "A"); // Cycle from A, B, or C
        System.out.printf("ABCA(A) cycle = %s%n", g.findCycle("A"));
        System.out.printf("ABCA(B) cycle = %s%n", g.findCycle("B"));
        System.out.printf("ABCA(C) cycle = %s%n", g.findCycle("C"));
        System.out.printf("ABCA(D) cycle = %s%n", g.findCycle("D")); // Non-existing node
        g.addEdge("A", "D");
        System.out.printf("ABCA(D) cycle = %s%n", g.findCycle("D")); // D cannot reach the cycle
        System.out.printf("g1(A)   cycle = %s%n", SampleGraphs.g1().findCycle("A"));
        System.out.printf("g2(A)   cycle = %s%n", SampleGraphs.g2().findCycle("A"));
        System.out.printf("g2(B)   cycle = %s%n", SampleGraphs.g2().findCycle("B"));
        System.out.printf("g2(C)   cycle = %s%n", SampleGraphs.g2().findCycle("C"));
        System.out.printf("g2(D)   cycle = %s%n", SampleGraphs.g2().findCycle("D"));
        System.out.printf("g2(E)   cycle = %s%n", SampleGraphs.g2().findCycle("E"));
        System.out.printf("g3(A)   cycle = %s%n", SampleGraphs.g3().findCycle("A"));
        System.out.printf("g4(A)   cycle = %s%n", SampleGraphs.g4().findCycle("A"));
        System.out.printf("g4(C)   cycle = %s%n", SampleGraphs.g4().findCycle("C"));
        System.out.printf("g4(G)   cycle = %s%n", SampleGraphs.g4().findCycle("G"));
    }

}