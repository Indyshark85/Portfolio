package traversals;

import util.Progress;

import java.util.*;

/**
 * Breadth-First Search (BFS) strategy. Uses a queue to explore nodes.
 * The method 'start' initializes the traversal with a starting node.
 * The method 'hasNext' checks if there are more nodes to visit.
 * The method 'next' retrieves the next node to visit.
 * The method 'getResult' returns the list of visited nodes.
 * The method 'visit' processes the current node and its neighbors as
 * follows. If the current node has already been visited, it continues to the next node.
 * Otherwise, it adds the current node to the result list and adds its neighbors to the queue.
 * And then indicates the traversal should continue.
 */
public class BFSStrategy<V> implements TraversalStrategy<V> {
    private final Queue<V> toVisit = new LinkedList<>();
    private final Set<V> visited = new HashSet<>();
    private final List<V> result = new ArrayList<>();

    public void start(V start) {
        throw new Error("TODO: traversals.BFSStrategy.start()");
    }
    public boolean hasNext() {
        throw new Error("TODO: traversals.BFSStrategy.hasNext()");
    }
    public V next() {
        throw new Error("TODO: traversals.BFSStrategy.next()");
    }
    public List<V> getResult() {
        throw new Error("TODO: traversals.BFSStrategy.getResult()");
    }

    public Progress visit(V current, Collection<V> neighbors) {
        throw new Error("TODO: traversals.BFSStrategy.visit()");
    }
}
