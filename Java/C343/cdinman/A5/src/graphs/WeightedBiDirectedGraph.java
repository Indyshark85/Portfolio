package graphs;

import traversals.MSTStrategy;
import util.Edge;
import util.Weight;

import java.util.Collection;

/**
 * WeightedBiDirectedGraph is a graph where edges have weights and can be
 * traversed in both directions.
 */
public class WeightedBiDirectedGraph<V> extends WeightedDirectedGraph<V> {
    /**
     * We override the addEdge method to ensure that edges are added in both
     * directions.
     */
    public void addEdge(V from, V to, Weight weight) {
        super.addEdge(from, to, weight);
        super.addEdge(to, from, weight);
    }

    //-------------------------------------------------------------------------
    // Entry point for minimum spanning tree traversal
    //-------------------------------------------------------------------------

    public Collection<Edge<V>> minimumSpanningTree(V start) {
        MSTStrategy<V> strategy = new MSTStrategy<>(this);
        traverse(start, strategy);
        return strategy.getMSTEdges();
    }

}