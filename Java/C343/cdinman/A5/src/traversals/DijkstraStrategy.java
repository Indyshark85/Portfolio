package traversals;

import graphs.WeightedDirectedGraph;
import heap.BinaryMinHeap;
import heap.HeapNode;
import heap.MinHeap;
import util.Edge;
import util.EdgePath;
import util.Progress;
import util.Weight;

import java.util.*;

/**
 * DijkstraStrategy implements Dijkstra's algorithm for finding the shortest
 * path in a weighted directed graph.
 */
public class DijkstraStrategy<V> implements TraversalStrategy<V> {
    // Nodes in the graph are of type V
    private final WeightedDirectedGraph<V> graph;
    // HeapNode<Weight> is used to store the weight (distance) of each node
    // in the heap. The weight is a wrapper around an int, which can be
    // infinite. The heap is a min-heap, so the node with the smallest
    // weight is at the top. The nodeMap and reverseNodeMap are used to
    // navigate between the nodes in the graph and the nodes in the heap.
    private final MinHeap<Weight> heap = new BinaryMinHeap<>();
    private final Map<V, HeapNode<Weight>> nodeMap = new HashMap<>();
    private final Map<HeapNode<Weight>, V> reverseNodeMap = new HashMap<>();
    // The parentMap is used to keep track of the shortest path
    // from the start node to the goal node. The goal is the destination
    // node we are trying to reach.
    private final V goal;
    private final Map<V, Edge<V>> parentMap = new HashMap<>();

    public DijkstraStrategy(WeightedDirectedGraph<V> graph, V goal) {
        this.graph = graph;
        this.goal = goal;
    }
    private V start;
    /**
     * Start the traversal from the given start node. The start node is
     * the source node from which we will begin the traversal. The
     * start node is added to the heap with a distance of 0. All other
     * nodes are added to the heap with an infinite distance.
     */
    public void start(V start) {
        this.start = start;
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

        for (V v : graph.getVertices()) {
            Weight dist = v.equals(start) ? Weight.ZERO : Weight.infinity();
            HeapNode<Weight> node = new HeapNode<>(dist) {};
            heap.insert(node);
            nodeMap.put(v, node);
            reverseNodeMap.put(node, v);
        }
    }

    /**
     * Check if the traversal has reached the goal node.
     */
    public boolean hasNext() {
        try {
            // if the minimum value is infinite, there are no reachable nodes left
            return !heap.getMin().getValue().isInfinite();
        } catch (MinHeap.EmptyHeapExc e) {
            return false;
        }
    }

    /**
     * Get the next node in the traversal. The next() method
     * removes the node with the smallest distance from the heap
     * and returns the corresponding vertex in the graph.
     */
    public V next() {
        try {
            HeapNode<Weight> node = heap.removeMin();
            return reverseNodeMap.get(node);
        } catch (MinHeap.EmptyHeapExc e) {
            return null;
        }
    }

    /**
     * This is the heart of the algorithm. If we reached the goal, we stop. Otherwise,
     * we get the neighbors of the current node and update their distances in the heap.
     * We also update the parent map to keep track of the shortest path.
     */
    public Progress visit(V current, Collection<V> neighbors) {
        if (current.equals(goal)) return Progress.STOP;

        HeapNode<Weight> curNode = nodeMap.get(current);
        if (curNode == null) return Progress.CONTINUE;
        Weight curDist = curNode.getValue();

        for (V nbr : neighbors) {
            // ask graph for the edge weight (Optional<Weight>)
            Optional<Weight> wOpt = graph.getEdgeWeight(current, nbr);
            if (wOpt.isEmpty()) continue;

            Weight edgeW = wOpt.get();
            Weight newDist = curDist.plus(edgeW);

            HeapNode<Weight> nbrNode = nodeMap.get(nbr);
            if (nbrNode == null) continue;
            Weight oldDist = nbrNode.getValue();

            if (newDist.compareTo(oldDist) < 0) {
                heap.reduceValue(nbrNode, newDist);
                parentMap.put(nbr, new Edge<>(current, nbr, edgeW));
            }
        }

        return Progress.CONTINUE;
    }

    /**
     * Get the path from the start node to the goal node. The path is
     * constructed by following the parent map from the goal node to the
     * start node.
     */

    public Optional<EdgePath<V>> getPath () {
        if (goal.equals(start)) {
            return Optional.of(new EdgePath<>(List.of()));
        }
        if (!parentMap.containsKey(goal)) return Optional.empty();

        LinkedList<Edge<V>> edges = new LinkedList<>();
        V curr = goal;

        while (parentMap.containsKey(curr)) {
            Edge<V> e = parentMap.get(curr);
            edges.addFirst(e);
            curr = e.from();
        }

        return Optional.of(new EdgePath<>(edges));
    }
}