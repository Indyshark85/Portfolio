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
        toVisit.clear();
        visited.clear();
        result.clear();
        toVisit.add(start);
    }
    public boolean hasNext() {
        return !toVisit.isEmpty();
    }
    public V next() {
        return toVisit.poll();
    }
    public List<V> getResult() {
        return result;
    }

    public Progress visit(V current, Collection<V> neighbors) {
        if (visited.contains(current)) return Progress.CONTINUE;

        visited.add(current);
        result.add(current);

        for(V n: neighbors){
            toVisit.add(n);
        }
        return Progress.CONTINUE;
    }
}
