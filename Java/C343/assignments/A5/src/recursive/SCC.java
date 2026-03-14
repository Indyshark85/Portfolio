package recursive;

import graphs.DirectedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class implements the Kosaraju algorithm for finding strongly connected components.
 * A strongly connected component (SCC) is a maximal subgraph where every vertex is reachable
 * from every other vertex in the subgraph. The algorithm works is two passes:
 * <ul>
 * <li> Perform topological sort on the original graph starting from every vertex.
 * This will give a topological ordering of the vertices.
 * <li> Reverse the graph, clear the visited set, and perform a DFS on the
 * vertices in the order given by the topological sort. Each time a new vertex
 * is visited, a new strongly connected component is started.
 * </ul>
 */
public class SCC<V> extends DFS<V> {
    private final List<Set<V>> components;
    private Set<V> current;

    public SCC(DirectedGraph<V> g) {
        super(g);
        this.components = new ArrayList<>();
        this.current = new HashSet<>();
    }

    /**
     * TODO
     * Computes the strongly connected components of the graph as explained above. The
     * first pass is done by a new instance of TopologicalSort. The second pass is done
     * by this class by overriding some of the abstract methods of the DFS class.
     */
    public List<Set<V>> computeSCCs() {
        throw new Error("TODO: recursive.SCC.computeSCCs()");
    }

    // TODO

}
