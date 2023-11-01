package lab11.graphs;

import java.util.*;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int[] h;
    private PriorityQueue<Node> pq;
    Set<Integer> settled;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        h = new int[maze.V()];
        pq = new PriorityQueue<Node>(maze.V(), new Node());
        settled = new HashSet<Integer>();
        setH();
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return -1;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private void setH(){
        int targetX = maze.N();
        int targetY = maze.N();
        for(int i = 0; i < h.length; i++){
            int x = maze.toX(i);
            int y = maze.toY(i);
            h[i] =  Math.abs(x - targetX) + Math.abs(y - targetY);
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        pq.add(new Node(0, 0));
        while(!pq.isEmpty()){
            Node n = pq.poll();
            settled.add(n.v);
            marked[n.v] = true;
            if(n.v == t) {
                announce();
                break;
            };
            for(int neighbour: maze.adj(n.v)){
                relax(neighbour, n.v);
            }
            announce();
        }
    }

    private void relax(int neighbour, int v){
        if(settled.contains(neighbour)) return;
        int d = 1 + distTo[v];
        if(d < distTo[neighbour]){
            edgeTo[neighbour] = v;
            distTo[neighbour] = d;
            if(pq.contains(neighbour)){
                pq.remove(new Node(neighbour, d));
            }
            pq.add(new Node(neighbour, d + h[neighbour]));
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

    private class Node implements Comparator<Node> {
        private int v;
        private int distance;

        public Node() {
        }

        public Node(int v, int distance) {

            this.v = v;
            this.distance = distance;
        }
        @Override
        public int compare(Node o1, Node o2) {
            if(o1.distance == o2.distance) {
                return 0;
            }
            else if (o1.distance < o2.distance) {
                return -1;
            }
            else{
                return 1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return v == node.v;
        }
    }
}

