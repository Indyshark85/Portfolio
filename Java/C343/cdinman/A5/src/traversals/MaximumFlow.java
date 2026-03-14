package traversals;
import graphs.WeightedDirectedGraph;
import util.Edge;
import util.Weight;

import java.util.*;

/**
 * This class finds the maximum flow in a directed graph as follows. First a copy
 * of the graph is made and will be used as a 'residual' graph. Then we have a recursive
 * method 'runMaxFlow' that performs the following steps:
 * <ul>
 *     <li>Find the shortest path from the source to the destination in the residual graph.</li>
 *     <li>If no path is found, return the current maximum flow.</li>
 *     <li>Find the minimum weight of the edges in the path; this is the bottleneck. It is
 *     added to the current maximum flow</li>
 *     <li>Modify the residual graph by subtracting the bottleneck from the edges in the path
 *     (removing the edges if the weight becomes zero) and adding the bottleneck to the reverse edges.</li>
 *     <li>Repeat the process until no more paths can be found.</li>
 * </ul>
 */
public class MaximumFlow<V> {
    private final WeightedDirectedGraph<V> residual;
    private final V source;
    private final V destination;
    private int maxFlow= 0;

    public MaximumFlow(WeightedDirectedGraph<V> graph, V source, V destination) {
        this.residual = graph.copy();
        this.source = source;
        this.destination = destination;
    }


    public int runMaxFlow() {
        while (true) {
            // BFS to find augmenting path
            Map<V, V> parent = new HashMap<>();
            Queue<V> q = new ArrayDeque<>();
            Set<V> visited = new HashSet<>();

            q.add(source);
            visited.add(source);

            boolean found = false;
            while (!q.isEmpty() && !found) {
                V u = q.remove();
                Collection<V> neigh = residual.getNeighbors(u);
                for (V v : neigh) {
                    if (visited.contains(v)) continue;
                    Optional<Weight> wOpt = residual.getEdgeWeight(u, v);
                    if (wOpt.isEmpty()) continue;
                    int cap = wOpt.get().asInt();
                    if (cap <= 0) continue;

                    parent.put(v, u);
                    visited.add(v);
                    q.add(v);

                    if (v.equals(destination)) {
                        found = true;
                        break;
                    }
                }
            }

            if (!found) return maxFlow;

            // Reconstruct path from destination -> source
            List<V> path = new ArrayList<>();
            V cur = destination;

            while (!cur.equals(source)) {
                path.add(cur);
                cur = parent.get(cur);
            }
            path.add(source);
            Collections.reverse(path);

            int bottleneck = Integer.MAX_VALUE;
            for (int i = 0; i < path.size() - 1; i++) {
                V u = path.get(i);
                V v = path.get(i+1);
                int cap = residual.getEdgeWeight(u, v).get().asInt();
                bottleneck = Math.min(bottleneck, cap);
            }
            if (bottleneck <= 0 || bottleneck == Integer.MAX_VALUE) return maxFlow;

            maxFlow += bottleneck;

            for (int i = 0; i < path.size() - 1; i++) {
                V u = path.get(i);
                V v = path.get(i + 1);

                int forward = residual.getEdgeWeight(u, v)
                        .map(Weight::asInt).orElse(0);
                int newForward = forward - bottleneck;

                residual.addEdge(u, v, Math.max(newForward, 0));

                int reverse = residual.getEdgeWeight(v, u)
                        .map(Weight::asInt).orElse(0);
                residual.addEdge(v, u, reverse + bottleneck);
            }
        }
    }
}