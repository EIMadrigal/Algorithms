import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int n;
    private int numberOfOpenSites;
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufWithoutBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be large than 0");
        }
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufWithoutBottom = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n; ++i) {
            uf.union(n * n, i);
            uf.union(n * n + 1, n * n - 1 - i);
            ufWithoutBottom.union(n * n, i);
        }
        this.n = n;
        numberOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col)) {
            throw new IllegalArgumentException("Index error");
        }
        int idx = (row - 1) * n + col - 1;
        if (!grid[row - 1][col - 1]) {
            ++numberOfOpenSites;
            grid[row - 1][col - 1] = true;
            if (row - 2 >= 0 && isOpen(row - 1, col)) {
                uf.union(idx, (row - 2) * n + col - 1);
                ufWithoutBottom.union(idx, (row - 2) * n + col - 1);
            }
            if (row < n && isOpen(row + 1, col)) {
                uf.union(idx, row * n + col - 1);
                ufWithoutBottom.union(idx, row * n + col - 1);
            }
            if (col < n && isOpen(row, col + 1)) {
                uf.union(idx, (row - 1) * n + col);
                ufWithoutBottom.union(idx, (row - 1) * n + col);
            }
            if (col - 2 >= 0 && isOpen(row, col - 1)) {
                uf.union(idx, (row - 1) * n + col - 2);
                ufWithoutBottom.union(idx, (row - 1) * n + col - 2);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            throw new IllegalArgumentException("Index error");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) {
            throw new IllegalArgumentException("Index error");
        }
        int idx = (row - 1) * n + col - 1;
        return isOpen(row, col) && ufWithoutBottom.find(idx) == ufWithoutBottom.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }
        return uf.find(n * n) == uf.find(n * n + 1);
    }

    private boolean validate(int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n;
    }
}
