package hw2.hw2;

import hw2.Percolation;

public class PercolationFactory {
    public Percolation make(int N) {
        return new Percolation(N);
    }
}
