package hw4.hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private MinPQ<SearchNode> pq;
    private int moves;
    private SearchNode res;
    private Set<WorldState> marked;
    public Solver(WorldState initial){
        pq = new MinPQ<>(new SearchNode());
        SearchNode init = new SearchNode(initial, 0, initial.estimatedDistanceToGoal(), null);
        res = null;
        marked = new HashSet<>();
        marked.add(initial);
        pq.insert(init);
        moves = 0;
        solve();
    }
    public int moves(){
        return moves;
    }
    public Iterable<WorldState> solution(){
        List<WorldState> arr = Arrays.asList(new WorldState[moves + 1]);
        SearchNode n = res;
        int i = moves;
        while(n != null){
            arr.set(i, n.ws);
            i--;
            n = n.parentNode;
        }
        return arr;
    }

    private void solve(){
        while(!pq.isEmpty()){
            SearchNode word = pq.delMin();
            if(word.ws.isGoal()){
                res = word;
                moves = res.moves;
                break;
            }
            Iterable<WorldState> neighbors = word.ws.neighbors();
            int parentMove = word.moves;
            for(WorldState neighbor: neighbors){
                if(!marked.contains(neighbor)){
                    marked.add(neighbor);
                    int edtg = neighbor.estimatedDistanceToGoal() + parentMove;
                    SearchNode node = new SearchNode(neighbor, parentMove + 1, edtg, word);
                    pq.insert(node);
                }
            }
        }
    }

    private class SearchNode implements Comparator<SearchNode> {
        private WorldState ws;
        private int moves;
        private int edtg;
        private SearchNode parentNode;

        public SearchNode(WorldState w, int moves, int edtg, SearchNode parentNode) {
            this.ws = w;
            this.moves = moves;
            this.edtg = edtg;
            this.parentNode = parentNode;
        }

        public SearchNode() {
        }

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if(o1.edtg < o2.edtg){
                return -1;
            }
            else if(o1.edtg > o2.edtg){
                return 1;
            }
            return 0;
        }
    }
}
