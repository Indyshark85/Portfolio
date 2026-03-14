import graphs.MazeGraph;
import util.MazePanel;

public class MazeTest {

    public static void main (String[] args) {
        int dimension = 50;
        MazeGraph g = new MazeGraph(dimension);

        g.generateMaze();
        var maze = g.getMaze();

        g.solveMaze();
        var solution = g.getSolution();

        MazePanel.showMaze(dimension, maze, solution);
    }

}
