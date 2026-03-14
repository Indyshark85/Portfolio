package recursive;

import graphs.DirectedGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a recursive implementation of a depth-first search (DFS) algorithm. The traversal
 * allows for various customizations by overriding the abstract methods. Subclasses can
 * define specific behaviors for entering, visiting, revisiting, and exiting nodes in the graph.
 * Additionally, when multiple traversals are performed, the `beforeNewComponent` method can be
 * overridden to handle any setup needed before starting a new traversal.
 */
public abstract class DFS<V> {
    protected DirectedGraph<V> graph;
    protected final Set<V> visited;

    protected DFS(DirectedGraph<V> g) {
        this.graph = g;
        this.visited = new HashSet<>();
    }

    protected void enter(V v) {}
    protected void visit(V v) {}
    protected void revisit(V v) {}
    protected void exit(V v) {}
    protected void beforeNewComponent(V v) {}

    public void traverse(V current) {
        if (!visited.add(current)) revisit(current);
        else {
            enter(current);
            visit(current);
            for (V v : graph.getNeighbors(current)) traverse(v);
            exit(current);
        }
    }

    public void traverseAll(Collection<V> sources) {
        for (V v : sources) {
            if (!visited.contains(v)) {
                beforeNewComponent(v);
                traverse(v);
            }
        }
    }
}