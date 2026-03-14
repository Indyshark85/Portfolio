package recursive;

import graphs.DirectedGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * This is a re-implementation of the cycle detection algorithm but using a recursive
 * approach. The algorithm uses a depth-first search (DFS) strategy to traverse the
 * graph and detect cycles. It maintains a stack of vertices that are currently being
 * visited. If a vertex is revisited while it is still in the stack, a cycle is detected.
 * You only need to override one of more of the abstract methods of the DFS class.
 */
public class CycleDetection<V> extends DFS<V> {
    private final Set<V> stack = new HashSet<>();
    private boolean hasCycle;

    public CycleDetection(DirectedGraph<V> g) {
        super(g);
    }

    // TODO

    public boolean foundCycle() { return hasCycle; }
}