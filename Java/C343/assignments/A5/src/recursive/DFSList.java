package recursive;

import graphs.DirectedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * An example subclass of DFS that collects the visited vertices in a list. In this
 * case, the only method that needs to be overridden is the visit method, which adds
 * the vertex to the result list.
 */
public class DFSList<V> extends DFS<V> {
    private final List<V> result;

    public DFSList(DirectedGraph<V> g) {
        super(g);
        result = new ArrayList<>();
    }

    public void visit(V v) { result.add(v); }

    public List<V> getResult() { return result; }
}