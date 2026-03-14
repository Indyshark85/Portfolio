package traversals;

import graphs.DirectedGraph;
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
    private final List<V> inPath = new ArrayList<>();   // current recursion stack
    private final List<V> cycle = new ArrayList<>();   // detected cycle (if any)
    private Frame<V> currentFrame = null;
    private List<V> foundCycle = new ArrayList<>();

    /**
     * Abstract class representing a frame in the stack. The frame
     * can be empty indicating we are done. It can also be a node frame
     * indicating we are visiting a node, or a marker indicating we
     * are done visiting a node.
     */
    private static class Frame<V> {
        /**
         V node;
         boolean isMarker;
         Frame() {}
         Frame(V node) { this.node = node; }
         Frame(V node, boolean m) { this.node = node; isMarker = m; }
         void setMarker() { isMarker = true; }
         boolean isMarker() { return isMarker; }**/

        V node;
        boolean marker;

        Frame() {}
        Frame(V n) { node = n; }
        Frame(V n, boolean m) { node = n; marker = m; }

        boolean isMarker() { return marker; }
    }

    public void start(V start) {
        toVisit.clear();
        visited.clear();
        inPath.clear();
        foundCycle = new ArrayList<>();

        toVisit.push(new Frame<>(start));
    }

    /**
     * hasNext() returns true if there are more nodes to visit.
     */
    public boolean hasNext() {
        return !toVisit.isEmpty() && foundCycle.isEmpty();
        //return !toVisit.isEmpty();
    }


    /**
     * next() returns the next node to visit and updates the current frame
     * so that it can be used in the visit() method.
     */
    public V next() {
        /**
        if (toVisit.isEmpty()) return null;
        currentFrame = toVisit.pop();
        return currentFrame.node;
         **/
        if (toVisit.isEmpty())
            throw new NoSuchElementException("No more nodes");

        currentFrame = toVisit.pop();
        return currentFrame.node;


    }

    /**
     * Just returns the result of the traversal.
     */
    public List<V> getCycle() {
        return foundCycle;
    }

    /**
     * The method examines the current frame which must contain the current node.
     * If the current node is already in the path (i.e, its marker is on the stack),
     * it means we have found a cycle. Otherwise, we do the usual processing:
     * mark the current node as visited, add it to the path, and push its neighbors
     * on the stack.
     */
    public Progress visit(V current, Collection<V> neighbors) {
        // Stop immediately once cycle is found
        if (!foundCycle.isEmpty()) {
            return Progress.STOP;
        }
        // If this frame is a post-visit marker, remove from inPath and return.
        if (currentFrame != null && currentFrame.isMarker()) {
            // post-visit: remove the node that the marker frame represents
            inPath.remove(currentFrame.node);
            return Progress.CONTINUE;
        }

        // If current is already in recursion stack -> cycle
        if (inPath.contains(current)) {
            int idx = inPath.indexOf(current);
            foundCycle = new ArrayList<>(inPath.subList(idx, inPath.size()));
            foundCycle.add(current);

            return Progress.STOP;   // <-- stop traversal
        }
        visited.add(current);
        inPath.add(current);

        List<V> list = new ArrayList<>(neighbors);
        Collections.reverse(list);
        for (V n : list) {
            if (n != null) toVisit.push(new Frame<>(n));
        }

        // push marker after neighbors
        toVisit.push(new Frame<>(current, true));


        return Progress.CONTINUE;
    }

    /**
     * Constructs the detected cycle from the recursion path.
     */
    private void buildCycleFrom(V startNode) {
        int idx = inPath.indexOf(startNode);
        if (idx >= 0) {
            cycle.clear();
            cycle.addAll(inPath.subList(idx, inPath.size()));
            cycle.add(startNode); // close the cycle
        }
    }
}
