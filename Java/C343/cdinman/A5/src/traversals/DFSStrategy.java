package traversals;

import util.Progress;

import java.util.*;

/**
 * Depth-First Search (DFS) strategy. Uses a stack to explore nodes. The
 * method 'start' initializes the traversal with a starting node. The
 * method 'hasNext' checks if there are more nodes to visit. The
 * method 'next' retrieves the next node to visit. The method 'getResult'
 * returns the list of visited nodes. The method 'visit' processes the current
 * node and its neighbors as follows. If the current node has already been
 * visited, it continues to the next node. Otherwise, it adds the current node
 * to the result list and adds its neighbors to the stack. And then indicates
 * the traversal should continue.
 */
public class DFSStrategy<V> implements TraversalStrategy<V> {
    private final Stack<V> toVisit = new Stack<>();
    private final Set<V> visited = new HashSet<>();
    private final List<V> result = new ArrayList<>();

    public void start(V start) { toVisit.push(start); }
    public boolean hasNext() { return !toVisit.isEmpty(); }
    public V next() { return toVisit.pop(); }
    public List<V> getResult() { return result; }

    public Progress visit(V current, Collection<V> neighbors) {
        if (visited.add(current)) {
            result.add(current);
            toVisit.addAll(neighbors);
        }
        return Progress.CONTINUE;
    }
}
