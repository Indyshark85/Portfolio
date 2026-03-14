package graphs;

import traversals.DijkstraStrategy;
import util.Edge;
import util.EdgePath;
import util.Weight;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * WeightedDirectedGraph is a graph where edges have weights and can be
 * traversed in one direction.
 */
public class WeightedDirectedGraph<V> extends DirectedGraph<V> {
    private final Map<V, Map<V, Weight>> weights = new HashMap<>();

    //-------------------------------------------------------------------------
    // Graph structure
    //-------------------------------------------------------------------------

    /**
     * Adds an edge to the graph with a specified weight.
     */
    public void addEdge (Edge<V> edge) {
        addEdge(edge.from(), edge.to(), edge.weight());
    }

    /**
     * Adds a directed edge from 'from' to 'to' with a specified weight. If either
     * vertex does not exist, it adds them.
     */
    public void addEdge(V from, V to, int weight) {
        addEdge(from, to, Weight.of(weight));
    }

    public void addEdge(V from, V to, Weight weight) {
        super.addEdge(from, to);
        weights.computeIfAbsent(from, k -> new HashMap<>()).put(to, weight);
    }

    /**
     * Gets all the neighbors of a vertex along with their weights.
     */
    public Map<V, Weight> getWeightedNeighbors(V v) {
        return weights.getOrDefault(v, new HashMap<>());
    }

    /**
     * TODO
     * The method first calculates the new weight by subtracting the given amount
     * from the current weight. If the new weight is non-positive (i.e., zero or negative),
     * it removes the edge from the graph. Otherwise, it updates the weight of the edge.
     */
    public void subtractEdgeWeight(Edge<V> edge, Weight amount) {
        V from = edge.from();
        V to = edge.to();

        Map<V, Weight> outgoing = weights.get(from);
        if (outgoing == null || !outgoing.containsKey(to)) {
            return;
        }

        Weight current = outgoing.get(to);
        Weight newWeight = current.minus(amount);

        if (newWeight.isNonPositive()) {
            outgoing.remove(to);
            getNeighbors(from).remove(to);
            if (outgoing.isEmpty()) {
                weights.remove(from);
            }
        } else {
            outgoing.put(to, newWeight);
        }
    }

    /**
     * TODO
     * The method first checks if the edge exists in the graph. If it does not, then
     * it adds the edge with the given weight. If it does exist, it retrieves the current
     * weight of the edge and updates it by adding the given amount to it.
     */
    public void addEdgeWeight(Edge<V> edge, Weight amount) {
        V from = edge.from();
        V to = edge.to();

        if (!hasEdge(from, to)) {
            addEdge(from, to, amount);
            return;
        }

        Weight current = weights.get(from).get(to);
        Weight newWeight = current.plus(amount);

        weights.get(from).put(to, newWeight);
    }

    /**
     * TODO
     * The method returns a new directed graph with the same vertices and edges. It is
     * important to note that the new graph should not share any references with the original graph.
     * This means that if you modify the new graph, it should not affect the original graph and
     * vice versa.
     */
    public WeightedDirectedGraph<V> copy() {
        WeightedDirectedGraph<V> copy = new WeightedDirectedGraph<>();
        for (V v : getVertices()) {
            copy.addVertex(v);
        }
        for (V from : getVertices()) {
            Map<V, Weight> outgoing = getWeightedNeighbors(from);
            for (Map.Entry<V, Weight> e : outgoing.entrySet()) {
                copy.addEdge(from, e.getKey(), e.getValue());
            }
        }
        return copy;
    }

    //-------------------------------------------------------------------------
    // Entry point for Dijkstra's shortest path traversal
    //-------------------------------------------------------------------------

    public Optional<EdgePath<V>> shortestWeightedPath(V start, V goal) {
        DijkstraStrategy<V> strategy = new DijkstraStrategy<>(this, goal);
        traverse(start, strategy);
        return strategy.getPath();
    }
    public Optional<Weight> getEdgeWeight(V from, V to) {
        Map<V, Weight> outgoing = weights.get(from);
        if (outgoing == null) return Optional.empty();
        Weight w = outgoing.get(to);
        return Optional.ofNullable(w);
    }
}