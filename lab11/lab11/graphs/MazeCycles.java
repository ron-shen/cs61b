package lab11.lab11.graphs;

import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound = false;
    private int cycleStart = Integer.MAX_VALUE;
    private List<Integer> list;

    public MazeCycles(Maze m) {
        super(m);
        list = new ArrayList<>();
    }

    private void dfs(int v) {
        marked[v] = true;
        list.add(v);
        for (int w : maze.adj(v)) {
            if(!marked[w]){
                edgeTo[w] = v;
                dfs(w);
                if(cycleFound) {
                    return;
                }
            }
            //cycle occurs
            else if(marked[w] && edgeTo[v] != w){
                cycleStart = w;
                cycleFound = true;
                return;
            }
        }
    }

    private void showGraph(){
        int i = 0;
        for(; i < list.size(); i++){
            int curNode = list.get(i);
            edgeTo[curNode] = Integer.MAX_VALUE;
            if(curNode == cycleStart) break;
        }
        edgeTo[cycleStart] = list.get(list.size() - 1);
        i++;
        for(; i < list.size(); i++){
            int parNode = list.get(i - 1);
            int curNode = list.get(i);
            edgeTo[curNode] = parNode;
        }
        announce();
    }

    @Override
    public void solve() {
        int start = maze.xyTo1D(1, 1);
        dfs(start);
        showGraph();
    }
}

