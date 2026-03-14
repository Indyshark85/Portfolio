package traversals;

import graphs.DirectedGraph;
import util.Progress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * RandomStrategy simulates a random walk through the graph.
 */
public class RandomStrategy<V> implements TraversalStrategy<V> {
    private final DirectedGraph<V> graph;
    private final int maxSteps;
    private int steps;
    private final Stack<V> toVisit = new Stack<>();
    private final List<V> result = new ArrayList<>();

    public RandomStrategy(DirectedGraph<V> graph, int maxSteps) {
        this.graph    = graph;
        this.maxSteps = maxSteps;
    }

    public void start(V start) { toVisit.push(start); }
    public boolean hasNext() { return !toVisit.isEmpty() && steps < maxSteps; }
    public V next() { return toVisit.pop(); }
    public List<V> getResult() { return result; }

    /**
     * Adds the current node to the result list and randomly selects a neighbor
     * to visit next.
     */
    public Progress visit(V current, Collection<V> neighbors) {
        steps++;
        result.add(current);
        graph.getRandomNeighbor(neighbors).ifPresent(toVisit::push);
        return Progress.CONTINUE;
    }
}
