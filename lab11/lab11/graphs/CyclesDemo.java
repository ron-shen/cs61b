package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class CyclesDemo {
    /* Identifies a cycle (if any exist) in the given graph, and draws the cycle with
     * a purple line. */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public static void main(String[] args) {
        Maze maze = new Maze("D:\\cs61b\\lab11\\lab11\\graphs\\maze.txt");

        MazeCycles mc = new MazeCycles(maze);
        mc.solve();
    }

}
