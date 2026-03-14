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
        while (true) {
            try {
                heap.removeMin();
            } catch (MinHeap.EmptyHeapExc e) {
                break;
            }
        }

        nodeMap.clear();
        reverseNodeMap.clear();
        parentMap.clear();
        visited.clear();

        for (V v : graph.getVertices()) {
            Weight dist = v.equals(start) ? Weight.ZERO : Weight.infinity();
            HeapNode<Weight> hn = new HeapNode<>(dist) {};
            heap.insert(hn);
            nodeMap.put(v, hn);
            reverseNodeMap.put(hn, v);
        }
    }

    /**
     * Checks if there are more nodes to visit in the heap.
     */
    public boolean hasNext() {
        try {
            // If min is infinite, but unvisited nodes remain, restart Prim
            if (heap.getMin().getValue().isInfinite()) {
                for (V v : graph.getVertices()) {
                    if (!visited.contains(v)) {
                        heap.reduceValue(nodeMap.get(v), Weight.ZERO);
                        return true;
                    }
                }
                return false;
            }
            return true;
        } catch (MinHeap.EmptyHeapExc e) {
            return false;
        }
    }

    /**
     * Removes the minimum node from the heap and returns the corresponding vertex.
     * This method should only be called if hasNext() is true.
     */
    public V next() {
        try {
            while (true) {
                HeapNode<Weight> node = heap.removeMin();
                V v = reverseNodeMap.get(node);
                if (!visited.contains(v)) return v;
                // otherwise skip and continue
            }
        } catch (MinHeap.EmptyHeapExc e) {
            return null;
        }
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
        if (visited.contains(current)) return Progress.CONTINUE;
        visited.add(current);

        for (V n : neighbors) {
            if (visited.contains(n)) continue;

            Optional<Weight> wOpt = graph.getEdgeWeight(current, n);
            if (wOpt.isEmpty()) continue;

            Weight cost = wOpt.get();
            HeapNode<Weight> nNode = nodeMap.get(n);
            if (nNode == null) continue;

            if (cost.compareTo(nNode.getValue()) < 0) {
                heap.reduceValue(nNode, cost);
                parentMap.put(n, new Edge<>(current, n, cost));
            }
        }

        return Progress.CONTINUE;
    }

    /**
     * Returns all the edges in the MST. This is done by iterating over the parentMap
     * and creating edges from each child to its parent.
     */
    public Collection<Edge<V>> getMSTEdges() {
        return new ArrayList<>(parentMap.values());
    }
}