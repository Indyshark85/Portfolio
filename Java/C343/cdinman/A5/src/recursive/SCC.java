package recursive;

import graphs.DirectedGraph;

import java.util.*;

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
        TopologicalSort<V> topo = new TopologicalSort<>(graph);
        topo.traverseAll(graph.getVertices());
        List<V> order = new ArrayList<>(topo.getSorted());

        DirectedGraph<V> rev = graph.reverse();
        this.graph = rev;
        this.visited.clear();
        components.clear();

        for (V v : order) {
            if (!visited.contains(v)) {
                current = new HashSet<>();
                beforeNewComponent(v);
                traverse(v);
                components.add(current);
            }
        }

        return components;
    }

    @Override
    protected void beforeNewComponent(V v){
        current = new HashSet<>();
    }

    protected void visit(V v){
        current.add(v);
    }

    private void dfs(DirectedGraph<V> g, V v) {
        visited.add(v);
        visit(v);
        for (V n : g.getNeighbors(v)) {
            if (!visited.contains(n))
                dfs(g, n);
        }
    }


    protected void preVisit(V v) { visit(v); }


}
