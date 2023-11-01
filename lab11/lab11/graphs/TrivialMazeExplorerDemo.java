package lab11.lab11.graphs;

/**
 *  @author Josh Hug
 */
public class TrivialMazeExplorerDemo {
    public static void main(String[] args) {
        Maze maze = new Maze("D:\\cs61b\\lab11\\lab11\\graphs\\maze.txt");
        TrivialMazeExplorer tme = new TrivialMazeExplorer(maze);
        tme.solve();
    }
}
