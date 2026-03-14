package traversals;

import graphs.WeightedBiDirectedGraph;
import heap.BinaryMinHeap;
import heap.HeapNode;
import heap.MinHeap;
import util.Edge;
import util.Progress;
import util.Weight;

import java.util.*;

/**
 * This class implements a Minimum Spanning Tree (MST) strategy using Prim's algorithm.
 * This is almost identical to Dijkstra's algorithm. The only difference is that
 * the current node cost is not added to the total cost.
 */
public class MSTStrategy<V> implements TraversalStrategy<V> {
    private final WeightedBiDirectedGraph<V> graph;
    private final MinHeap<Weight> heap = new BinaryMinHeap<>();
    private final Map<V, HeapNode<Weight>> nodeMap = new HashMap<>();
    private final Map<HeapNode<Weight>, V> reverseNodeMap = new HashMap<>();
    private final Map<V, Edge<V>> parentMap = new HashMap<>();
    private final Set<V> visited = new HashSet<>();

    public MSTStrategy(WeightedBiDirectedGraph<V> graph) {
        this.graph = graph;
    }

    /**
     * Initializes the traversal with the given starting vertex. We add
     * all vertices to the heap with infinite distance, except for the starting vertex
     * which is set to 0.
     */
    public void start(V start) {
        throw new Error("TODO: traversals.MSTStrategy.start()");
    }

    /**
     * Checks if there are more nodes to visit in the heap.
     */
    public boolean hasNext() {
        throw new Error("TODO: traversals.MSTStrategy.hasNext()");
    }

    /**
     * Removes the minimum node from the heap and returns the corresponding vertex.
     * This method should only be called if hasNext() is true.
     */
    public V next() {
        throw new Error("TODO: traversals.MSTStrategy.next()");
    }

    /**
     * Visits the current vertex and its neighbors. If the current vertex or
     * any of its neighbors have already been visited, it returns CONTINUE.
     * Otherwise, we compute the cost to each neighbor as the weight of the
     * edge from the current node to the neighbor. If this cost is less than the
     * current cost of the neighbor, we update the cost in the heap and set the
     * parent of the neighbor to the current node.
     */
    public Progress visit(V current, Collection<V> neighbors) {
        throw new Error("TODO: traversals.MSTStrategy.visit()");
    }

    /**
     * Returns all the edges in the MST. This is done by iterating over the parentMap
     * and creating edges from each child to its parent.
     */
    public Collection<Edge<V>> getMSTEdges() {
        throw new Error("TODO: traversals.MSTStrategy.getMSTEdges()");
    }
}
