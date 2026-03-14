import graphs.WikiDirectedGraph;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class WikiDirectedGraphTest {

    @Test
    public void testBasicDFSFromQuantum() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 30);
        URI start = wiki.toURI("Quantum_mechanics");
        List<URI> visited = wiki.dfs(start);
        System.out.printf("-----------------%nDFS(Quantum)%n-----------------%n");
        visited.forEach(uri -> System.out.println(wiki.fromURI(uri)));
        assertFalse(visited.isEmpty());
        assertTrue(visited.getFirst().toString().contains("/wiki/Quantum_mechanics"));
    }

    @Test
    public void testDFSHealth() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 30);
        URI start = wiki.toURI("Health");
        List<URI> visited = wiki.dfs(start);
        System.out.printf("-----------------%nDFS(Health)%n-----------------%n");
        visited.forEach(uri -> System.out.println(wiki.fromURI(uri)));
        assertFalse(visited.isEmpty());
        assertTrue(visited.getFirst().toString().contains("/wiki/Health"));
    }


    @Test
    public void testBasicBFSFromGravity() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 30);
        URI start = wiki.toURI("Gravity");
        List<URI> visited = wiki.bfs(start);
        System.out.printf("-----------------%nBFS(Gravity)%n-----------------%n");
        visited.forEach(uri -> System.out.println(wiki.fromURI(uri)));
        assertFalse(visited.isEmpty());
        assertTrue(visited.getFirst().toString().contains("/wiki/Gravity"));
    }

    @Test
    public void testRandomWalkFromRelativity() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 30);
        URI start = wiki.toURI("Theory_of_relativity");
        List<URI> walk = wiki.randomWalk(start,10);
        System.out.printf("-----------------%nRandom(Relativity)%n-----------------%n");
        walk.forEach(uri -> System.out.println(wiki.fromURI(uri)));
        assertFalse(walk.isEmpty());
        assertTrue(walk.getFirst().toString().contains("/wiki/Theory_of_relativity"));
    }

    @Test
        public void testRandomWalkWithFrequencies() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 100);
        URI start = wiki.toURI("Internet");
        Map<URI, Integer> freq = wiki.randomWalkWithFrequency(start, 10, 30);
        assertFalse(freq.isEmpty(), "Frequencies map should not be empty");
        System.out.printf("-----------------%nRandom(Internet)%n-----------------%n");
        freq.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(30)
                .forEach(entry ->
                        System.out.println(wiki.fromURI(entry.getKey()) + " → " + entry.getValue()));
    }


    @Test
    public void testFindPathBetweenTwoConcepts() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 30);
        URI start = wiki.toURI("Computer_science");
        URI goal = wiki.toURI("Artificial_intelligence");
        List<URI> path = wiki.findBFSPath(start, goal);
        System.out.printf("-----------------%nPath(CS-AI)%n-----------------%n");
        path.forEach(uri -> System.out.println(wiki.fromURI(uri)));
    }

    @Test
    public void testCycleDetectionFromPhysics() {
        WikiDirectedGraph wiki = new WikiDirectedGraph(200, 30);
        URI start = wiki.toURI("Physics");
        List<URI> cycle = wiki.findCycle(start);
        System.out.printf("-----------------%nCycle(Physics)%n-----------------%n");
        cycle.forEach(uri -> System.out.println(wiki.fromURI(uri)));
    }

}