package traversals;

import util.Progress;

import java.util.*;

/**
 * CycleDetectStrategy detects cycles in the graph.
 * It uses a depth-first search (DFS) approach to traverse the graph that
 * is modified to detect cycles. The main issue is that nodes may be
 * revisited for different reasons, such as being part of a cycle or
 * simply being reachable through different paths. To handle this,
 * the strategy uses a list 'inPath' to keep track of the nodes that we
 * started visiting but have not yet finished visiting.
 */
public class CycleDetectStrategy<V> implements TraversalStrategy<V> {
    private final Stack<Frame<V>> toVisit = new Stack<>();
    private final Set<V> visited = new HashSet<>();
    private final List<V> inPath = new ArrayList<>();
    private Frame<V> currentFrame = new Frame<>();

    /**
     * Abstract class representing a frame in the stack. The frame
     * can be empty indicating we are done. It can also be a node frame
     * indicating we are visiting a node, or a marker indicating we
     * are done visiting a node.
     */
    private static class Frame<V> {
        V node;
        boolean isMarker;
        Frame() {}
        Frame(V node) { this.node = node; }
        Frame(V node, boolean m) { this.node = node; isMarker = m; }
        void setMarker() { isMarker = true; }
        boolean isMarker() { return isMarker; }
    }

    public void start(V start) {
        throw new Error("TODO: traversals.CycleDetectStrategy.start()");
    }

    /**
     * hasNext() returns true if there are more nodes to visit.
     */
    public boolean hasNext() {
        throw new Error("TODO: traversals.CycleDetectStrategy.hasNext()");
    }

    //TODO: Revise comments ahead after understanding "solution"

    /**
     * next() returns the next node to visit and updates the current frame
     * so that it can be used in the visit() method.
     */
    public V next() {
        throw new Error("TODO: traversals.CycleDetectStrategy.next()");
    }

    /**
     * Just returns the result of the traversal.
     */
    public List<V> getCycle() {
        throw new Error("TODO: traversals.CycleDetectStrategy.getCycle()");
    }

    /**
     * The method examines the current frame which must contain the current node.
     * If the current node is already in the path (i.e, its marker is on the stack),
     * it means we have found a cycle. Otherwise, we do the usual processing:
     * mark the current node as visited, add it to the path, and push its neighbors
     * on the stack.
     */
    public Progress visit(V current, Collection<V> neighbors) {
        throw new Error("TODO: traversals.CycleDetectStrategy.visit()");
    }
}
