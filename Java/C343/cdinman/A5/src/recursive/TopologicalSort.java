package recursive;

import graphs.DirectedGraph;

import java.util.LinkedList;
import java.util.List;

/**
 * A topological sort is a linear ordering of vertices in a directed acyclic graph (DAG)
 * such that for every directed edge 'u --> v', vertex 'u' comes before 'v' in the ordering.
 * We sometimes use the algorithm on graphs that may contain cycles, but in that case
 * the result breaks the cycle and gives a partial ordering of the vertices. Again
 * the only thing that needs to be done is to override one or more of the abstract methods
 * of the DFS class.
 */
public class TopologicalSort<V> extends DFS<V> {
    final LinkedList<V> result;

    public TopologicalSort(DirectedGraph<V> g) {
        super(g);
        result = new LinkedList<>();
    }

    @Override
    protected void exit(V v){
        result.addFirst(v);
    }

    public List<V> getSorted() { return result; }
}
