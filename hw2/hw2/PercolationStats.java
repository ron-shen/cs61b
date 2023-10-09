package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private double [] threshold;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationFactory pFactory = new PercolationFactory();
        threshold = new double[T];
        int size = N * N;
        for(int i = 0; i < T; i++){
            Percolation p = pFactory.make(N);
            while(!p.percolates()){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            threshold[i] = (double) p.numberOfOpenSites() / size;
        }
    }
    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(threshold);
    }
    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(threshold);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return mean() - (1.96 * stddev()) / Math.sqrt(threshold.length);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean() + (1.96 * stddev()) / Math.sqrt(threshold.length);
    }
}
