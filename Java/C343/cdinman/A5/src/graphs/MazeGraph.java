package graphs;

import util.Weight;

import java.util.List;
import java.util.Random;

/**
 * MazeGraph is a class that represents a maze as a weighted graph. It operates
 * in two stages: in the first state, the current instance of WeightedBiDirectedGraph
 * is used to create a grid of vertices connected symmetrically with random weights.
 * In the second stage, a minimum spanning tree is created from the grid of vertices
 * to guarantee a unique path from the start to the goal. The generated maze is
 * is created as an instance of WeightedDirectedGraph (not bidirectional). Finally,
 * Dijkstra's algorithm is used to find the path from the start to the goal.
 */
public class MazeGraph extends WeightedBiDirectedGraph<MazeGraph.Vertex> {
    private final int dimension;
    private final Random rand;
    private WeightedDirectedGraph<Vertex> maze;
    private List<Vertex> solution;

    public MazeGraph(int dimension) {
        this.dimension = dimension;
        this.rand = new Random();
        generateGrid();
    }

    /**
     * Generates a grid of vertices and connects them symmetrically with
     * random weights.
     */
    private void generateGrid() {
        for (var x = 0; x < dimension; x++) {
            for (var y = 0; y < dimension; y++) {
                var v = new Vertex(x, y);
                if (x > 0) {
                    Vertex b = new Vertex(x - 1, y);
                    addEdge(v, b, Weight.of(1 + rand.nextInt(100)));
                }
                if (y > 0) {
                    Vertex b = new Vertex(x, y - 1);
                    addEdge(v, b, Weight.of(1 + rand.nextInt(100)));
                }
            }
        }
    }

    // ---------------------------------------------------------------------------
    // The constructor above creates an instance of WeightedBiDirectedGraph.
    // Now we will use the minimum spanning tree algorithm to create a maze
    // as a directed (not bidirectional) graph.
    // ---------------------------------------------------------------------------

    /**
     * Generates a maze by creating a minimum spanning tree
     * from the grid of vertices.
     */
    public void generateMaze() {
        var start = new Vertex(0, 0);
        this.maze = new WeightedDirectedGraph<>();
        minimumSpanningTree(start).forEach(maze::addEdge);
    }

    /**
     * Finds the path from the start to the goal using Dijkstra's algorithm.
     */
    public void solveMaze() {
        var start = new Vertex(0, 0);
        var goal = new Vertex(dimension - 1, dimension - 1);
        this.solution = maze.shortestWeightedPath(start, goal).orElseThrow().nodes();
    }

    //---------------------------------------------------------------------------
    // Getters used by MazePanel to display the maze and solution
    //---------------------------------------------------------------------------

    public WeightedDirectedGraph<Vertex> getMaze() {
        return maze;
    }

    public List<Vertex> getSolution() {
        return solution;
    }

    //---------------------------------------------------------------------------
    // Inner class Vertex
    //---------------------------------------------------------------------------

    public record Vertex(int x, int y) {}
}
