package graphs;

import traversals.*;
import util.Progress;

import java.util.*;

/**
 * DirectedGraph represents a directed graph using an adjacency list. We have one
 * generic traversal strategy interface and several implementations for DFS, BFS,
 * random walks, and cycle detection.
 */
public class DirectedGraph<V> {
    private final Map<V, Set<V>> adjacencyList = new HashMap<>();

    //----------------------------------------------------------------------------
    // Graph structure setters and getters
    //----------------------------------------------------------------------------

    /**
     * Adds a vertex to the graph. If the vertex already exists, it does nothing.
     */
    public void addVertex(V v) {
        adjacencyList.putIfAbsent(v, new HashSet<>());
    }

    /**
     * Adds a directed edge from 'from' to 'to'. If either vertex does not exist,
     * it adds them.
     */
    public void addEdge(V from, V to) {
        addVertex(from);
        addVertex(to);
        adjacencyList.get(from).add(to);
    }

    /**
     * Gets the vertices in the graph.
     */
    public Set<V> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Gets the neighbors of a vertex.
     */
    public Set<V> getNeighbors(V v) {
        return adjacencyList.getOrDefault(v, new HashSet<>());
    }

    /**
     * Picks a random neighbor from the collection of neighbors. If the collection is empty,
     * it returns an empty Optional.
     */
    public Optional<V> getRandomNeighbor(Collection<V> neighbors) {
        List<V> list = new ArrayList<>(neighbors);
        if (list.isEmpty()) return Optional.empty();
        Collections.shuffle(list);
        return Optional.of(list.get(0));
    }

    /**
     * Checks if there is an edge from 'from' to 'to'.
     */
    public boolean hasEdge(V from, V to) {
        return adjacencyList.containsKey(from) && adjacencyList.get(from).contains(to);
    }

    /**
     * TODO
     * This method should return a new directed graph with the same vertices and edges,
     * but with the direction of the edges reversed.
     */
    public DirectedGraph<V> reverse() {
        DirectedGraph<V> rev = new DirectedGraph<>();

        for(V v: getVertices()){
            rev.addVertex(v);
        }

        for (V from: getVertices()){
            for(V to: getNeighbors(from)){
                rev.addEdge(to,from);
            }
        }
        return rev;
    }

    //----------------------------------------------------------------------------
    // Traversals
    //----------------------------------------------------------------------------

    /**
     * Traverses the graph starting from 'start' using the provided strategy.
     * Each strategy must implement the traversals.TraversalStrategy interface which provides
     * a method start to initialize the traversal, hasNext to check if there are
     * more nodes to visit, next to get the next node, and visit to process the
     * current node and its neighbors and decide whether to continue.
     */
    public void traverse(V start, TraversalStrategy<V> strategy) {
        /**
        strategy.start(start);
        while (strategy.hasNext()) {
            V current = strategy.next();
            // Defensive: skip null frames returned by next()
            if (current == null) continue;
            if (strategy.visit(current, getNeighbors(current)).stop()) break;
        } **/
        strategy.start(start);

        while (strategy.hasNext()) {
            V node = strategy.next();
            Progress p = strategy.visit(node, getNeighbors(node));

            if (p.stop()) {
                return;
            }
        }
    }

    // -------------------------------------------------------------------------------
    // Entry points for various common traversals
    // -------------------------------------------------------------------------------

    /**
     * Depth-First Search (DFS) traversal starting from 'start'.
     */
    public List<V> dfs(V start) {
        DFSStrategy<V> strategy = new DFSStrategy<>();
        traverse(start, strategy);
        return strategy.getResult();
    }

    /**
     * Breadth-First Search (BFS) traversal starting from 'start'.
     */
    public List<V> bfs(V start) {
        BFSStrategy<V> strategy = new BFSStrategy<>();
        traverse(start, strategy);
        return strategy.getResult();
    }

    /**
     * Random walk traversal starting from 'start' with a maximum number of steps.
     */
    public List<V> randomWalk(V start, int maxSteps) {
        RandomStrategy<V> strategy = new RandomStrategy<>(this, maxSteps);
        traverse(start, strategy);
        return strategy.getResult();
    }

    /**
     * Performs multiple random walks from 'start' and returns a frequency map of visited nodes.
     */
    public Map<V, Integer> randomWalkWithFrequency(V start, int numWalks, int maxSteps) {
        Map<V, Integer> frequency = new HashMap<>();
        for (int i = 0; i < numWalks; i++) {
            RandomStrategy<V> strategy = new RandomStrategy<>(this, maxSteps);
            traverse(start, strategy);
            for (V v : strategy.getResult()) {
                frequency.put(v, frequency.getOrDefault(v, 0) + 1);
            }
        }
        return frequency;
    }

    /**
     * Finds a path from 'start' to 'goal' using a goal-directed BFS strategy.
     */
    public List<V> findBFSPath(V start, V goal) {
        GoalDirectedBFS<V> strategy = new GoalDirectedBFS<>(start, goal);
        traverse(start, strategy);
        return strategy.getPath();
    }

    /**
     * Checks if there is a cycle in the graph starting from 'start'.
     */
    public List<V> findCycle(V start) {
        CycleDetectStrategy<V> strategy = new CycleDetectStrategy<>();
        traverse(start, strategy);
        return strategy.getCycle();
    }
}