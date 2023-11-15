package hw4.hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

import static lab11.lab11.graphs.Maze.MazeType.BLANK;

public class Board implements WorldState {

    private int N;
    private int BLANK = 0;
    private ArrayList<Integer> board;


    /** Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        board = new ArrayList<>();
        N = tiles[0].length;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board.add(tiles[i][j]);
            }
        }
    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if ((i < 0 || i > N - 1) || (j < 0 || j > N - 1)) {
            throw new IndexOutOfBoundsException("The Index is Out Of Bounds!");
        }

        return board.get(N * i + j);
    }

    /** Returns the board size N */
    public int size() {
        return N;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * @source Josh Hug
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Hamming estimate
     * The number of tiles in the wrong position */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N * N - 1; i++) {
            if (board.get(i) != i + 1) {
                count += 1;
            }
        }
        return count;
    }

    /** compute a number's postion in a N-by-N array of tiles  */
    private int[] conv2D(int n) {
        int[] pos = new int[2];
        pos[0] = n / N;
        pos[1] = n % N;
        return pos;
    }

    /** compute the distance of two postions */
    private int compDis(int[] a, int[] b) {
        return (Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]));
    }

    /** Manhattan estimate
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.*/
    public int manhattan() {
        int dis = 0;
        for (int i = 0; i < N * N - 1; i++) {
            int[] posCurr = conv2D(board.indexOf(i + 1));
            int[] posGoal = conv2D(i);
            dis += compDis(posCurr,  posGoal);
        }
        return dis;
    }

    /** Estimated distance to goal. This method should simply return the results of manhattan(). */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Returns true if this board's tile values are the same
     position as y's */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board yy = (Board) y;
        if (this.N != yy.N) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tileAt(i, j) != yy.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
