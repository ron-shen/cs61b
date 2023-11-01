package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> vertices = new LinkedList<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        edgeTo[s] = s;
        announce();

        for (int neighbour : maze.adj(s)){
            vertices.add(neighbour);
            edgeTo[neighbour] = s;
            distTo[neighbour] = 1;
        }

        while(vertices.size() != 0){
            int vertex = vertices.remove();
            marked[vertex] = true;
            announce();
            if(vertex == t) return;
            for (int neighbour : maze.adj(vertex)){
                if(!marked[neighbour]){
                    vertices.add(neighbour);
                    edgeTo[neighbour] = vertex;
                    distTo[neighbour] = 1 + distTo[vertex];
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

