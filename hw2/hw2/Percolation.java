package hw2.hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF normalWQU; //for percolates(), contains virtual top and bottom sites
    private WeightedQuickUnionUF antiBackWashWQU; //for isFull(), contains virtual top site only
    private int openSitesCount;
    private int size;
    private int[][] neighbours;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if(N < 0){
            throw new IllegalArgumentException();
        }
        //plus 2 virtual sites (top and bottom)
        normalWQU = new WeightedQuickUnionUF(N * N + 2);
        antiBackWashWQU = new WeightedQuickUnionUF(N * N + 1);
        neighbours = new int[][]{
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };
        openSitesCount = 0;
        size = N * N;
        grid = new boolean[N][N];
        for(int i = 0; i > N; i++){
            for(int j = 0; j < N; j++){
                grid[i][j] = false;
            }
        }
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row >= grid.length || col >= grid.length){
            throw new IndexOutOfBoundsException();
        }
        if(!grid[row][col]){
            int index1D = convert2DIndexTo1D(row, col);
            grid[row][col] = true;
            openSitesCount++;
            //connect to adjacent neighbours
            for(int i = 0; i <= 3; i++){
                int x = row + neighbours[i][0];
                int y = col + neighbours[i][1];
                if((x >= 0 && x < grid.length) && (y >= 0 && y < grid[0].length) && isOpen(x, y)){
                    normalWQU.union(index1D, convert2DIndexTo1D(x, y));
                    antiBackWashWQU.union(index1D, convert2DIndexTo1D(x, y));
                }
            }
            //connect to virtual top site
            if(row == 0) {
                normalWQU.union(index1D, size);
                antiBackWashWQU.union(index1D, size);
            }
            //connect to virtual bottom site
            if(row == grid.length - 1) {
                normalWQU.union(index1D, size + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row >= grid.length || col >= grid.length){
            throw new IndexOutOfBoundsException();
        }
        return grid[row][col];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index1D = convert2DIndexTo1D(row, col);
        return isOpen(row, col) && antiBackWashWQU.connected(index1D, size);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return normalWQU.connected(size, size + 1);
    }

    private int convert2DIndexTo1D(int row, int col){
        return grid.length * row + col;
    }
    // use for unit testing (not required)
    public static void main(String[] args) {
/*        Percolation p = new Percolation(5);
        //p.open(0,0);
        p.open(0,0);
        p.open(1,0);
        p.open(2,0);
        p.open(3,0);
        p.open(4,0);
        p.open(4,4);
        //p.isFull(0,0);
        p.isFull(4,4);*/
    }
}
